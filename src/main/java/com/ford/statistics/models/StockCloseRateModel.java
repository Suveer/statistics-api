package com.ford.statistics.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ford.statistics.common.BasicConstants;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StockCloseRateModel {

    private BigDecimal maxClose;
    private BigDecimal minClose;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BasicConstants.DATE_FORMAT)
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BasicConstants.DATE_FORMAT)
    private LocalDate endDate;
    List<DailyStockModel> dailyStockList = new ArrayList<>();

    public StockCloseRateModel(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
