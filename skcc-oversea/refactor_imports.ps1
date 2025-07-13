# Import refactoring script for SKCC Oversea project
# This script replaces com.chb.coses imports with com.skcc.oversea

param(
    [string]$ProjectPath = "."
)

Write-Host "Starting import refactoring for SKCC Oversea project..." -ForegroundColor Green

# Define import mappings
$importMappings = @{
    # Framework transfer
    "import com\.chb\.coses\.framework\.transfer\.DTO;" = "import com.skcc.oversea.framework.transfer.DTO;"
    "import com\.chb\.coses\.framework\.transfer\.IDTO;" = "import com.skcc.oversea.framework.transfer.IDTO;"
    "import com\.chb\.coses\.framework\.transfer\.IEvent;" = "import com.skcc.oversea.framework.transfer.IEvent;"
    "import com\.chb\.coses\.framework\.transfer\.ListDTO;" = "import com.skcc.oversea.framework.transfer.ListDTO;"
    "import com\.chb\.coses\.framework\.transfer\.\*;" = "import com.skcc.oversea.framework.transfer.*;"
    
    # Foundation log
    "import com\.chb\.coses\.foundation\.log\.Log;" = "import com.skcc.oversea.foundation.log.Log;"
    "import com\.chb\.coses\.foundation\.log\.\*;" = "import com.skcc.oversea.foundation.log.*;"
    
    # Foundation utility
    "import com\.chb\.coses\.foundation\.utility\.StringUtils;" = "import com.skcc.oversea.foundation.utility.StringUtils;"
    "import com\.chb\.coses\.foundation\.utility\.\*;" = "import com.skcc.oversea.foundation.utility.*;"
    
    # Foundation config
    "import com\.chb\.coses\.foundation\.config\.Config;" = "import com.skcc.oversea.foundation.config.Config;"
    "import com\.chb\.coses\.foundation\.config\.\*;" = "import com.skcc.oversea.foundation.config.*;"
    
    # Foundation base
    "import com\.chb\.coses\.foundation\.base\.\*;" = "import com.skcc.oversea.foundation.base.*;"
    
    # Foundation jndi
    "import com\.chb\.coses\.foundation\.jndi\.JNDIService;" = "import com.skcc.oversea.foundation.jndi.JNDIService;"
    "import com\.chb\.coses\.foundation\.jndi\.\*;" = "import com.skcc.oversea.foundation.jndi.*;"
    
    # Foundation db
    "import com\.chb\.coses\.foundation\.db\.DBService;" = "import com.skcc.oversea.foundation.db.DBService;"
    "import com\.chb\.coses\.foundation\.db\.\*;" = "import com.skcc.oversea.foundation.db.*;"
    
    # Framework constants
    "import com\.chb\.coses\.framework\.constants\.Constants;" = "import com.skcc.oversea.framework.constants.Constants;"
    "import com\.chb\.coses\.framework\.constants\.\*;" = "import com.skcc.oversea.framework.constants.*;"
    
    # Framework exception
    "import com\.chb\.coses\.framework\.exception\.BizActionException;" = "import com.skcc.oversea.framework.exception.BizActionException;"
    "import com\.chb\.coses\.framework\.exception\.BizDelegateException;" = "import com.skcc.oversea.framework.exception.BizDelegateException;"
    "import com\.chb\.coses\.framework\.exception\.\*;" = "import com.skcc.oversea.framework.exception.*;"
    
    # Framework business
    "import com\.chb\.coses\.framework\.business\.delegate\.IBizDelegate;" = "import com.skcc.oversea.framework.business.delegate.IBizDelegate;"
    "import com\.chb\.coses\.framework\.business\.delegate\.\*;" = "import com.skcc.oversea.framework.business.delegate.*;"
    "import com\.chb\.coses\.framework\.business\.dao\.IDAO;" = "import com.skcc.oversea.framework.business.dao.IDAO;"
    "import com\.chb\.coses\.framework\.business\.dao\.AbstractDAO;" = "import com.skcc.oversea.framework.business.dao.AbstractDAO;"
    "import com\.chb\.coses\.framework\.business\.dao\.AbstractDAOFactory;" = "import com.skcc.oversea.framework.business.dao.AbstractDAOFactory;"
    "import com\.chb\.coses\.framework\.business\.dao\.\*;" = "import com.skcc.oversea.framework.business.dao.*;"
    "import com\.chb\.coses\.framework\.business\.\*;" = "import com.skcc.oversea.framework.business.*;"
    
    # CosesFramework exception
    "import com\.chb\.coses\.cosesFramework\.exception\.CosesAppException;" = "import com.skcc.oversea.framework.exception.CosesAppException;"
    "import com\.chb\.coses\.cosesFramework\.exception\.CosesExceptionDetail;" = "import com.skcc.oversea.framework.exception.CosesExceptionDetail;"
    "import com\.chb\.coses\.cosesFramework\.exception\.\*;" = "import com.skcc.oversea.framework.exception.*;"
    
    # CosesFramework transfer
    "import com\.chb\.coses\.cosesFramework\.transfer\.CosesCommonDTO;" = "import com.skcc.oversea.framework.transfer.CosesCommonDTO;"
    "import com\.chb\.coses\.cosesFramework\.transfer\.\*;" = "import com.skcc.oversea.framework.transfer.*;"
    
    # Common business
    "import com\.chb\.coses\.common\.business\.constants\.\*;" = "import com.skcc.oversea.common.business.constants.*;"
    "import com\.chb\.coses\.common\.business\.facade\.\*;" = "import com.skcc.oversea.common.business.facade.*;"
    "import com\.chb\.coses\.common\.transfer\.\*;" = "import com.skcc.oversea.common.transfer.*;"
    
    # User business
    "import com\.chb\.coses\.user\.business\.facade\.\*;" = "import com.skcc.oversea.user.business.facade.*;"
    "import com\.chb\.coses\.user\.transfer\.UserCDTO;" = "import com.skcc.oversea.user.transfer.UserCDTO;"
    "import com\.chb\.coses\.user\.transfer\.\*;" = "import com.skcc.oversea.user.transfer.*;"
    
    # Reference business
    "import com\.chb\.coses\.reference\.business\.facade\.\*;" = "import com.skcc.oversea.reference.business.facade.*;"
    
    # Deposit business
    "import com\.chb\.coses\.deposit\.business\.facade\.\*;" = "import com.skcc.oversea.deposit.business.facade.*;"
    "import com\.chb\.coses\.deposit\.transfer\.\*;" = "import com.skcc.oversea.deposit.transfer.*;"
    
    # GL Posting
    "import com\.chb\.coses\.glPosting\.transfer\.\*;" = "import com.skcc.oversea.glposting.transfer.*;"
    
    # Linkage
    "import com\.chb\.coses\.linkage\.transfer\.\*;" = "import com.skcc.oversea.linkage.transfer.*;"
}

