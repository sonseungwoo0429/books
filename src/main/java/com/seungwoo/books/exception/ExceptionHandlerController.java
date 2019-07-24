package com.seungwoo.books.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by Leo.
 * User: ssw
 * Date: 2019-07-22
 * Time: 10:33
 */
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ReduplicationUsernameException.class)
    public String reduplicationUsernameException(ReduplicationUsernameException e) {
        return "redirect:/member_join?reduplication";
    }
}
