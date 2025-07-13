package com.chb.coses.eplatonFMK.business.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import com.chb.coses.eplatonFMK.business.delegate.EPlatonBizDelegateSBBean;
import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;
import com.chb.coses.eplatonFMK.transfer.EPlatonCommonDTO;
import com.chb.coses.eplatonFMK.transfer.TPSVCINFODTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.HashMap;

/**
 * EPlaton Framework 외부 연계 컨트롤러
 * 모든 REST 요청을 하나의 execute 메서드로 수신하는 구조
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
@RestController
@RequestMapping("/api/eplaton")
@Tag(name = "EPlaton Framework API", description = "EPlaton Framework 외부 연계 API")
public class EPlatonController {

    private static final Logger logger = LoggerFactory.getLogger(EPlatonController.class);

    @Autowired
    private EPlatonBizDelegateSBBean eplatonBizDelegate;

    /**
     * 모든 REST 요청을 수신하는 통합 execute 메서드
     * 
     * @param requestBody 요청 본문 (JSON)
     * @param request     HTTP 요청 객체
     * @return 응답 결과
     */
    @PostMapping(value = "/execute", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "EPlaton Framework 통합 실행", description = "모든 비즈니스 요청을 처리하는 통합 엔드포인트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<Map<String, Object>> execute(
            @Parameter(description = "요청 데이터", required = true) @RequestBody Map<String, Object> requestBody,
            HttpServletRequest request) {

        logger.info("EPlaton Controller execute 호출됨 - 요청: {}", requestBody);

        try {
            // 요청 데이터를 EPlatonEvent로 변환
            EPlatonEvent event = createEPlatonEvent(requestBody, request);

            // EPlatonBizDelegateSBBean을 통해 비즈니스 로직 실행
            Object result = eplatonBizDelegate.execute(event);

            // 응답 생성
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("result", result);
            response.put("transactionId", event.getTransactionId());
            response.put("timestamp", System.currentTimeMillis());

            logger.info("EPlaton Controller execute 성공 - 트랜잭션ID: {}", event.getTransactionId());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("EPlaton Controller execute 실패", e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());
            errorResponse.put("timestamp", System.currentTimeMillis());

            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * GET 요청을 위한 별도 엔드포인트 (조회용)
     * 
     * @param serviceName 서비스명
     * @param methodName  메서드명
     * @param request     HTTP 요청 객체
     * @return 응답 결과
     */
    @GetMapping("/execute")
    @Operation(summary = "EPlaton Framework 조회 실행", description = "GET 요청을 통한 조회 처리")
    public ResponseEntity<Map<String, Object>> executeGet(
            @Parameter(description = "서비스명") @RequestParam(required = false) String serviceName,
            @Parameter(description = "메서드명") @RequestParam(required = false) String methodName,
            HttpServletRequest request) {

        logger.info("EPlaton Controller executeGet 호출됨 - 서비스: {}, 메서드: {}", serviceName, methodName);

        try {
            // GET 요청 데이터를 Map으로 구성
            Map<String, Object> requestData = new HashMap<>();
            requestData.put("serviceName", serviceName);
            requestData.put("methodName", methodName);
            requestData.put("requestType", "GET");

            // POST execute 메서드 재사용
            return execute(requestData, request);

        } catch (Exception e) {
            logger.error("EPlaton Controller executeGet 실패", e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());
            errorResponse.put("timestamp", System.currentTimeMillis());

            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * 헬스 체크 엔드포인트
     * 
     * @return 헬스 상태
     */
    @GetMapping("/health")
    @Operation(summary = "EPlaton Framework 헬스 체크", description = "서비스 상태 확인")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> healthStatus = new HashMap<>();
        healthStatus.put("status", "UP");
        healthStatus.put("service", "EPlaton Framework");
        healthStatus.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.ok(healthStatus);
    }

    /**
     * 요청 데이터를 EPlatonEvent로 변환
     * 
     * @param requestBody 요청 본문
     * @param request     HTTP 요청 객체
     * @return EPlatonEvent 객체
     */
    private EPlatonEvent createEPlatonEvent(Map<String, Object> requestBody, HttpServletRequest request) {
        EPlatonEvent event = new EPlatonEvent();

        // 트랜잭션 ID 생성
        String transactionId = generateTransactionId();
        event.setTransactionId(transactionId);

        // 요청 데이터 설정
        if (requestBody != null) {
            event.setServiceName((String) requestBody.get("serviceName"));
            event.setMethodName((String) requestBody.get("methodName"));
            event.setRequestData(requestBody);
        }

        // HTTP 요청 정보 설정
        event.setIpAddress(getClientIpAddress(request));
        event.setRequestTime(System.currentTimeMillis());

        // 기본값 설정
        if (event.getServiceName() == null) {
            event.setServiceName("DEFAULT");
        }
        if (event.getMethodName() == null) {
            event.setMethodName("execute");
        }

        return event;
    }

    /**
     * 트랜잭션 ID 생성
     * 
     * @return 트랜잭션 ID
     */
    private String generateTransactionId() {
        return "TXN-" + System.currentTimeMillis() + "-" + Thread.currentThread().getId();
    }

    /**
     * 클라이언트 IP 주소 추출
     * 
     * @param request HTTP 요청 객체
     * @return 클라이언트 IP 주소
     */
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }

        return request.getRemoteAddr();
    }
}