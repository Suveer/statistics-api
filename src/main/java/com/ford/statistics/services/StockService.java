package com.ford.statistics.services;

import com.ford.statistics.common.BasicConstants;
import com.ford.statistics.entities.DailyStockPrice;
import com.ford.statistics.exceptions.CustomValidationException;
import com.ford.statistics.models.AverageCloseModel;
import com.ford.statistics.models.DailyStockModel;
import com.ford.statistics.models.StockCloseRateModel;
import com.ford.statistics.repositories.DailyStockPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class StockService {

    private final DailyStockPriceRepository dailyStockPriceRepository;

    @Autowired
    public StockService(DailyStockPriceRepository dailyStockPriceRepository) {
        this.dailyStockPriceRepository = dailyStockPriceRepository;
    }

    public StockCloseRateModel getStockCloseRateOverTime(StockCloseRateModel stockCloseRateModel) throws CustomValidationException {

        if(stockCloseRateModel.getStartDate().isAfter(stockCloseRateModel.getEndDate())){
            throw new CustomValidationException(Arrays.asList(BasicConstants.START_DATE_SHOULD_BE_BEFORE_END_DATE));
        }

        List<DailyStockPrice> dailyStockPrices =
                dailyStockPriceRepository.findAllByDateBetweenOrderByDate(Date.valueOf(stockCloseRateModel.getStartDate()),
                        Date.valueOf(stockCloseRateModel.getEndDate()));

        stockCloseRateModel.getDailyStockList().addAll(getDailyStockListFromDailyStockPrice(dailyStockPrices));
        if(!stockCloseRateModel.getDailyStockList().isEmpty()) {
            calculateMaxAndMinClose(stockCloseRateModel);
        }
        return stockCloseRateModel;
    }

    public List<AverageCloseModel> getAverageCloseOverAPeriod(AverageCloseModel averageCloseModel) throws CustomValidationException{
        if(averageCloseModel.getYear()!=null && averageCloseModel.getMonth()!=null){
            return getAverageCloseOverAYearAMonth(averageCloseModel);
        } else if(averageCloseModel.getYear()!=null){
            return getAverageCloseOverAYear(averageCloseModel);
        } else {
           return getAverageCloseOverAllYears();
        }
    }

    private List<AverageCloseModel> getAverageCloseOverAllYears() {
        List<AverageCloseModel> averageCloseModelList = new ArrayList<>();
        TreeMap<Integer,List<DailyStockPrice>> dailyStockPriceListMap = new TreeMap<>(dailyStockPriceRepository.findAll().stream()
                .collect(Collectors.groupingBy(DailyStockPrice::getYear)));
        dailyStockPriceListMap.forEach((year, dailyStockPrices) -> averageCloseModelList.add(new AverageCloseModel(year, averageCloseFromList(dailyStockPrices))));
        return averageCloseModelList;
    }

    private List<AverageCloseModel> getAverageCloseOverAYear(AverageCloseModel averageCloseModel) throws CustomValidationException {
        if(averageCloseModel.getYear()<1 || averageCloseModel.getYear() > Year.now().getValue()){
            throw new CustomValidationException(Arrays.asList(BasicConstants.INVALID_YEAR_INPUT));
        }
        List<AverageCloseModel> averageCloseModelList = new ArrayList<>();
        dailyStockPriceRepository.findAllByYearEquals(averageCloseModel.getYear()).stream()
                .collect(Collectors.groupingBy(DailyStockPrice::getMonth))
                .forEach((month, dailyStockPrices) -> averageCloseModelList.add(new AverageCloseModel(averageCloseModel.getYear(),month, averageCloseFromList(dailyStockPrices))));
        return averageCloseModelList;
    }

    private List<AverageCloseModel> getAverageCloseOverAYearAMonth(AverageCloseModel averageCloseModel) throws CustomValidationException {
        List<String> validationErrors = new ArrayList<>();
        if(averageCloseModel.getYear()<1 || averageCloseModel.getYear() > Year.now().getValue()){
            validationErrors.add(BasicConstants.INVALID_YEAR_INPUT);
        }
        if(averageCloseModel.getMonth()<1 || averageCloseModel.getMonth()>12){
            validationErrors.add(BasicConstants.INVALID_MONTH_INPUT);
        }
        if(!validationErrors.isEmpty()){
            throw new CustomValidationException(validationErrors);
        }
        List<AverageCloseModel> averageCloseModelList = new ArrayList<>();
        dailyStockPriceRepository.findAllByYearEqualsAndMonthEquals(averageCloseModel.getYear(),averageCloseModel.getMonth()).stream()
                .map(dailyStockPrice -> new AverageCloseModel(dailyStockPrice.getYear(), dailyStockPrice.getMonth(), dailyStockPrice.getDay(), dailyStockPrice.getClose()))
                .forEach(averageCloseModelList::add);
        return averageCloseModelList;
    }

    private void calculateMaxAndMinClose(StockCloseRateModel stockCloseRateModel) {
        stockCloseRateModel.setMaxClose(stockCloseRateModel.getDailyStockList().get(0).getClose());
        stockCloseRateModel.setMinClose(stockCloseRateModel.getDailyStockList().get(0).getClose());
        stockCloseRateModel.getDailyStockList().parallelStream().forEach(dailyStockModel -> {
            if (stockCloseRateModel.getMaxClose().compareTo(dailyStockModel.getClose()) == -1) {
                stockCloseRateModel.setMaxClose(dailyStockModel.getClose());
            }
            if (stockCloseRateModel.getMinClose().compareTo(dailyStockModel.getClose()) == 1) {
                stockCloseRateModel.setMinClose(dailyStockModel.getClose());
            }
        });
    }

    private List<DailyStockModel> getDailyStockListFromDailyStockPrice(List<DailyStockPrice> dailyStockPrices) {
        return dailyStockPrices.stream()
                .map(dailyStockPrice -> new DailyStockModel(dailyStockPrice.getDate().toLocalDate(),
                        dailyStockPrice.getOpen(),
                        dailyStockPrice.getHigh(),
                        dailyStockPrice.getLow(),
                        dailyStockPrice.getClose(),
                        dailyStockPrice.getAdjustedClose(),
                        dailyStockPrice.getVolume()))
                .collect(Collectors.toList());
    }

    private BigDecimal averageCloseFromList(List<DailyStockPrice> dailyStockPrices){
        BigDecimal sum = dailyStockPrices.parallelStream()
                .map(DailyStockPrice::getClose)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(new BigDecimal(dailyStockPrices.size()),BigDecimal.ROUND_HALF_DOWN);
    }
}
