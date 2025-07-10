package com.chb.coses.eplatonFWK.business.dao;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class DAOException extends Exception
{
    private Exception cause;

    public DAOException()
    {
        super();
    }

    public DAOException(String message)
    {
        super(message);
    }

    public DAOException(Exception cause)
    {
        super();
        this.cause = cause;
    }

    public DAOException(String message, Exception cause)
    {
        super(message);
        this.cause = cause;
    }

    public Exception getCausedException()
    {
        return cause;
    }

    /**
      * 체인된 예외가 있다면 문자열에 추가하여 돌려준다.
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer(super.toString());
        if (cause != null) {
            sb.append(System.getProperty("line.separator"));
            sb.append("Caused by: ");
            sb.append(cause.toString());
        }
        return sb.toString();
    }
}