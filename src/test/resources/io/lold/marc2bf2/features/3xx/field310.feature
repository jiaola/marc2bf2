Feature: 310 - CURRENT PUBLICATION FREQUENCY
  Background:
    Given a marc field "=310  \\$aAnnual,$b1983-"
    When converted by a field converter io.lold.marc2bf2.converters.field3XX.Field310_321Converter

  Scenario: 310 creates a frequency property of the Instance
    When I search with patterns:
      | ?x a bf:Instance       |
      | ?x bf:frequency ?y     |
      | ?y a bf:Frequency      |
      | ?y rdfs:label "Annual" |
    Then I should find matches

  Scenario: $b creates a date property of the Frequency
    When I search with patterns:
      | ?x a bf:Instance       |
      | ?x bf:frequency ?y     |
      | ?y a bf:Frequency      |
      | ?y bf:date "1983-"     |
    Then I should find matches
