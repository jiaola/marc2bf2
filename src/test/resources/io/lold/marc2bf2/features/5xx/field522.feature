Feature: 522 - GEOGRAPHIC COVERAGE NOTE
  Background:
    Given a marc field "=522  \\$aCanada."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field522Converter

  Scenario: 522 creates a geographicCoverage/GeographicCoverage property of the Work
    When I search with patterns:
      | ?x a bf:Work                       |
      | ?x bf:geographicCoverage ?y        |
      | ?y a bf:GeographicCoverage         |
      | ?y rdfs:label "Canada."            |
    Then I should find 1 match
