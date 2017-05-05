Feature: MARC field 345 - PROJECTION CHARACTERISTICS OF MOVING IMAGE
  Background:
    Given a marc field "=345  \\$3filmstrip$aCinerama$b24 fps$2rda"
    When converted by a field converter io.lold.marc2bf2.converters.Field345Converter

  Scenario: $a creates a projectionCharacteristic/PresentationFormat property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                  |
      | ?x bf:projectionCharacteristic ?y |
      | ?y a bf:PresentationFormat        |
      | ?y rdfs:label "Cinerama"          |
    Then I should find 1 match

  Scenario: $b creates a projectionCharacteristic/ProjectionSpeed property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                  |
      | ?x bf:projectionCharacteristic ?y |
      | ?y a bf:ProjectionSpeed           |
      | ?y rdfs:label "24 fps"            |
    Then I should find 1 match

  Scenario: $2 creates a source property on generated Resources
    When I search with patterns:
      | ?x a bf:PresentationFormat        |
      | ?x bf:source ?y                   |
      | ?y a bf:Source                    |
      | ?y rdfs:label "rda"               |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property on generated Resources
    When I search with patterns:
      | ?x a bf:PresentationFormat        |
      | ?x bflc:appliesTo ?y              |
      | ?y a bflc:AppliesTo               |
      | ?y rdfs:label "filmstrip"         |
    Then I should find 1 match
