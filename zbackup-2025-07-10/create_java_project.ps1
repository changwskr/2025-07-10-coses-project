# PowerShell script to create Java project structure

Write-Host "Creating Java project structure..."
Write-Host "=================================="

# Create project directories
$directories = @(
    "src/main/java",
    "src/main/resources", 
    "src/test/java",
    "src/test/resources",
    "src/main/webapp/WEB-INF",
    "logs"
)

foreach ($dir in $directories) {
    if (!(Test-Path $dir)) {
        New-Item -ItemType Directory -Path $dir -Force | Out-Null
        Write-Host "Created directory: $dir"
    }
}

# Create a simple Java main class
$mainClassContent = @"
package com.chb.banking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Banking System Main Application
 */
public class BankingApplication {
    
    private static final Logger logger = LoggerFactory.getLogger(BankingApplication.class);
    
    public static void main(String[] args) {
        logger.info("Starting Banking System Application...");
        
        try {
            // Initialize application
            BankingApplication app = new BankingApplication();
            app.initialize();
            
            logger.info("Banking System Application started successfully!");
            
        } catch (Exception e) {
            logger.error("Failed to start Banking System Application", e);
            System.exit(1);
        }
    }
    
    private void initialize() {
        logger.info("Initializing banking system components...");
        
        // TODO: Initialize database connection
        // TODO: Load configuration
        // TODO: Start web server
        // TODO: Initialize business components
        
        logger.info("Banking system components initialized successfully");
    }
}
"@

$mainClassContent | Out-File -FilePath "src/main/java/com/chb/banking/BankingApplication.java" -Encoding UTF8 -Force
Write-Host "Created: src/main/java/com/chb/banking/BankingApplication.java"

# Create a sample business class
$businessClassContent = @"
package com.chb.banking.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sample business logic class
 */
public class AccountService {
    
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    
    public AccountService() {
        logger.info("AccountService initialized");
    }
    
    public boolean validateAccount(String accountNumber) {
        logger.debug("Validating account: {}", accountNumber);
        
        // TODO: Implement account validation logic
        return accountNumber != null && accountNumber.length() > 0;
    }
    
    public double getBalance(String accountNumber) {
        logger.debug("Getting balance for account: {}", accountNumber);
        
        // TODO: Implement balance retrieval logic
        return 0.0;
    }
}
"@

$businessClassContent | Out-File -FilePath "src/main/java/com/chb/banking/business/AccountService.java" -Encoding UTF8 -Force
Write-Host "Created: src/main/java/com/chb/banking/business/AccountService.java"

# Create a sample test class
$testClassContent = @"
package com.chb.banking.business;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for AccountService
 */
public class AccountServiceTest {
    
    @Test
    public void testValidateAccount() {
        AccountService service = new AccountService();
        
        assertTrue("Valid account should return true", 
                   service.validateAccount("1234567890"));
        assertFalse("Null account should return false", 
                    service.validateAccount(null));
        assertFalse("Empty account should return false", 
                    service.validateAccount(""));
    }
    
    @Test
    public void testGetBalance() {
        AccountService service = new AccountService();
        
        double balance = service.getBalance("1234567890");
        assertEquals("Initial balance should be 0.0", 0.0, balance, 0.001);
    }
}
"@

$testClassContent | Out-File -FilePath "src/test/java/com/chb/banking/business/AccountServiceTest.java" -Encoding UTF8 -Force
Write-Host "Created: src/test/java/com/chb/banking/business/AccountServiceTest.java"

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

$appPropertiesContent | Out-File -FilePath "src/main/resources/application.properties" -Encoding UTF8 -Force
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

$logbackContent | Out-File -FilePath "src/main/resources/logback.xml" -Encoding UTF8 -Force
Write-Host "Created: src/main/resources/logback.xml"

# Create web.xml
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

$webXmlContent | Out-File -FilePath "src/main/webapp/WEB-INF/web.xml" -Encoding UTF8 -Force
Write-Host "Created: src/main/webapp/WEB-INF/web.xml"

# Create index.html
$indexHtmlContent = @"
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Banking System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #2c3e50;
            text-align: center;
            margin-bottom: 30px;
        }
        .feature {
            margin: 20px 0;
            padding: 15px;
            border-left: 4px solid #3498db;
            background-color: #f8f9fa;
        }
        .feature h3 {
            color: #2c3e50;
            margin-top: 0;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🏦 Banking System</h1>
        
        <div class="feature">
            <h3>고객 계좌 관리</h3>
            <p>고객의 계좌 정보를 안전하게 관리하고 조회할 수 있습니다.</p>
        </div>
        
        <div class="feature">
            <h3>현금카드 서비스</h3>
            <p>현금카드 발급, 관리 및 거래 내역을 처리합니다.</p>
        </div>
        
        <div class="feature">
            <h3>해외 서비스</h3>
            <p>해외 송금, 외화 거래 등 국제 금융 서비스를 제공합니다.</p>
        </div>
        
        <div class="feature">
            <h3>거래 처리 시스템</h3>
            <p>실시간 거래 처리 및 정산 시스템을 운영합니다.</p>
        </div>
        
        <p style="text-align: center; margin-top: 30px; color: #7f8c8d;">
            Banking System v1.0.0
        </p>
    </div>
</body>
</html>
"@

$indexHtmlContent | Out-File -FilePath "src/main/webapp/index.html" -Encoding UTF8 -Force
Write-Host "Created: src/main/webapp/index.html"

# Create .gitignore
$gitignoreContent = @"
# Compiled class files
*.class

# Log files
*.log
logs/

# BlueJ files
*.ctxt

# Mobile Tools for Java (J2ME)
.mtj.tmp/

# Package Files
*.jar
*.war
*.nar
*.ear
*.zip
*.tar.gz
*.rar

# Virtual machine crash logs
hs_err_pid*
replay_pid*

# Maven
target/
pom.xml.tag
pom.xml.releaseBackup
pom.xml.versionsBackup
pom.xml.next
release.properties
dependency-reduced-pom.xml
buildNumber.properties
.mvn/timing.properties
.mvn/wrapper/maven-wrapper.jar

# IDE
.idea/
*.iml
.vscode/
.settings/
.project
.classpath

# OS
.DS_Store
Thumbs.db
"@

$gitignoreContent | Out-File -FilePath ".gitignore" -Encoding UTF8 -Force
Write-Host "Created: .gitignore"

Write-Host "`nJava project structure created successfully!"
Write-Host "Project structure:"
Write-Host "├── src/"
Write-Host "│   ├── main/"
Write-Host "│   │   ├── java/          # Java source code"
Write-Host "│   │   ├── resources/     # Configuration files"
Write-Host "│   │   └── webapp/        # Web application files"
Write-Host "│   └── test/"
Write-Host "│       ├── java/          # Test code"
Write-Host "│       └── resources/     # Test resources"
Write-Host "├── pom.xml                # Maven configuration"
Write-Host "├── README.md              # Project documentation"
Write-Host "└── .gitignore            # Git ignore file"
Write-Host "`nYou can now run: mvn clean compile" 