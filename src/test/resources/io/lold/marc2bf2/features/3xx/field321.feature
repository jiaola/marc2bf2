Feature: 321 - FORMER PUBLICATION FREQ
  Background:
    Given a marc field "=321  \\$aSemiannual,$b1981-1982"
    When converted by a field converter io.lold.marc2bf2.converters.Field310_321Converter

  Scenario: 321 creates a frequency property of the Instance
    When I search with patterns:
      | ?x a bf:Instance           |
      | ?x bf:frequency ?y         |
      | ?y a bf:Frequency          |
      | ?y rdfs:label "Semiannual" |
    Then I should find matches

  Scenario: $b creates a date property of the Frequency
    When I search with patterns:
      | ?x a bf:Instance       |
      | ?x bf:frequency ?y     |
      | ?y a bf:Frequency      |
      | ?y bf:date "1981-1982" |
    Then I should find matches
