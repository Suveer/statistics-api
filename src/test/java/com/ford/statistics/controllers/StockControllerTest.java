package com.ford.statistics.controllers;

import com.ford.statistics.common.BasicConstants;
import com.ford.statistics.models.AverageCloseModel;
import com.ford.statistics.models.StockCloseRateModel;
import com.ford.statistics.services.StockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StockController.class)
public class StockControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StockService stockService;


    @Test
    public void getStockCloseRateOverTime() throws Exception {
        StockCloseRateModel stockCloseRateModel = new StockCloseRateModel(LocalDate.of(1987,1,1),
                                                            LocalDate.of(1988,1,1));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(BasicConstants.DATE_FORMAT);

        when(stockService.getStockCloseRateOverTime(any(StockCloseRateModel.class))).thenReturn(stockCloseRateModel);

        mvc.perform(get("/close-rate-over-time?startDate=1-1-1987&endDate=1-1-1988")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startDate", is(stockCloseRateModel.getStartDate().format(dateTimeFormatter))))
                .andExpect(jsonPath("$.endDate", is(stockCloseRateModel.getEndDate().format(dateTimeFormatter))));
    }

    @Test
    public void getAverageCloseOverAPeriod() throws Exception {

        AverageCloseModel averageCloseModel = new AverageCloseModel(1985);

        when(stockService.getAverageCloseOverAPeriod(any(AverageCloseModel.class))).thenReturn(Arrays.asList(averageCloseModel));

        mvc.perform(get("/average-close-over-a-period?year=1985")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].year", is(averageCloseModel.getYear())));
    }
}