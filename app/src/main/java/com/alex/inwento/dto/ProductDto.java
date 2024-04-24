package com.alex.inwento.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {
    private int id;
    private String title;
    private String description;
    private String bar_code;
    private Double price;
    private String liable;
    private String receiver;
    private String branch;


}
