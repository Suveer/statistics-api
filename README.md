# statistics-api

## Introduction

This project is an RESTful API project for Ford Motor Company in order to get their Daily Stock Prices from 22years

## Overview

In this project, H2, an in memory database is used. Initially on the starting of the project, after initialising JPA Entity Manager, Spring Boot searches for data.sql file to import data for initial DB Setup. So in here our code to create table and import CSV data occurs(Even adding indexes to some columns happen).

## Key Features

	Spring Boot v2.0.4.RELEASE Application
	Java 8 features
	H2 in-memory Database
	Centralized Exception Handling
	Single Error JSON Bean
	Input validations
	Swagger UI 
	log4j2 with Rolling file appender
	JUnits with 100% Code Coverage

## Build the project

Build the jar by using below command in root folder.
	
	mvn clean package
	
## Run as standalone

Run below command to run application as a standalone
	
	java -jar target/statistics-api-0.0.1-SNAPSHOT.jar

## Database

H2 is one of the popular in memory databases. Spring Boot has very good integration for H2.
H2 is a relational database management system written in Java. It can be embedded in Java applications or run in the client-server mode.
H2 supports a sub set of the SQL standard.
H2 also provides a web console to maintain the database.

**To Check Database**
	http://localhost:8080/h2-console
Change JDBC URL to jdbc:h2:mem:testdb and click connect

## Swagger-UI
Swagger 2 is an open-source project used to describe and document RESTful APIs. Swagger 2 is language-agnostic and is extensible into new technologies and protocols beyond HTTP. The current version defines a set HTML, JavaScript, and CSS assets to dynamically generate documentation from a Swagger-compliant API. These files are bundled by the Swagger UI project to display the API on the browser. Besides rendering documentation, Swagger UI allows other API developers or consumers to interact with the API’s resources without having any of the implementation logic in place.

The Swagger 2 specification, which is known as OpenAPI specification, has several implementations. Currently, Springfox that has replaced Swagger-SpringMVC (Swagger 1.2 and older) is popular for Spring Boot applications. Springfox supports both Swagger 1.2 and 2.0.

**Below is the URL for this project.**
	http://localhost:8080/swagger-ui.html

## PostMan Endpoint

GET Request for http://localhost:8080/close-rate-over-time?startDate={startDate}&endDate={endDate} Close rate over a period of time, inputs are startDate and endDate (Dates in MM-dd-yyyy format)
 
GET Request for http://localhost:8080/average-close-over-a-period Average Close over a period, period is year.

GET Request for http://localhost:8080/average-close-over-a-period?year={year} Average Close over a period, period is all the months in a year

GET Request for http://localhost:8080/average-close-over-a-period?year={year}&month={month} Average Close over a period, period is all the days in a month and a year
