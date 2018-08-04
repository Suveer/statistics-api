package com.ford.statistics.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AverageCloseModel {

    private Integer year;
    private Integer month;
    private Integer day;
    private BigDecimal averageClose;

    public AverageCloseModel(Integer year) {
        this.year = year;
    }

    public AverageCloseModel(Integer year, Integer month) {
        this.year = year;
        this.month = month;
    }

    public AverageCloseModel(Integer year, BigDecimal averageClose) {
        this.year = year;
        this.averageClose = averageClose;
    }

    public AverageCloseModel(Integer year, Integer month, BigDecimal averageClose) {
        this.year = year;
        this.month = month;
        this.averageClose = averageClose;
    }
}
