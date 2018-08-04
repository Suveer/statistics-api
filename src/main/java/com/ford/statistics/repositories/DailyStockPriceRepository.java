package com.ford.statistics.repositories;

import com.ford.statistics.entities.DailyStockPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface DailyStockPriceRepository  extends JpaRepository<DailyStockPrice,Date> {
    List<DailyStockPrice> findAllByDateBetweenOrderByDate(Date startDate, Date endDate);
    List<DailyStockPrice> findAllByYearEquals(Integer year);
    List<DailyStockPrice> findAllByYearEqualsAndMonthEquals(Integer year, Integer month);
}
