Feature: 880$6521 - TARGET AUDIENCE NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  4\$6521-01$aУмеренно мотивировано."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 521 creates an intendedAudience property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:intendedAudience ?y           |
      | ?y a bf:IntendedAudience            |
      | ?y rdfs:label "Умеренно мотивировано." |
    Then I should find 1 match

