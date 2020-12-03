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
    And I observe log code 4000
    And I receive a list of 0 articles

  Scenario: Fetch not existing article
    Given Endpoint path is set to "articles/1"
    When I set a GET/DELETE request HEADER
    And I set the RestTemplate
    And I send a dataless "GET" HTTP request
    Then I receive http status "INTERNAL_SERVER_ERROR"
    And I observe log code 5000 "Could not find article id '1'"

  Scenario: Create one article
    Given Endpoint path is set to "articles/"
    When I set a POST/PUT request HEADER
    And I set the RestTemplate
    And I preload jsonBody from file "article.json"
    And I send a data "POST" HTTP request
    Then I receive http status "OK"
    And I observe log code 4001
    And I receive the first article
  
  Scenario: Create multiple articles
    Given Endpoint path is set to "articles/"
    When I set a POST/PUT request HEADER
    And I set the RestTemplate
    And I preload jsonBody with article "<title>" "<content>"
    And I send a data "POST" HTTP request
    Then I receive http status "OK"
    And I observe log code 4001 "Create new article '<title>'"
    And I receive the article <id> "<title>" "<content>"
  Examples:
    | id | title             | content                                   |
    | 2  | My second Article | This is the content of the second article |
    | 3  | My third Article  | This is the content of the third article  |
    | 4  | My fourth Article | This is the content of the fourth article |
  
  Scenario: Fetch articles list
    Given Endpoint path is set to "articles/"
    When I set a GET/DELETE request HEADER
    And I set the RestTemplate
    And I send a dataless "GET" HTTP request
    Then I receive http status "OK"
    And I observe log code 4000
    And I receive a list of 4 articles
  
  Scenario: Update multiple articles
    Given Endpoint path is set to "articles/<id>"
    When I set a POST/PUT request HEADER
    And I set the RestTemplate
    And I preload jsonBody with article <id> "<title>" "<content>"
    And I send a data "PUT" HTTP request
    Then I receive http status "OK"
    And I observe log code 4003 "Update article id '<id>'"
    And I receive the article <id> "<title>" "<content>"
  Examples:
    | id | title                     | content                                           |
    | 2  | My updated second Article | This is the updated content of the second article |
    | 3  | My updated third Article  | This is the updated content of the third article  |
    | 4  | My updated fourth Article | This is the updated content of the fourth article |

  Scenario: Fetch multiple articles
    Given Endpoint path is set to "articles/<id>"
    When I set a GET/DELETE request HEADER
    And I set the RestTemplate
    And I send a dataless "GET" HTTP request
    Then I receive http status "OK"
    And I observe log code 4002 "Return article id '<id>'"
    And I receive the article <id> "<title>" "<content>"
  Examples:
    | id | title                     | content                                           |
    | 2  | My updated second Article | This is the updated content of the second article |
    | 3  | My updated third Article  | This is the updated content of the third article  |
    | 4  | My updated fourth Article | This is the updated content of the fourth article |
  
  Scenario: Delete multiple articles
    Given Endpoint path is set to "articles/<id>"
    When I set a GET/DELETE request HEADER
    And I set the RestTemplate
    And I send a dataless "DELETE" HTTP request
    And I observe log code 4005 "Delete article id '<id>'"
    Then I receive http status "OK"
  Examples:
    | id |
    | 2  |
    | 3  |
    | 4  |
  
  Scenario: Fetch multiple deleted articles
    Given Endpoint path is set to "articles/<id>"
    When I set a GET/DELETE request HEADER
    And I set the RestTemplate
    And I send a dataless "GET" HTTP request
    Then I receive http status "INTERNAL_SERVER_ERROR"
    And I observe log code 5000 "Could not find article id '<id>'"
  Examples:
    | id |
    | 2  |
    | 3  |
    | 4  |
  
  Scenario: Fetch articles list
    Given Endpoint path is set to "articles/"
    When I set a GET/DELETE request HEADER
    And I set the RestTemplate
    And I send a dataless "GET" HTTP request
    Then I receive http status "OK"
    And I observe log code 4000
    And I receive a list of 1 article
  
  Scenario: Test the Log Reader
    Given Endpoint path is set to "articles/"
    When I set a GET/DELETE request HEADER
    And I set the RestTemplate
    And I send a dataless "GET" HTTP request
    Then I receive http status "OK"
    And I observe log code 4000
    And I receive a list of 1 article
    And I send a dataless "GET" HTTP request
    Then I receive http status "OK"
    And I observe log code 4000
    And I receive a list of 1 article
    And I send a dataless "GET" HTTP request
    Then I receive http status "OK"
    And I observe log code 4000
    And I receive a list of 1 article
    And I send a dataless "GET" HTTP request
    Then I receive http status "OK"
    And I observe log code 4000
    And I receive a list of 1 article
    And I send a dataless "GET" HTTP request
    Then I receive http status "OK"
    And I observe log code 4000
    And I receive a list of 1 article
    Given Endpoint path is set to "articles/1"
    When I set a GET/DELETE request HEADER
    And I set the RestTemplate
    And I send a dataless "GET" HTTP request
    Then I receive http status "OK"
    And I observe log code 4002
    Given Endpoint path is set to "articles/2"
    When I set a GET/DELETE request HEADER
    And I set the RestTemplate
    And I send a dataless "GET" HTTP request
    Then I receive http status "INTERNAL_SERVER_ERROR"
    And I observe log code 5000
    And I print all LogLines
