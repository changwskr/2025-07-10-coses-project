# PowerShell script to migrate legacy Java code to new Maven project structure

Write-Host "Legacy code migration to Maven project structure starting..."
Write-Host "================================================================"

# Create backup directory
$backupDir = "legacy_backup_$(Get-Date -Format 'yyyyMMdd_HHmmss')"
if (!(Test-Path $backupDir)) {
    New-Item -ItemType Directory -Path $backupDir -Force | Out-Null
    Write-Host "Backup directory created: $backupDir"
}

# Legacy source directories
$legacyDirs = @("chb", "ims", "kdb")

# Migration statistics
$totalFiles = 0
$migratedFiles = 0
$errorFiles = 0

foreach ($legacyDir in $legacyDirs) {
    if (Test-Path $legacyDir) {
        Write-Host "`nProcessing directory: $legacyDir"
        
        # Find and migrate Java files
        $javaFiles = Get-ChildItem -Path $legacyDir -Recurse -Filter "*.java"
        
        foreach ($file in $javaFiles) {
            $totalFiles++
            
            try {
                # Read file content
                $content = Get-Content $file.FullName -Raw -Encoding UTF8
                
                # Find package declaration
                $packageMatch = [regex]::Match($content, 'package\s+([^;]+);')
                if ($packageMatch.Success) {
                    $packageName = $packageMatch.Groups[1].Value
                    
                    # Convert package path to directory path
                    $packagePath = $packageName.Replace('.', '\')
                    $targetPath = "src\main\java\$packagePath\$($file.Name)"
                    $targetDir = Split-Path $targetPath -Parent
                    
                    # Create target directory
                    if (!(Test-Path $targetDir)) {
                        New-Item -ItemType Directory -Path $targetDir -Force | Out-Null
                    }
                    
                    # Copy file
                    Copy-Item $file.FullName $targetPath -Force
                    
                    # Create backup
                    $backupPath = $file.FullName.Replace((Get-Location).Path, $backupDir)
                    $backupDirPath = Split-Path $backupPath -Parent
                    if (!(Test-Path $backupDirPath)) {
                        New-Item -ItemType Directory -Path $backupDirPath -Force | Out-Null
                    }
                    Copy-Item $file.FullName $backupPath -Force
                    
                    Write-Host "  Migrated: $($file.Name) -> $targetPath"
                    $migratedFiles++
                } else {
                    Write-Host "  No package declaration: $($file.FullName)" -ForegroundColor Yellow
                    $errorFiles++
                }
            }
            catch {
                Write-Host "  Error: $($file.FullName) - $($_.Exception.Message)" -ForegroundColor Red
                $errorFiles++
            }
        }
        
        # Migrate resource files
        $resourceFiles = Get-ChildItem -Path $legacyDir -Recurse -Include "*.xml", "*.properties", "*.cfg", "*.conf", "*.ini", "*.sql"
        
        foreach ($file in $resourceFiles) {
            $totalFiles++
            
            try {
                $relativePath = $file.FullName.Replace((Get-Location).Path, "").TrimStart("\")
                $targetPath = "src\main\resources\$relativePath"
                $targetDir = Split-Path $targetPath -Parent
                
                if (!(Test-Path $targetDir)) {
                    New-Item -ItemType Directory -Path $targetDir -Force | Out-Null
                }
                
                Copy-Item $file.FullName $targetPath -Force
                
                # Create backup
                $backupPath = $file.FullName.Replace((Get-Location).Path, $backupDir)
                $backupDirPath = Split-Path $backupPath -Parent
                if (!(Test-Path $backupDirPath)) {
                    New-Item -ItemType Directory -Path $backupDirPath -Force | Out-Null
                }
                Copy-Item $file.FullName $backupPath -Force
                
                Write-Host "  Resource migrated: $($file.Name) -> $targetPath"
                $migratedFiles++
            }
            catch {
                Write-Host "  Resource error: $($file.FullName) - $($_.Exception.Message)" -ForegroundColor Red
                $errorFiles++
            }
        }
    }
}

# Print migration statistics
Write-Host "`n================================================================"
Write-Host "Migration completed!"
Write-Host "Total files: $totalFiles"
Write-Host "Successfully migrated: $migratedFiles"
Write-Host "Error files: $errorFiles"
Write-Host "Backup location: $backupDir"

# Analyze migrated package structure
Write-Host "`nMigrated package structure:"
$migratedJavaFiles = Get-ChildItem -Path "src\main\java" -Recurse -Filter "*.java"
$packages = @{}

foreach ($file in $migratedJavaFiles) {
    $content = Get-Content $file.FullName -Raw -Encoding UTF8
    $packageMatch = [regex]::Match($content, 'package\s+([^;]+);')
    if ($packageMatch.Success) {
        $packageName = $packageMatch.Groups[1].Value
        if ($packages.ContainsKey($packageName)) {
            $packages[$packageName]++
        } else {
            $packages[$packageName] = 1
        }
    }
}

foreach ($package in $packages.Keys | Sort-Object) {
    Write-Host "  $package : $($packages[$package]) files"
}

Write-Host "`nYou can now run 'mvn clean compile' to build the project." 