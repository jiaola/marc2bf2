Feature: 880$6352 - ALTERNATE GRAPHIC REPRESENTATION - DIGITAL GRAPHIC REPRESENTATION
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6352-01$aВектор."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 352 creates a digitalCharacteristic property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:digitalCharacteristic ?y      |
      | ?y a bf:CartographicDataType        |
      | ?y rdfs:label "Вектор"              |
    Then I should find 1 match

