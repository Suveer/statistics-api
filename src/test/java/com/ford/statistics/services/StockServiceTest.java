package com.ford.statistics.services;

import com.ford.statistics.entities.DailyStockPrice;
import com.ford.statistics.exceptions.CustomValidationException;
import com.ford.statistics.models.AverageCloseModel;
import com.ford.statistics.models.StockCloseRateModel;
import com.ford.statistics.repositories.DailyStockPriceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class StockServiceTest {

    @Mock
    private DailyStockPriceRepository dailyStockPriceRepository;

    @InjectMocks
    private StockService stockService;


    @Test
    public void getStockCloseRateOverTime() throws CustomValidationException {
        StockCloseRateModel stockCloseRateModel = new StockCloseRateModel(LocalDate.of(1985,1,1),
                LocalDate.of(1986,1,1));

        when(dailyStockPriceRepository.findAllByDateBetweenOrderByDate(any(Date.class),any(Date.class)))
                .thenReturn(getSampleData());
        stockService.getStockCloseRateOverTime(stockCloseRateModel);
    }

    @Test(expected = CustomValidationException.class)
    public void getStockCloseRateOverTimeException() throws CustomValidationException {
        StockCloseRateModel stockCloseRateModel = new StockCloseRateModel(LocalDate.of(1987,1,1),
                LocalDate.of(1986,1,1));
        stockService.getStockCloseRateOverTime(stockCloseRateModel);
    }

    @Test
    public void getAverageCloseOverAllYears() throws CustomValidationException {
        when(dailyStockPriceRepository.findAll())
                .thenReturn(getSampleData());
        stockService.getAverageCloseOverAPeriod(new AverageCloseModel());
    }

    @Test
    public void getAverageCloseOverAYear() throws CustomValidationException {
        AverageCloseModel averageCloseModel = new AverageCloseModel(1985);
        when(dailyStockPriceRepository.findAllByYearEquals(any(Integer.class)))
                .thenReturn(getSampleData());
        stockService.getAverageCloseOverAPeriod(averageCloseModel);
    }

    @Test(expected = CustomValidationException.class)
    public void getAverageCloseOverAYearException() throws CustomValidationException {
        AverageCloseModel averageCloseModel = new AverageCloseModel(0);
        stockService.getAverageCloseOverAPeriod(averageCloseModel);
    }

    @Test
    public void getAverageCloseOverAYearAMonth() throws CustomValidationException {
        AverageCloseModel averageCloseModel = new AverageCloseModel(1985,1);
        when(dailyStockPriceRepository.findAllByYearEqualsAndMonthEquals(any(Integer.class),any(Integer.class)))
                .thenReturn(getSampleData());
        stockService.getAverageCloseOverAPeriod(averageCloseModel);
    }

    @Test(expected = CustomValidationException.class)
    public void getAverageCloseOverAYearAMonthException() throws CustomValidationException {
        AverageCloseModel averageCloseModel = new AverageCloseModel(2345,13);
        stockService.getAverageCloseOverAPeriod(averageCloseModel);
    }

    private List<DailyStockPrice> getSampleData(){
        List<DailyStockPrice> dailyStockPriceList = new ArrayList<>();
        dailyStockPriceList.add(new DailyStockPrice(Date.valueOf( "1985-01-01"),
                1985,1,1,new BigDecimal(1.5),new BigDecimal(1.5),new BigDecimal(1.5),new BigDecimal(1.5),new BigDecimal(1.5),1));
        dailyStockPriceList.add(new DailyStockPrice(Date.valueOf( "1985-01-02"),
                1985,1,2,new BigDecimal(1.5),new BigDecimal(1.5),new BigDecimal(1.5),new BigDecimal(1.75),new BigDecimal(1.5),1));
        dailyStockPriceList.add(new DailyStockPrice(Date.valueOf( "1985-01-03"),
                1985,1,3,new BigDecimal(1.5),new BigDecimal(1.5),new BigDecimal(1.5),new BigDecimal(1.25),new BigDecimal(1.5),1));
        return dailyStockPriceList;
    }
}