# Get all Java files in the project
$javaFiles = Get-ChildItem -Path $ProjectPath -Recurse -Filter "*.java" | Where-Object { $_.FullName -like "*skcc-oversea*" }

Write-Host "Found $($javaFiles.Count) Java files to process" -ForegroundColor Yellow

$processedFiles = 0
$modifiedFiles = 0

foreach ($file in $javaFiles) {
    $processedFiles++
    Write-Progress -Activity "Processing Java files" -Status "Processing $($file.Name)" -PercentComplete (($processedFiles / $javaFiles.Count) * 100)
    
    $content = Get-Content -Path $file.FullName -Raw -Encoding UTF8
    $originalContent = $content
    $fileModified = $false
    
    # Apply import mappings
    foreach ($mapping in $importMappings.GetEnumerator()) {
        $pattern = $mapping.Key
        $replacement = $mapping.Value
        
        if ($content -match $pattern) {
            $content = $content -replace $pattern, $replacement
            $fileModified = $true
            Write-Host "  Updated import in $($file.Name): $pattern -> $replacement" -ForegroundColor Cyan
        }
    }
    
    # Write back to file if modified
    if ($fileModified) {
        Set-Content -Path $file.FullName -Value $content -Encoding UTF8
        $modifiedFiles++
        Write-Host "  Modified: $($file.Name)" -ForegroundColor Green
    }
}

Write-Host "`nImport refactoring completed!" -ForegroundColor Green
Write-Host "Processed files: $processedFiles" -ForegroundColor White
Write-Host "Modified files: $modifiedFiles" -ForegroundColor White

# Create summary report
$reportPath = Join-Path $ProjectPath "import_refactoring_report.txt"
$report = @"
Import Refactoring Report
=========================
Date: $(Get-Date)
Project: SKCC Oversea
Processed files: $processedFiles
Modified files: $modifiedFiles

Import Mappings Applied:
"@

foreach ($mapping in $importMappings.GetEnumerator()) {
    $report += "`n$($mapping.Key) -> $($mapping.Value)"
}

$report | Out-File -FilePath $reportPath -Encoding UTF8
Write-Host "`nReport saved to: $reportPath" -ForegroundColor Yellow 