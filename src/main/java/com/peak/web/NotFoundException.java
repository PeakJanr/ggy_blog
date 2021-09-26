package com.peak.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 404页面访问异常 对象不存在
 * @PROJECT_NAME: ggy_Blog
 * @DESCRIPTION:
 * @USER: Peak_GGY
 * @DATE: 2021/9/4 11:08
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
