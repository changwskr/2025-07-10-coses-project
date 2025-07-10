# PowerShell script to migrate legacy Java code to new Maven project structure

Write-Host "기존 레거시 코드를 Maven 프로젝트 구조로 마이그레이션을 시작합니다..."
Write-Host "================================================================"

# 백업 디렉토리 생성
$backupDir = "legacy_backup_$(Get-Date -Format 'yyyyMMdd_HHmmss')"
if (!(Test-Path $backupDir)) {
    New-Item -ItemType Directory -Path $backupDir -Force | Out-Null
    Write-Host "백업 디렉토리 생성: $backupDir"
}

# 기존 소스 디렉토리들
$legacyDirs = @("chb", "ims", "kdb", "사본 - kdb")

# 마이그레이션 통계
$totalFiles = 0
$migratedFiles = 0
$errorFiles = 0

foreach ($legacyDir in $legacyDirs) {
    if (Test-Path $legacyDir) {
        Write-Host "`n처리 중인 디렉토리: $legacyDir"
        
        # Java 파일들을 찾아서 마이그레이션
        $javaFiles = Get-ChildItem -Path $legacyDir -Recurse -Filter "*.java"
        
        foreach ($file in $javaFiles) {
            $totalFiles++
            
            try {
                # 파일 내용 읽기
                $content = Get-Content $file.FullName -Raw -Encoding UTF8
                
                # 패키지 선언 찾기
                $packageMatch = [regex]::Match($content, 'package\s+([^;]+);')
                if ($packageMatch.Success) {
                    $packageName = $packageMatch.Groups[1].Value
                    
                    # 패키지 경로를 디렉토리 경로로 변환
                    $packagePath = $packageName.Replace('.', '\')
                    $targetPath = "src\main\java\$packagePath\$($file.Name)"
                    $targetDir = Split-Path $targetPath -Parent
                    
                    # 타겟 디렉토리 생성
                    if (!(Test-Path $targetDir)) {
                        New-Item -ItemType Directory -Path $targetDir -Force | Out-Null
                    }
                    
                    # 파일 복사
                    Copy-Item $file.FullName $targetPath -Force
                    
                    # 백업 생성
                    $backupPath = $file.FullName.Replace((Get-Location).Path, $backupDir)
                    $backupDirPath = Split-Path $backupPath -Parent
                    if (!(Test-Path $backupDirPath)) {
                        New-Item -ItemType Directory -Path $backupDirPath -Force | Out-Null
                    }
                    Copy-Item $file.FullName $backupPath -Force
                    
                    Write-Host "  마이그레이션 완료: $($file.Name) -> $targetPath"
                    $migratedFiles++
                } else {
                    Write-Host "  패키지 선언 없음: $($file.FullName)" -ForegroundColor Yellow
                    $errorFiles++
                }
            }
            catch {
                Write-Host "  오류 발생: $($file.FullName) - $($_.Exception.Message)" -ForegroundColor Red
                $errorFiles++
            }
        }
        
        # 리소스 파일들도 마이그레이션
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
                
                # 백업 생성
                $backupPath = $file.FullName.Replace((Get-Location).Path, $backupDir)
                $backupDirPath = Split-Path $backupPath -Parent
                if (!(Test-Path $backupDirPath)) {
                    New-Item -ItemType Directory -Path $backupDirPath -Force | Out-Null
                }
                Copy-Item $file.FullName $backupPath -Force
                
                Write-Host "  리소스 마이그레이션 완료: $($file.Name) -> $targetPath"
                $migratedFiles++
            }
            catch {
                Write-Host "  리소스 오류: $($file.FullName) - $($_.Exception.Message)" -ForegroundColor Red
                $errorFiles++
            }
        }
    }
}

# 마이그레이션 완료 후 통계 출력
Write-Host "`n================================================================"
Write-Host "마이그레이션 완료!"
Write-Host "총 파일 수: $totalFiles"
Write-Host "성공적으로 마이그레이션된 파일: $migratedFiles"
Write-Host "오류 발생 파일: $errorFiles"
Write-Host "백업 위치: $backupDir"

# 마이그레이션된 파일들의 패키지 구조 분석
Write-Host "`n마이그레이션된 패키지 구조:"
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
    Write-Host "  $package : $($packages[$package])개 파일"
}

Write-Host "`n이제 'mvn clean compile' 명령으로 프로젝트를 빌드할 수 있습니다." 