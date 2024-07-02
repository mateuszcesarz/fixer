# Fixer

## Setup
Open the fixer.properties file and replace YOUR_API_KEY with your actual Fixer API key:
key=YOUR_API_KEY

Since this is a project for testing purposes only, here is an apikey that should work for few runs (because of the limits)

VVQ2VFBhV1NGN1dtWm1MOUV6ZHphNGZtVzhGUnhSQ0g=   (this is encoded in Base64, feel free to use any online decoder)

## Run Automated Tests
To run the tests use maven command:
```  
mvn clean test  
```

or just press run on the RunCucumberTest class, should also do the job

## Generate Test Report
Allure framework is used to generate report from test results - https://github.com/allure-framework/allure-maven

To serve test report on local machine make sure you have run tests using `mvn clean test` command before, then use below command:
```  
allure serve target/allure-results
(IT REQUIRES ALLURE ON YOUR LOCAL MACHINE IN ORDER TO RUN)[i thinnk]
```
