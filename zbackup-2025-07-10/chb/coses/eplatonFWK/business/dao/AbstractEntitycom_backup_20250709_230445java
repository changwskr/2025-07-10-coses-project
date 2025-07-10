package com.chb.coses.eplatonFWK.business.dao;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.chb.coses.foundation.log.Log;
import com.chb.coses.foundation.utility.StringUtils;

/**
 * This class is abstract entity.
 *
 * @author  <a href="mailto:ghyu@imssystem.com">Gwanghyeok Yu</a>
 * @version 1.0, 2002/11/01
 */
public abstract class AbstractEntity
{
    /**
     * DB 특수 문자들을 escape처리한다.
     */
    public static String escape(String value)
    {
        if (null == value) return "";
        value = StringUtils.replace(value, "'", "''");
        return StringUtils.replace(value, "%", "%%");
    }

    /**
     * Release DB resource.
     */
    public static void releaseResource(Statement stmt, ResultSet rs)
    {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) { /* ignored */ }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) { /* ignored */ }
        }
    }

    // ------------------------------------------------ helper method

    public static boolean isLogabled()
    {
        return Log.VDCLogger.isDebugEnabled();
    }

    public static void log(Object message)
    {
        Log.VDCLogger.debug(message);
    }

    public static void log(Object message, Throwable throwable)
    {
        Log.VDCLogger.debug(message, throwable);
    }
}
