package com.snowflake.utils;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.io.IoUtil;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义工具类
 */
public class CustomizedServletUtil {

    /**
     * 重写hutool方法，因为它最新版本为提供支持jakarta
     * @param response
     * @param text
     * @param contentType
     */
    public static void write(HttpServletResponse response, String text, String contentType){
        response.setContentType(contentType);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(text);
            writer.flush();
        }catch (IOException e){
            throw new UtilException(e);
        }finally {
            IoUtil.close(writer);
        }
    }
}
