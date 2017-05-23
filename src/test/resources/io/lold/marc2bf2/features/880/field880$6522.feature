Feature: 880$6522 - GEOGRAPHIC COVERAGE NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6522-01$aКанада."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 522 creates a geographicCoverage/GeographicCoverage property of the Work
    When I search with patterns:
      | ?x a bf:Work                          |
      | ?x bf:geographicCoverage ?y           |
      | ?y a bf:GeographicCoverage            |
      | ?y rdfs:label "Канада."               |
    Then I should find 1 match

