package com.csp.springbootjwt.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    private int id;
    private String username;
    private String userpwd;
}
