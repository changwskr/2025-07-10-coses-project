package com.chb.coses.eplatonFMK.business.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * TPC 송수신 클래스
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
@Component
public class TPCsendrecv {

    private static final Logger logger = LoggerFactory.getLogger(TPCsendrecv.class);

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    /**
     * 기본 생성자
     */
    public TPCsendrecv() {
        super();
    }

    /**
     * 소켓 연결
     * 
     * @param host 호스트
     * @param port 포트
     * @throws Exception 예외
     */
    public void connect(String host, int port) throws Exception {
        logger.debug("Connecting to {}:{}", host, port);

        try {
            socket = new Socket(host, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            logger.debug("Connected to {}:{}", host, port);
        } catch (Exception e) {
            logger.error("Failed to connect to {}:{}", host, port, e);
            throw e;
        }
    }

    /**
     * 데이터 전송
     * 
     * @param data 전송할 데이터
     * @throws Exception 예외
     */
    public void send(byte[] data) throws Exception {
        logger.debug("Sending {} bytes", data.length);

        try {
            out.writeInt(data.length);
            out.write(data);
            out.flush();

            logger.debug("Sent {} bytes successfully", data.length);
        } catch (Exception e) {
            logger.error("Failed to send data", e);
            throw e;
        }
    }

    /**
     * 데이터 수신
     * 
     * @return 수신된 데이터
     * @throws Exception 예외
     */
    public byte[] receive() throws Exception {
        logger.debug("Receiving data");

        try {
            int length = in.readInt();
            byte[] data = new byte[length];
            in.readFully(data);

            logger.debug("Received {} bytes successfully", length);
            return data;
        } catch (Exception e) {
            logger.error("Failed to receive data", e);
            throw e;
        }
    }

    /**
     * 연결 해제
     */
    public void disconnect() {
        logger.debug("Disconnecting");

        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }

            logger.debug("Disconnected successfully");
        } catch (Exception e) {
            logger.error("Failed to disconnect", e);
        }
    }

    /**
     * 연결 상태 확인
     * 
     * @return 연결 상태
     */
    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }
}