package com.ford.statistics.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ford.statistics.common.BasicConstants;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DailyStockModel {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BasicConstants.DATE_FORMAT)
    private LocalDate date;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal adjustedClose;
    private Integer volume;
}
