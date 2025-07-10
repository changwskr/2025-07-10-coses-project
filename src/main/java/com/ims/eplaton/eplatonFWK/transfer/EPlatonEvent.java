package com.ims.eplaton.eplatonFWK.transfer;


import com.chb.coses.framework.constants.Constants;
import com.chb.coses.framework.transfer.IDTO;
import com.chb.coses.framework.transfer.IEvent;


/**
 * @author 이 해일 (hiyi@componentvision.com)
 * @version 1.30
 *
 * 이 class는 business layer와 application layer사이에 주고 받는
 * 정보를 담는다.<br>
 * Application layer에서 business layer로 전달되는 정보는 request field에 저장되고,
 * Business layer에서 application layer로 전달되는 정보는 response field에 저장된다.
 * 또, Application layer에서 business layer로 전달되는 request에 항상 포함되는
 * 공통 정보는 common field에 저장된다.
 */

public final class EPlatonEvent implements IEvent
{

    private String action;
    private EPlatonCommonDTO common;
    private TPSVCINFODTO tpmsvc;
    private IDTO request;
    private IDTO response;

    /**
     * 기본 생성자.
     */
    public EPlatonEvent()
    {
      this.action = "xxxxxxxx";
      this.common = new EPlatonCommonDTO();
      this.tpmsvc = new TPSVCINFODTO();
    }

    /**
     * 모든 request에 항상 포함되는 공통 정보를 리턴한다.<br>
     *
     * @return EPlatonCommonDTO 공통 정보
     */
    public EPlatonCommonDTO getCommon()
    {
        return common;
    }

    /**
     * 모든 request에 항상 포함되는 공통 정보를 지정한다.<br>
     *
     * @param common 공통 정보
     */
    public void setCommon(EPlatonCommonDTO common)
    {
        this.common = common;
    }

    public TPSVCINFODTO getTPSVCINFODTO()
    {
        return tpmsvc;
    }

    /**
     * 모든 request에 항상 포함되는 공통 정보를 지정한다.<br>
     *
     * @param common 공통 정보
     */
    public void setTPSVCINFO(TPSVCINFODTO tpmsvc)
    {
        this.tpmsvc = tpmsvc;
    }

    /**
     * @see com.chb.coses.framework.transfer.IEvent#setAction(String)
     */
    public void setAction(String action)
    {
        this.action = action;
    }

    /**
     * @see com.chb.coses.framework.transfer.IEvent#getAction()
     */
    public String getAction()
    {
        return action;
    }

    /**
     * @see com.chb.coses.framework.transfer.IEvent#setRequest(IDTO)
     */
    public void setRequest(IDTO request)
    {
        this.request = request;
    }

    /**
     * @see com.chb.coses.framework.transfer.IEvent#getRequest()
     */
    public IDTO getRequest()
    {
        return request;
    }

    /**
     * @see com.chb.coses.framework.transfer.IEvent#setResponse(IDTO)
     */
    public void setResponse(IDTO response)
    {
        this.response = response;
    }

    /**
     * @see com.chb.coses.framework.transfer.IEvent#getResponse()
     */
    public IDTO getResponse()
    {
        return response;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "{" + getClass().getName() + "@" + this.hashCode() + Constants.LINE_SEPARATOR
        + "(action=" + this.action + ")" + Constants.LINE_SEPARATOR
        + "(common=" + this.common + ")" + Constants.LINE_SEPARATOR
        + "(request=" + this.request + ")" + Constants.LINE_SEPARATOR
        + "(response=" + this.response + ")}";
    }
}

