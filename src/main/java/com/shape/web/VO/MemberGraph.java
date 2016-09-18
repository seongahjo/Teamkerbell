package com.shape.web.VO;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by hootting on 2016. 3. 7..
 */
@Data
public class MemberGraph {
    private Integer useridx;
    private String name;
    private BigDecimal percentage;
    private BigDecimal participate;

    public MemberGraph() {
    }


}
