Feature: 257 - COUNTRY OF PRODUCING ENTITY
  
  Background:
    Given a marc field "=257  \\$aFrance$aGermany$aItaly$2naf"
    When converted by a field converter io.lold.marc2bf2.converters.field250to270.Field257Converter

  Scenario: 257 creates a provisionActivity property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:provisionActivity ?y         |
      | ?y a bf:Production                 |
      | ?y bf:place ?z                     |
      | ?z a bf:Place                      |
      | ?z rdfs:label "France"             |
    Then I should find 1 match

  Scenario: $2 creates a source property of the Place
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:provisionActivity ?y         |
      | ?y a bf:Production                 |
      | ?y bf:place ?z                     |
      | ?z a bf:Place                      |
      | ?z bf:source ?s                    |
      | ?s a bf:Source                     |
      | ?s rdfs:label "naf"                |
    Then I should find 3 matches

    