# PowerShell script to migrate existing Java source code to Maven project structure

Write-Host "Starting migration to Maven project structure..."
Write-Host "================================================"

# Create Maven project directories if they don't exist
$directories = @(
    "src/main/java",
    "src/main/resources", 
    "src/test/java",
    "src/test/resources",
    "src/main/webapp/WEB-INF"
)

foreach ($dir in $directories) {
    if (!(Test-Path $dir)) {
        New-Item -ItemType Directory -Path $dir -Force | Out-Null
        Write-Host "Created directory: $dir"
    }
}

# Function to copy Java files to Maven structure
function Copy-JavaFiles {
    param(
        [string]$SourceDir,
        [string]$TargetDir
    )
    
    try {
        $javaFiles = Get-ChildItem -Path $SourceDir -Recurse -Filter "*.java"
        
        foreach ($file in $javaFiles) {
            $relativePath = $file.FullName.Replace($SourceDir, "").TrimStart("\")
            $targetPath = Join-Path $TargetDir $relativePath
            $targetDir = Split-Path $targetPath -Parent
            
            if (!(Test-Path $targetDir)) {
                New-Item -ItemType Directory -Path $targetDir -Force | Out-Null
            }
            
            Copy-Item $file.FullName $targetPath -Force
            Write-Host "Copied: $relativePath"
        }
        
        return $javaFiles.Count
    }
    catch {
        Write-Host "Error copying files from $SourceDir : $($_.Exception.Message)" -ForegroundColor Red
        return 0
    }
}

# Function to copy resource files
function Copy-ResourceFiles {
    param(
        [string]$SourceDir,
        [string]$TargetDir
    )
    
    try {
        $resourceFiles = Get-ChildItem -Path $SourceDir -Recurse -Include "*.xml", "*.properties", "*.cfg", "*.conf", "*.ini", "*.sql"
        
        foreach ($file in $resourceFiles) {
            $relativePath = $file.FullName.Replace($SourceDir, "").TrimStart("\")
            $targetPath = Join-Path $TargetDir $relativePath
            $targetDir = Split-Path $targetPath -Parent
            
            if (!(Test-Path $targetDir)) {
                New-Item -ItemType Directory -Path $targetDir -Force | Out-Null
            }
            
            Copy-Item $file.FullName $targetPath -Force
            Write-Host "Copied resource: $relativePath"
        }
        
        return $resourceFiles.Count
    }
    catch {
        Write-Host "Error copying resource files from $SourceDir : $($_.Exception.Message)" -ForegroundColor Red
        return 0
    }
}

# Copy Java files from existing directories
$sourceDirs = @("chb", "ims", "kdb")
$totalJavaFiles = 0
$totalResourceFiles = 0

foreach ($sourceDir in $sourceDirs) {
    if (Test-Path $sourceDir) {
        Write-Host "`nProcessing directory: $sourceDir"
        $javaCount = Copy-JavaFiles -SourceDir $sourceDir -TargetDir "src/main/java"
        $resourceCount = Copy-ResourceFiles -SourceDir $sourceDir -TargetDir "src/main/resources"
        $totalJavaFiles += $javaCount
        $totalResourceFiles += $resourceCount
    }
}

# Create web.xml for web application
$webXmlContent = @"
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee 
         http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    
    <display-name>Banking System</display-name>
    
    <welcome-file-list>
        <welcome-file>index.jsp</welcome-file>
        <welcome-file>index.html</welcome-file>
    </welcome-file-list>
    
    <session-config>
        <session-timeout>30</session-timeout>
    </session-config>
    
</web-app>
"@

$webXmlContent | Out-File -FilePath "src/main/webapp/WEB-INF/web.xml" -Encoding UTF8
Write-Host "Created: src/main/webapp/WEB-INF/web.xml"

# Create application.properties
$appPropertiesContent = @"
# Database Configuration
db.url=jdbc:mysql://localhost:3306/banking_system
db.username=root
db.password=password
db.driver=com.mysql.cj.jdbc.Driver

# Application Configuration
app.name=Banking System
app.version=1.0.0
app.encoding=UTF-8

# Logging Configuration
logging.level.root=INFO
logging.level.com.chb=DEBUG
logging.pattern=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
"@

$appPropertiesContent | Out-File -FilePath "src/main/resources/application.properties" -Encoding UTF8
Write-Host "Created: src/main/resources/application.properties"

# Create logback configuration
$logbackContent = @"
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>logs/banking-system.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>logs/banking-system.%d{yyyy-MM-dd}.log</fileNamePattern>
            <maxHistory>30</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    
    <root level="INFO">
        <appender-ref ref="STDOUT" />
        <appender-ref ref="FILE" />
    </root>
    
    <logger name="com.chb" level="DEBUG" />
</configuration>
"@

$logbackContent | Out-File -FilePath "src/main/resources/logback.xml" -Encoding UTF8
Write-Host "Created: src/main/resources/logback.xml"

# Create README.md
$readmeContent = @"
# Banking System

이 프로젝트는 기존 레거시 Java 코드를 Maven 프로젝트 구조로 마이그레이션한 은행 시스템입니다.

## 프로젝트 구조

```
src/
├── main/
│   ├── java/          # Java 소스 코드
│   ├── resources/     # 설정 파일들
│   └── webapp/        # 웹 애플리케이션 파일들
└── test/
    ├── java/          # 테스트 코드
    └── resources/     # 테스트 리소스
```

## 빌드 및 실행

### Maven을 사용한 빌드
```bash
mvn clean compile
mvn package
```

### 테스트 실행
```bash
mvn test
```

### 웹 애플리케이션 실행
```bash
mvn tomcat7:run
```

## 주요 기능

- 고객 계좌 관리
- 현금카드 서비스
- 해외 서비스
- 거래 처리 시스템

## 기술 스택

- Java 8
- Maven
- Java EE
- MySQL
- Logback
- JUnit

## 설정

`src/main/resources/application.properties` 파일에서 데이터베이스 연결 정보를 수정하세요.
"@

$readmeContent | Out-File -FilePath "README.md" -Encoding UTF8
Write-Host "Created: README.md"

Write-Host "`nMigration completed!"
Write-Host "Total Java files copied: $totalJavaFiles"
Write-Host "Total resource files copied: $totalResourceFiles"
Write-Host "`nMaven project structure created successfully!"
Write-Host "You can now run: mvn clean compile" 