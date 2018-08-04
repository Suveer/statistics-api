package com.ford.statistics.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class DailyStockPrice {
    private Date date;
    private Integer year;
    private Integer month;
    private Integer day;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal adjustedClose;
    private Integer volume;

    @Id
    @Column(name = "Date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    @Basic
    @Column(name = "Year", nullable = false)
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
    @Basic
    @Column(name = "Month", nullable = false)
    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    @Basic
    @Column(name = "Day", nullable = false)
    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    @Basic
    @Column(name = "StockOpen", nullable = false)
    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    @Basic
    @Column(name = "High", nullable = false)
    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    @Basic
    @Column(name = "Low", nullable = false)
    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    @Basic
    @Column(name = "StockClose", nullable = false)
    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    @Basic
    @Column(name = "AdjustedClose", nullable = false)
    public BigDecimal getAdjustedClose() {
        return adjustedClose;
    }

    public void setAdjustedClose(BigDecimal adjustedClose) {
        this.adjustedClose = adjustedClose;
    }

    @Basic
    @Column(name = "Volume", nullable = false)
    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }
}
