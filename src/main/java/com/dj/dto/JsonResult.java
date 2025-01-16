package com.dj.dto;

import lombok.Data;

@Data
public class JsonResult<T> {

    private int code;
    private String message;
    private T result;

    public static <T> JsonResult<T> success(T result) {
        JsonResult<T> jsonResult = new JsonResult<>();
        jsonResult.setCode(200);
        jsonResult.setMessage("success");
        jsonResult.setResult(result);
        return jsonResult;
    }
}
