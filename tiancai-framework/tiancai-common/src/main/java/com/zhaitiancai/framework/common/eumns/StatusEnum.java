package com.zhaitiancai.framework.common.eumns;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: zhaitiancai
 * @url: http://81.70.228.116/#/
 * @date: 2023-08-15 10:33
 * @description: 状态
 **/
@Getter
@AllArgsConstructor
public enum StatusEnum {
    // 启用
    ENABLE(0),
    // 禁用
    DISABLED(1);

    private final Integer value;
}