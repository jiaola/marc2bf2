Feature: 880$6052 - ALTERNATE GRAPHIC REPRESENTATION - GEOGRAPHIC CLASSIFICATION
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6052-01$a3800$dНью-Йорк"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 052 creates a geographicCoverage property of the Work
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:geographicCoverage ?y          |
      | ?y a bf:Place                        |
      | ?y rdfs:label "Нью-Йорк"             |
    Then I should find 1 match
