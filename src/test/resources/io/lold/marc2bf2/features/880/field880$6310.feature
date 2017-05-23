Feature: 880$6310 - ALTERNATE GRAPHIC REPRESENTATION - CURRENT PUBLICATION FREQUENCY
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6310-01$aгодовой,$b1983-"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 310 creates a frequency property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:frequency ?y                  |
      | ?y a bf:Frequency                   |
      | ?y rdfs:label "годовой"             |
    Then I should find 1 match

