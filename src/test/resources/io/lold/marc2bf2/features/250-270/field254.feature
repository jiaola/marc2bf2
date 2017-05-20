Feature: 254 - MUSICAL PRESENTATION STATEMENT

  Scenario: 250 creates an note property of the Instance
    Given a marc field "=254  \\$aFull score."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field254Converter
    When I search with patterns:
      | ?x a bf:Instance           |
      | ?x bf:note ?y              |
      | ?y a bf:Note               |
      | ?y rdfs:label "Full score" |
    Then I should find 1 match
