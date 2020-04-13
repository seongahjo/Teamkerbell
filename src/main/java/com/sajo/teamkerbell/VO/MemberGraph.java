package com.sajo.teamkerbell.VO;

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

    public MemberGraph() {
    }
    public MemberGraph(Integer useridx,String name,BigDecimal percentage) {
        this.useridx=useridx;
        this.name=name;
        this.percentage=percentage;
    }


}
