package com.shape.web.VO;

import java.math.BigDecimal;

/**
 * Created by hootting on 2016. 3. 7..
 */
public class MemberGraph {
    private Integer useridx;
    private String name;
    private BigDecimal participate;

    public MemberGraph() {
    }



    public Integer getUseridx() {
        return useridx;
    }

    public void setUseridx(Integer useridx) {
        this.useridx = useridx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getParticipate() {
        return participate;
    }

    public void setParticipate(BigDecimal participate) {
        this.participate = participate;
    }
}
