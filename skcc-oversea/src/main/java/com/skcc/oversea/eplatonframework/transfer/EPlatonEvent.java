package com.skcc.oversea.eplatonframework.transfer;

import com.skcc.oversea.framework.constants.Constants;
import com.skcc.oversea.framework.transfer.IDTO;
import com.skcc.oversea.framework.transfer.IEvent;

/**
 * =============================================================================
 * 프로그램 설명:
 * =============================================================================
 * 이 class는 business layer와 application layer사이에 주고 받는
 * 정보를 담는다.<br>
 * Application layer에서 business layer로 전달되는 정보는 request field에 저장되고,
 * Business layer에서 application layer로 전달되는 정보는 response field에 저장된다.
 * 또, Application layer에서 business layer로 전달되는 request에 항상 포함되는
 * 공통 정보는 common field에 저장된다.
 *
 * =============================================================================
 * 변경내역 정보:
 * =============================================================================
 * 2004년 03월 16일 1차버전 release
 *
 *
 * =============================================================================
 * 
 * @author : 장우승(WooSungJang)
 * @company: IMS SYSTEM
 * @email : changwskr@yahoo.co.kr
 * @version 1.0
 *          =============================================================================
 */

public final class EPlatonEvent implements IEvent {

    private String action;
    private EPlatonCommonDTO common;
    private TPSVCINFODTO tpmsvc;
    private IDTO request;
    private IDTO response;
    private String source;
    private String type;
    private Object data;

    /**
     * 기본 생성자.
     */
    public EPlatonEvent() {
        this.action = "xxxxxxxx";
        this.common = new EPlatonCommonDTO();
        this.tpmsvc = new TPSVCINFODTO();
    }

    /**
     * 모든 request에 항상 포함되는 공통 정보를 리턴한다.<br>
     *
     * @return EPlatonCommonDTO 공통 정보
     */
    public EPlatonCommonDTO getCommon() {
        return common;
    }

    /**
     * 모든 request에 항상 포함되는 공통 정보를 지정한다.<br>
     *
     * @param common 공통 정보
     */
    public void setCommon(EPlatonCommonDTO common) {
        this.common = common;
    }

    public TPSVCINFODTO getTPSVCINFODTO() {
        return tpmsvc;
    }

    /**
     * 모든 request에 항상 포함되는 공통 정보를 지정한다.<br>
     *
     * @param common 공통 정보
     */
    public void setTPSVCINFO(TPSVCINFODTO tpmsvc) {
        this.tpmsvc = tpmsvc;
    }

    public void setTPSVCINFODTO(TPSVCINFODTO tpmsvc) {
        this.tpmsvc = tpmsvc;
    }

    /**
     * @see com.chb.coses.framework.transfer.IEvent#setAction(String)
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @see com.chb.coses.framework.transfer.IEvent#getAction()
     */
    public String getAction() {
        return action;
    }

    /**
     * @see com.chb.coses.framework.transfer.IEvent#setRequest(IDTO)
     */
    public void setRequest(IDTO request) {
        this.request = request;
    }

    /**
     * @see com.chb.coses.framework.transfer.IEvent#getRequest()
     */
    public IDTO getRequest() {
        return request;
    }

    /**
     * @see com.chb.coses.framework.transfer.IEvent#setResponse(IDTO)
     */
    public void setResponse(IDTO response) {
        this.response = response;
    }

    /**
     * @see com.chb.coses.framework.transfer.IEvent#getResponse()
     */
    public IDTO getResponse() {
        return response;
    }

    @Override
    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public void setData(Object data) {
        this.data = data;
    }

    public String getSource() {
        return source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("EPlatonEvent {");
        sb.append(Constants.LINE_SEPARATOR);
        sb.append("  source: ").append(source);
        sb.append(Constants.LINE_SEPARATOR);
        sb.append("  type: ").append(type);
        sb.append(Constants.LINE_SEPARATOR);
        sb.append("  data: ").append(data);
        sb.append(Constants.LINE_SEPARATOR);
        sb.append("}");
        return sb.toString();
    }
}
