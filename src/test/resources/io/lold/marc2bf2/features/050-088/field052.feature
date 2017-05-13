Feature: 052 - GEOGRAPHIC CLASSIFICATION

  Background:
    Given a marc field "=052  \\$a4034$bR4$bR8"
    And a marc field "=052  1\$aBK$dMostar"
    When converted by a field converter io.lold.marc2bf2.converters.field050to088.Field052Converter

  Scenario: 052 creates a geographicCoverage/Place property of the Work
    When I search with patterns:
      | ?x a bf:Work                |
      | ?x bf:geographicCoverage ?y |
      | ?y a bf:Place               |
    Then I should find 3 matches

  Scenario: ind1 = ' ' creates a source property of the Place
    When I search with patterns:
      | ?x a bf:Work                |
      | ?x bf:geographicCoverage ?y |
      | ?y a bf:Place               |
      | ?y bf:source <http://id.loc.gov/vocabulary/classSchemes/lcc> |
      | <http://id.loc.gov/vocabulary/classSchemes/lcc> a bf:Source  |
    Then I should find 2 matches

  Scenario: $ab creates an rdf:value property of the Place
    When I search with patterns:
      | ?x a bf:Work                |
      | ?x bf:geographicCoverage ?y |
      | ?y a bf:Place               |
      | ?y rdf:value "4034 R8"      |
    Then I should find 1 match

  Scenario: $d creates an rdfs:label property of the Place
    When I search with patterns:
      | ?x a bf:Work                |
      | ?x bf:geographicCoverage ?y |
      | ?y a bf:Place               |
      | ?y rdfs:label "Mostar"      |
    Then I should find 1 match
