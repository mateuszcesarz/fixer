Feature: Fetching Historical Exchange Rates Data
#
#  Scenario: Retrieve historical exchange rates between two dates
#    Given I am authenticated to Fixer
#    When I request historical exchange rates data between two periods of time
#    Then the API should respond with status code 200
#    And the response should contain exchange rates data for the specified period
#
#  Scenario Outline: Retrieve daily historical rates within a specified period
#    Given I am authenticated to Fixer
#    When I request daily historical rates between "<start_date>" and "<end_date>"
#    Then the API should respond with status "<is_success>"
#    Examples:
#      | start_date  | end_date    | is_success   | case               |
#      | 2023-01-01  | 2023-12-31  | true         | Exactly 365 days   |
#      | 2023-01-01  | 2023-06-30  | true         | Less than 365 days |
#      | 2023-01-01  | 2024-01-07  | false        | More than 365 days |
#
#  Scenario Outline: Handle invalid date formats
#    Given I am authenticated to Fixer
#    When I request daily historical rates between "<start_date>" and "<end_date>"
#    Then the API should respond with an error code 502
#    Examples:
#      | start_date   | end_date     |
#      | 03-22-1999 | 03-25-1999 |
#      | 2023/01/01 | 2023/01/05 |
#      | 03/22/1999 | 03/27/1999 |
#
#  Scenario: Handle missing or invalid parameters
#    Given I am authenticated to Fixer
#    When I request historical exchange rate s data without providing start_date
#    Then the API should respond with an error code 501
#
#  Scenario: Handle missing or invalid parameters
#    Given I am authenticated to Fixer
#    When I request historical exchange rate s data without providing end_date
#    Then the API should respond with an error code 501
#
#
#  Scenario: Handle unauthorized access
#    Given I am not authenticated to Fixer
#    When I request historical exchange rates data between two periods of time
#    Then the API should respond with status code 401
#
#  Scenario: Handle rate limiting
#    Given I use depleted apikey
#    When I request historical exchange rates data between two periods of time
#    Then the API should respond with status code 429

  Scenario: Handle invalid base symbols
    Given I am authenticated to Fixer
    When I request historical exchange rates data for a non-existing symbol "aaa,bbb,ccc"
    Then the API should validate against invalid symbols

  Scenario: Handle invalid base
    Given I am authenticated to Fixer
    When I request historical exchange rates data for a non-existing base "aaa"
    Then the API should validate against invalid base