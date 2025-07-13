# PowerShell script to convert package names from com.kdb.oversea to com.skcc.oversea
# Exclude tpmutil packages as requested

Write-Host "Starting package name conversion..." -ForegroundColor Green

# Source directory (current kdb-oversea source)
$sourceDir = "..\src\main\java\com\kdb\oversea"
$targetDir = "src\main\java\com\skcc\oversea"

# Create target directory structure
if (!(Test-Path $targetDir)) {
    New-Item -ItemType Directory -Path $targetDir -Force
    Write-Host "Created target directory: $targetDir" -ForegroundColor Yellow
}

# Function to copy and convert files
function Copy-And-Convert-Files {
    param($sourcePath, $targetPath)
    
    if (Test-Path $sourcePath) {
        # Create target directory if it doesn't exist
        $targetDirPath = Split-Path $targetPath -Parent
        if (!(Test-Path $targetDirPath)) {
            New-Item -ItemType Directory -Path $targetDirPath -Force
        }
        
        # Read file content
        $content = Get-Content $sourcePath -Raw -Encoding UTF8
        
        # Skip tpmutil packages
        if ($content -match "package com\.kdb\.oversea.*tpmutil") {
            Write-Host "Skipping tpmutil file: $sourcePath" -ForegroundColor Gray
            return
        }
        
        # Replace package declarations
        $content = $content -replace "package com\.kdb\.oversea", "package com.skcc.oversea"
        
        # Replace import statements
        $content = $content -replace "import com\.kdb\.oversea", "import com.skcc.oversea"
        
        # Write converted content to target file
        $content | Out-File -FilePath $targetPath -Encoding UTF8
        
        Write-Host "Converted: $sourcePath -> $targetPath" -ForegroundColor Green
    }
}

# Function to process directory recursively
function Process-Directory {
    param($sourceDir, $targetDir)
    
    if (Test-Path $sourceDir) {
        $items = Get-ChildItem $sourceDir
        
        foreach ($item in $items) {
            $sourcePath = $item.FullName
            $relativePath = $item.FullName.Substring((Resolve-Path $sourceDir).Path.Length + 1)
            $targetPath = Join-Path $targetDir $relativePath
            
            if ($item.PSIsContainer) {
                # Skip tpmutil directories
                if ($item.Name -eq "tpmutil") {
                    Write-Host "Skipping tpmutil directory: $sourcePath" -ForegroundColor Gray
                    continue
                }
                
                # Recursively process subdirectory
                Process-Directory $sourcePath $targetPath
            } else {
                # Process file
                if ($item.Extension -eq ".java") {
                    Copy-And-Convert-Files $sourcePath $targetPath
                } else {
                    # Copy non-Java files as-is
                    $targetDirPath = Split-Path $targetPath -Parent
                    if (!(Test-Path $targetDirPath)) {
                        New-Item -ItemType Directory -Path $targetDirPath -Force
                    }
                    Copy-Item $sourcePath $targetPath -Force
                    Write-Host "Copied: $sourcePath -> $targetPath" -ForegroundColor Blue
                }
            }
        }
    }
}

# Start conversion
Write-Host "Processing source directory: $sourceDir" -ForegroundColor Yellow
Process-Directory $sourceDir $targetDir

Write-Host "Package name conversion completed!" -ForegroundColor Green
Write-Host "Converted files are in: $targetDir" -ForegroundColor Yellow 