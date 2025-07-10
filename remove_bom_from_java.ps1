# PowerShell script to remove BOM from all .java files in src/main/java

Write-Host "Removing BOM from all .java files in src/main/java..."

Get-ChildItem -Path 'src/main/java' -Recurse -Filter *.java | ForEach-Object {
    $content = Get-Content $_.FullName -Raw
    # Remove BOM if present
    $content = $content -replace '^EFF', ''
    # Save as UTF-8 without BOM
    [System.IO.File]::WriteAllText($_.FullName, $content, (New-Object System.Text.UTF8Encoding($false)))
    Write-Host "BOM removed: $($_.FullName)"
}

Write-Host "All BOMs removed. Now you can run: mvn clean compile" 