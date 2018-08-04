Drop table Daily_Stock_Price;
CREATE TABLE Daily_Stock_Price (
  Date          DATE NOT NULL,
  Year          INTEGER(4) NOT NULL,
  Month          INTEGER(2) NOT NULL,
  Day           INTEGER(2) NOT NULL,
  Stock_Open     DECIMAL(20, 10) NOT NULL,
  High          DECIMAL(20, 10) NOT NULL,
  Low           DECIMAL(20, 10) NOT NULL,
  Stock_Close    DECIMAL(20, 10) NOT NULL,
  Adjusted_Close DECIMAL(20, 10) NOT NULL,
  Volume        INTEGER(20) NOT NULL
)
  AS
    SELECT parsedatetime(Date,'M/d/y') as Date,
      Year(parsedatetime(Date,'M/d/y')) as Year,
      Month(parsedatetime(Date,'M/d/y')) as Month,
      DAY_OF_MONTH(parsedatetime(Date,'M/d/y')) as Day,
      Open as Stock_Open, High, Low, Close as Stock_Close, "Adj Close" as Adjusted_Close, Volume FROM CSVREAD ('classpath:F.CSV');
create INDEX year_index on Daily_Stock_Price(year);
create INDEX month_index on Daily_Stock_Price(month);