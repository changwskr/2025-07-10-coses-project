package com.ims.oversea.framework.transaction.config;


import org.jdom.*;
import org.jdom.input.*;
import com.chb.coses.foundation.base.*;

/**
 * <p>이 클래스에서 사용하는 config.xml 파일은 two level로 Node의 깊이가 제한되며,</p>
 * <p>동일 level의 Node에서는 중복된 element Name을 허용하지 않는다.</p>
 * @author
 * @version 1.0
 */

public class TCFConfig
{
  private static final String CONFIG_FILE_NAME = com.ims.oversea.framework.transaction.constant.TCFConstants.CONFIG_FILE_NAME ;
  private static TCFConfig instance;

  /**
   * A private constructor
   */
  private TCFConfig() {}

  /**
   * <p>자기 자신의 Instance를 생성해서 반환한다.</p>
   *
   * @return Config  A singleton instance of Config
   */
  public static synchronized TCFConfig getInstance()
  {
    if (instance == null) {
      instance = new TCFConfig();
    }
    return instance;
  }

  /**
   * <p>Config.xml 파일의 element name으로 해당 Text를 찾아서 반환한다.</p>
   *
   * @param  serviceName  찾고자 하는 1 level Element Name
   * @param  elementName  찾고자 하는 2 level Element Name
   * @return String       검색된 2 level Element Text
   */
  public String getValue(String serviceName, String elementName)
  {
    Document doc = XMLCache.getInstance().getXML((CONFIG_FILE_NAME));
    return doc.getRootElement().getChild(serviceName).getChild(elementName).getTextTrim();
  }

  /**
   * <p>1 level Element Name으로 Element Object를 찾아서 반환한다.</p>
   * <p>2 Level을 초과하는 Config Data인 경우에 사용한다.</P>
   *
   * @param  serviceName  찾고자 하는 1 level Element Name
   * @return Element      A singleton instance of Config
   */
  public Element getElement(String serviceName)
  {
    Document doc = XMLCache.getInstance().getXML((CONFIG_FILE_NAME));
    return doc.getRootElement().getChild(serviceName);
  }
}

