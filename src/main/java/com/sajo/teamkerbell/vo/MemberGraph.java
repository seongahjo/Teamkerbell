package com.sajo.teamkerbell.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by hootting on 2016. 3. 7..
 */
@Data
public class MemberGraph {
    private Integer userId;
    private String name;
    private BigDecimal percentage;

    public MemberGraph() {
    }

    public MemberGraph(Integer userId, String name, BigDecimal percentage) {
        this.userId = userId;
        this.name = name;
        this.percentage = percentage;
    }


}
