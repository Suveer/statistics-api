package com.ford.statistics.controllers;

import com.ford.statistics.common.BasicConstants;
import com.ford.statistics.exceptions.CustomValidationException;
import com.ford.statistics.models.AverageCloseModel;
import com.ford.statistics.models.StockCloseRateModel;
import com.ford.statistics.services.StockService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @ApiOperation(value="Close rate over a period of time, inputs are startDate and endDate (Dates in "+BasicConstants.DATE_FORMAT+" format)")
    @GetMapping(value = "close-rate-over-time")
    public StockCloseRateModel getStockCloseRateOverTime(@Valid @RequestParam("startDate") @DateTimeFormat(pattern= BasicConstants.DATE_FORMAT) Date startDate,
                                                         @Valid @RequestParam("endDate") @DateTimeFormat(pattern= BasicConstants.DATE_FORMAT) Date endDate) throws CustomValidationException {
        return stockService.getStockCloseRateOverTime(new StockCloseRateModel(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                                                                                endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
    }

    @ApiOperation(value="Average Close over a period, period can be year, month or day")
    @GetMapping(value = "average-close-over-a-period")
    public List<AverageCloseModel> getAverageCloseOverAPeriod(@Valid @RequestParam(value = "year", required = false) Integer year,
                                                            @Valid @RequestParam(value = "month", required = false) Integer month) throws CustomValidationException {
        return stockService.getAverageCloseOverAPeriod(new AverageCloseModel(year, month));
    }
}
