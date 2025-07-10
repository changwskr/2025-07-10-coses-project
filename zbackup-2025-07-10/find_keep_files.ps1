# PowerShell script to find all .keep files in the com directory

$sourceDir = "."

Write-Host "Searching for .keep files in: $sourceDir"
Write-Host "=========================================="

# Find all .keep files recursively
$keepFiles = Get-ChildItem -Path $sourceDir -Recurse -Filter "*.keep"

Write-Host "Found $($keepFiles.Count) .keep files:"
Write-Host ""

foreach ($file in $keepFiles) {
    $relativePath = $file.FullName.Replace((Get-Location).Path + "\", "")
    Write-Host "File: $relativePath"
    Write-Host "Size: $($file.Length) bytes"
    Write-Host "Last Modified: $($file.LastWriteTime)"
    Write-Host "---"
}

Write-Host "`nSummary:"
Write-Host "Total .keep files found: $($keepFiles.Count)"

# Group by directory
Write-Host "`nFiles by directory:"
$groupedFiles = $keepFiles | Group-Object { Split-Path $_.DirectoryName -Leaf }
foreach ($group in $groupedFiles) {
    Write-Host "$($group.Name): $($group.Count) files"
} 