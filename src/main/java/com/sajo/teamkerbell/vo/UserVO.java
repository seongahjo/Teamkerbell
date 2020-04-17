package com.sajo.teamkerbell.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserVO {
    @NotNull
    @Size(min = 4, max = 10)
    private String id;
    @NotNull
    @Size(min = 4, max = 10)
    private String pw;
    @NotNull
    private String name;

    public UserVO(String id, String pw, String name) {
        this.id = id;
        this.pw = pw;
        this.name = name;
    }
}
