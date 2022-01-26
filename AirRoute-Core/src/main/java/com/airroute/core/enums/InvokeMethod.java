package com.airroute.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Anthony
 * @create 2022/1/26
 * @desc
 **/
@Getter
@AllArgsConstructor
public enum InvokeMethod {
    SYNC("SYNC"),
    ASYNC("ASYNC");
    private String desc;
}
