Feature: 880$6346 - ALTERNATE GRAPHIC REPRESENTATION - VIDEO CHARACTERISTICS
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6346-01$aБета"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 346 creates Instance videoCharacteristic properties
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:videoCharacteristic ?y        |
      | ?y a bf:VideoFormat                 |
      | ?y rdfs:label "Бета"                |
    Then I should find 1 match

