Feature: 306 - PLAYING TIME
  Background:
    Given a marc field "=306  \\$a002016"
    When converted by a field converter io.lold.marc2bf2.converters.field3xx.Field306Converter

  Scenario: 306 creates a duration property of the Instance
    When I search with patterns:
      | ?x a bf:Instance |
      | ?x bf:duration "002016" |
    Then I should find matches
