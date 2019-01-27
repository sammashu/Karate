Feature: Sample Karate API AUTOMATION TEST

  Background:
    * url baseUrl


    Scenario: Get all states of a country
      Given path 'IND/all'
      When method GET
      Then status 200
      And match response.RestResponse.result == '#notnull'
      And assert response.RestResponse.result.length == 36