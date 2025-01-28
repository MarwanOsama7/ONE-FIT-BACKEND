package com.global.ProjectManagement.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PromoCodeResponse {
    private String message;
    private double discount;
}
