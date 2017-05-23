Feature: 880$6306 - ALTERNATE GRAPHIC REPRESENTATION - PLAYING TIME
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6306-01$a002016"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 306 creates a duration property of the Instance" test="//bf:Instance[1]/bf:duration[1] = '002016'"/>
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:duration "002016"             |
    Then I should find 1 match
