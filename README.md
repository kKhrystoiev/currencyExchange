# Online Currency Exchange application automation tests.

## Overview
This is a Java project with e2e frontend tests for Online Currency Exchange application.

## Technologies
- Java
- TestNG
- Maven

## config.properties
This configuration file contains the next properties:
- browser (Chrome is set as default)
- url (Put here actual Online Currency Exchange application url)

## testng.xml
This is a configuration of what tests you want to run.

## How to run the tests
Run the next command from the root project directory:

`mvn clean test -DsuiteXmlFile=testng.xml`
