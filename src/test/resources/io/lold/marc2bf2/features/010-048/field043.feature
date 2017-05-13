Feature: 043 - GEOGRAPHIC AREA CODE

  Background:
    Given a marc field "=043  \\$as-bl---$bs-bl-ba$0(DE-101c)310008891$2BlRjBN$cus"
    When converted by a field converter io.lold.marc2bf2.converters.field010to048.Field043Converter

  Scenario: $a creates a geographicCoverage property of the Work
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:geographicCoverage <http://id.loc.gov/vocabulary/geographicAreas/s-bl> |
      | <http://id.loc.gov/vocabulary/geographicAreas/s-bl> a bf:GeographicCoverage  |
    Then I should find 1 match

  Scenario: $b creates a geographicCoverage property of the Work
            with the source of the GeographicCoverage from $2
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:geographicCoverage ?y          |
      | ?y a bf:GeographicCoverage           |
      | ?y rdfs:label "s-bl-ba"              |
      | ?y bf:source ?z                      |
      | ?z a bf:Source                       |
      | ?z rdfs:label "BlRjBN"               |
    Then I should find 1 match

  Scenario: $c creates a geographicCoverage property of the Work
            with source labelled 'ISO 3166'
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:geographicCoverage ?y          |
      | ?y a bf:GeographicCoverage           |
      | ?y rdfs:label "us"                   |
      | ?y bf:source ?z                      |
      | ?z a bf:Source                       |
      | ?z rdfs:label "ISO 3166"             |
    Then I should find 1 match
