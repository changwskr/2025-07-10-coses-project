# PowerShell script to move non-Maven project files to backup directory

Write-Host "Moving non-Maven project files to backup directory..."
Write-Host "=================================================="

# Create backup directory
$backupDir = "zbackup-2025-07-10"
if (!(Test-Path $backupDir)) {
    New-Item -ItemType Directory -Path $backupDir -Force | Out-Null
    Write-Host "Created backup directory: $backupDir"
}

# Define Maven project structure files and directories to keep
$keepItems = @(
    "src",
    "pom.xml",
    "README.md",
    ".gitignore",
    "target"
)

# Define items to move to backup
$moveItems = @(
    "chb",
    "ims", 
    "kdb",
    "사본 - kdb",
    "convert_to_utf8.ps1",
    "convert_com_to_utf8.ps1",
    "convert_com_to_utf8com_backup_20250709_230445ps1",
    "convert_to_utf8com_backup_20250709_230445ps1",
    "convert_non_utf8_to_utf8.ps1",
    "find_keep_files.ps1",
    "migrate_to_maven.ps1",
    "migrate_legacy_code.ps1",
    "migrate_legacy_code_fixed.ps1",
    "create_java_project.ps1",
    "cleanup_project.ps1"
)

# Move items to backup
$movedCount = 0
foreach ($item in $moveItems) {
    if (Test-Path $item) {
        try {
            $targetPath = Join-Path $backupDir $item
            
            # If target already exists, add timestamp
            if (Test-Path $targetPath) {
                $timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
                $targetPath = "$targetPath`_$timestamp"
            }
            
            Move-Item $item $targetPath -Force
            Write-Host "Moved: $item -> $targetPath"
            $movedCount++
        }
        catch {
            Write-Host "Error moving $item : $($_.Exception.Message)" -ForegroundColor Red
        }
    }
}

# Move backup directories that were created during migration
$backupDirs = Get-ChildItem -Directory | Where-Object { 
    $_.Name -match ".*backup.*" -or 
    $_.Name -match ".*_backup_.*" -or
    $_.Name -match "non_utf8_backup.*" -or
    $_.Name -match "legacy_backup.*"
}

foreach ($backupDirItem in $backupDirs) {
    try {
        $targetPath = Join-Path $backupDir $backupDirItem.Name
        
        # If target already exists, add timestamp
        if (Test-Path $targetPath) {
            $timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
            $targetPath = "$targetPath`_$timestamp"
        }
        
        Move-Item $backupDirItem.FullName $targetPath -Force
        Write-Host "Moved backup directory: $($backupDirItem.Name) -> $targetPath"
        $movedCount++
    }
    catch {
        Write-Host "Error moving backup directory $($backupDirItem.Name) : $($_.Exception.Message)" -ForegroundColor Red
    }
}

# Show current project structure
Write-Host "`n=================================================="
Write-Host "Cleanup completed!"
Write-Host "Moved items: $movedCount"
Write-Host "Backup location: $backupDir"

Write-Host "`nCurrent Maven project structure:"
$currentItems = Get-ChildItem | Where-Object { $_.Name -notmatch "^$backupDir" }
foreach ($item in $currentItems) {
    $type = if ($item.PSIsContainer) { "📁" } else { "📄" }
    Write-Host "  $type $($item.Name)"
}

Write-Host "`nMaven project is now clean and ready for development!"
Write-Host "You can run: mvn clean compile" 