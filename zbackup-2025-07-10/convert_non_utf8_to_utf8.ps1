# PowerShell script to convert non-UTF-8 files to UTF-8 encoding
# This script will detect files that are not in UTF-8 and convert only those

$sourceDir = "."
$backupDir = "non_utf8_backup_$(Get-Date -Format 'yyyyMMdd_HHmmss')"

Write-Host "Starting detection and conversion of non-UTF-8 files in: $sourceDir"
Write-Host "Backup will be created in: $backupDir"

# Create backup directory
if (!(Test-Path $backupDir)) {
    New-Item -ItemType Directory -Path $backupDir -Force | Out-Null
    Write-Host "Created backup directory: $backupDir"
}

# Function to detect if file is UTF-8
function Test-IsUTF8 {
    param(
        [string]$FilePath
    )
    
    try {
        $bytes = [System.IO.File]::ReadAllBytes($FilePath)
        
        # Check for UTF-8 BOM
        if ($bytes.Length -ge 3 -and $bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF) {
            return $true
        }
        
        # Try to decode as UTF-8 and check if it's valid
        try {
            $content = [System.Text.Encoding]::UTF8.GetString($bytes)
            $reEncoded = [System.Text.Encoding]::UTF8.GetBytes($content)
            
            # Compare original bytes with re-encoded bytes
            if ($bytes.Length -eq $reEncoded.Length) {
                for ($i = 0; $i -lt $bytes.Length; $i++) {
                    if ($bytes[$i] -ne $reEncoded[$i]) {
                        return $false
                    }
                }
                return $true
            }
            return $false
        }
        catch {
            return $false
        }
    }
    catch {
        return $false
    }
}

# Function to detect encoding
function Get-FileEncoding {
    param(
        [string]$FilePath
    )
    
    try {
        $bytes = [System.IO.File]::ReadAllBytes($FilePath)
        
        # Check for BOM
        if ($bytes.Length -ge 3 -and $bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF) {
            return "UTF-8 with BOM"
        }
        elseif ($bytes.Length -ge 2 -and $bytes[0] -eq 0xFF -and $bytes[1] -eq 0xFE) {
            return "UTF-16 LE"
        }
        elseif ($bytes.Length -ge 2 -and $bytes[0] -eq 0xFE -and $bytes[1] -eq 0xFF) {
            return "UTF-16 BE"
        }
        elseif ($bytes.Length -ge 4 -and $bytes[0] -eq 0x00 -and $bytes[1] -eq 0x00 -and $bytes[2] -eq 0xFE -and $bytes[3] -eq 0xFF) {
            return "UTF-32 BE"
        }
        elseif ($bytes.Length -ge 4 -and $bytes[0] -eq 0xFF -and $bytes[1] -eq 0xFE -and $bytes[2] -eq 0x00 -and $bytes[3] -eq 0x00) {
            return "UTF-32 LE"
        }
        
        # Try to detect encoding by analyzing content
        $content = [System.Text.Encoding]::Default.GetString($bytes)
        
        # Check if it's likely UTF-8
        if (Test-IsUTF8 -FilePath $FilePath) {
            return "UTF-8"
        }
        
        # Check for Korean characters (EUC-KR, CP949)
        if ($content -match '[가-힣]') {
            return "Korean (EUC-KR/CP949)"
        }
        
        # Check for Japanese characters
        if ($content -match '[あ-んア-ン]') {
            return "Japanese"
        }
        
        # Check for Chinese characters
        if ($content -match '[一-龯]') {
            return "Chinese"
        }
        
        return "Unknown/Default"
    }
    catch {
        return "Error detecting encoding"
    }
}

