Feature: SmokeTesting the articles api
  Fetch empty articles, create, read, update, delete articles

  Scenario: Fetch empty articles
    Given I set the endpoint url
    When I set request HEADER
    And I set the RestTemplate
    And I send a GET HTTP request
    And I parsed the regarding log message
    Then I receive an empty articles list response
 
#  Scenario: Send authenticated test notification
#    Given I set the notification endpoint url
#    When I set request HEADER
#    And I set authenticated RestTemplate
#    And Send a POST HTTP request
#    And I receive valid response
#    Then I also receive Awaiting ID data Email
