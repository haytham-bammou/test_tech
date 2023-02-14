package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiRequest {
    private String model = "text-davinci-003";
    private int max_tokens = 4000;
    private double temperature=1.0;
    private String prompt;
}
