Feature: 880$6345 - ALTERNATE GRAPHIC REPRESENTATION - PROJECTION CHARACTERISTICS OF MOVING IMAGE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6345-01$aсинерама"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 345 creates a bunch of Instance projectionCharacteristic properties
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:projectionCharacteristic ?y   |
      | ?y a bf:PresentationFormat          |
      | ?y rdfs:label "синерама"            |
    Then I should find 1 match



