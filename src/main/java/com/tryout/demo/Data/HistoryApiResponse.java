package com.tryout.demo.Data;

import lombok.Data;

@Data
public class HistoryApiResponse {
    private int results;
    private History[] response;
}