# Function to convert file to UTF-8
function Convert-FileToUTF8 {
    param(
        [string]$FilePath
    )
    
    try {
        # Create backup
        $backupFilePath = $FilePath.Replace($sourceDir, $backupDir)
        $backupFileDir = Split-Path $backupFilePath -Parent
        
        if (!(Test-Path $backupFileDir)) {
            New-Item -ItemType Directory -Path $backupFileDir -Force | Out-Null
        }
        
        Copy-Item $FilePath $backupFilePath -Force
        
        # Read file content with default encoding
        $content = Get-Content $FilePath -Raw -Encoding Default
        
        # Write content back with UTF-8 encoding
        $content | Out-File -FilePath $FilePath -Encoding UTF8 -NoNewline
        
        Write-Host "Converted: $FilePath" -ForegroundColor Green
        return $true
    }
    catch {
        Write-Host "Error converting $FilePath : $($_.Exception.Message)" -ForegroundColor Red
        return $false
    }
}

# Function to delete .bak files
function Remove-BakFiles {
    param(
        [string]$Directory
    )
    
    try {
        $bakFiles = Get-ChildItem -Path $Directory -Recurse -Filter "*.bak"
        
        foreach ($bakFile in $bakFiles) {
            try {
                Remove-Item $bakFile.FullName -Force
                Write-Host "Deleted: $($bakFile.FullName)" -ForegroundColor Yellow
            }
            catch {
                Write-Host "Error deleting $($bakFile.FullName) : $($_.Exception.Message)" -ForegroundColor Red
            }
        }
        
        return $bakFiles.Count
    }
    catch {
        Write-Host "Error searching for .bak files in $Directory : $($_.Exception.Message)" -ForegroundColor Red
        return 0
    }
}

# Step 1: Delete all .bak files first
Write-Host "`nStep 1: Deleting .bak files..."
$deletedBakCount = Remove-BakFiles -Directory $sourceDir
Write-Host "Deleted $deletedBakCount .bak files"

# Step 2: Detect and convert non-UTF-8 files
Write-Host "`nStep 2: Detecting and converting non-UTF-8 files..."

# Get all files recursively
$allFiles = Get-ChildItem -Path $sourceDir -Recurse -File | Where-Object { 
    $_.Extension -match '\.(java|txt|xml|properties|cfg|conf|ini|log|sql|js|html|htm|css|jsp|tag|tld|jbx|ejbgrpx|keep)$' -or
    $_.Name -match '\.(java|txt|xml|properties|cfg|conf|ini|log|sql|js|html|htm|css|jsp|tag|tld|jbx|ejbgrpx|keep)$' -or
    $_.Extension -eq "" -or
    $_.Name -notmatch '\.(tmp|temp)$'
}

Write-Host "Found $($allFiles.Count) total files to check"

$utf8Count = 0
$nonUtf8Count = 0
$convertedCount = 0
$errorCount = 0

foreach ($file in $allFiles) {
    $encoding = Get-FileEncoding -FilePath $file.FullName
    
    if ($encoding -eq "UTF-8" -or $encoding -eq "UTF-8 with BOM") {
        $utf8Count++
        Write-Host "Already UTF-8: $($file.FullName) ($encoding)" -ForegroundColor Cyan
    } else {
        $nonUtf8Count++
        Write-Host "Non-UTF-8 detected: $($file.FullName) ($encoding)" -ForegroundColor Yellow
        
        if (Convert-FileToUTF8 -FilePath $file.FullName) {
            $convertedCount++
        } else {
            $errorCount++
        }
    }
}

Write-Host "`nConversion Summary:"
Write-Host "Total files checked: $($allFiles.Count)"
Write-Host "Already UTF-8: $utf8Count files"
Write-Host "Non-UTF-8 detected: $nonUtf8Count files"
Write-Host "Successfully converted: $convertedCount files"
Write-Host "Conversion errors: $errorCount files"
Write-Host "Deleted .bak files: $deletedBakCount"
Write-Host "Backup location: $backupDir"

if ($errorCount -gt 0) {
    Write-Host "`nSome files failed to convert. Check the error messages above." -ForegroundColor Yellow
}

Write-Host "`nProcess completed successfully!" -ForegroundColor Green 