package com.global.ProjectManagement.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSizeUpdateDto {
    private Long sizeId;
    private Long colorId;
    private Integer stockQuantity;
}