package com.tryout.demo.Data;

import lombok.Data;

@Data
public class History {
    private String country;
    private String day;
    private Cases cases;
    private Deaths deaths;
    private Tests tests;
}
