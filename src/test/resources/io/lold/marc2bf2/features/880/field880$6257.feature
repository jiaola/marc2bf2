Feature: 880$6257 - ALTERNATE GRAPHIC REPRESENTATION - COUNTRY OF PRODUCING ENTITY
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6257-01$aФранция"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 257 creates a provisionActivity property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:provisionActivity ?y          |
      | ?y a bf:Production                  |
      | ?y bf:place ?z                      |
      | ?z a bf:Place                       |
      | ?z rdfs:label "Франция"             |
    Then I should find 1 match
