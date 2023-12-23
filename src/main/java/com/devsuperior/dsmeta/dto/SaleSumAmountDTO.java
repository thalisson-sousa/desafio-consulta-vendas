package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;

public class SaleSumAmountDTO {

    public SaleSumAmountDTO() {}

    private String name;
    private Double sumAmount;

    public SaleSumAmountDTO(String name, Double sumAmount) {
        this.name = name;
        this.sumAmount = sumAmount;
    }

    public SaleSumAmountDTO(Sale entity) {
        name = entity.getSeller().getName();
        sumAmount = entity.getAmount();
    }

    public String getName() {
        return name;
    }

    public Double getSumAmount() {
        return sumAmount;
    }
}
