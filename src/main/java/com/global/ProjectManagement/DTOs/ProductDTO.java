package com.global.ProjectManagement.DTOs;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.global.ProjectManagement.Dto.ImageDataDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
    private String name;
    private String description;
    private double price;
    private double discount;
    private double priceAfterDiscount;
    private Set<ImageDataDto> images;
    private List<ColorGroupedSizesDTO> productSizes;

    public ProductDTO(Long id, String name,String description, double price, double discount, double priceAfterDiscount, 
                      Set<ImageDataDto> images, List<ColorGroupedSizesDTO> sizes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.priceAfterDiscount = priceAfterDiscount;
        this.images = images;
        this.productSizes = sizes;
    }
}
