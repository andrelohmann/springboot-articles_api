Feature: SmokeTesting the articles api
  Fetch empty articles, create, read, update, delete articles

  Scenario: Check the health endpoint
    Given Endpoint path is set to "actuator/health"
    When I set a GET/DELETE request HEADER
    And I set the RestTemplate
    And I send a dataless "GET" HTTP request
    Then I receive http status "OK"
    And I receive health status UP
    
  Scenario: Fetch empty articles
    Given Endpoint path is set to "articles/"
    When I set a GET/DELETE request HEADER
    And I set the RestTemplate
    And I send a dataless "GET" HTTP request
    Then I receive http status "OK"
    And I observe log code "4000"
    And I receive an empty articles list response

  Scenario: Fetch not existing article
    Given Endpoint path is set to "articles/1"
    When I set a GET/DELETE request HEADER
    And I set the RestTemplate
    And I send a dataless "GET" HTTP request
    Then I receive http status "INTERNAL_SERVER_ERROR"
 
#  Scenario: Send authenticated test notification
#    Given I set the notification endpoint url
#    When I set request HEADER
#    And I set authenticated RestTemplate
#    And Send a POST HTTP request
#    And I receive valid response
#    Then I also receive Awaiting ID data Email
