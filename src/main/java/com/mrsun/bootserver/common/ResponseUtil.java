package com.mrsun.bootserver.common;

import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description
 *
 * @author sunxiaogang
 */
public class ResponseUtil {

    public static void out(HttpServletResponse response, Result result) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.HTTP_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try {
            objectMapper.writeValue(response.getOutputStream(), result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
