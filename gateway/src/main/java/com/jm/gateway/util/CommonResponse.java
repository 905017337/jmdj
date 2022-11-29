package com.jm.gateway.util;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CommonResponse   {
    private int code;
    private String msg;

    public CommonResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private static CommonResponse fail(int code,String msg){
        return  new CommonResponse(code,msg);
    }
}
