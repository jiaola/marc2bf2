Feature: 880$6511 - ALTERNATE GRAPHIC REPRESENTATION - PARTICIPANT OR PERFORMER NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  0\$6511-01$aДжеки Гланвилл."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 511 creates a bf:credits property of the Work
    When I search with patterns:
      | ?x a bf:Work                    |
      | ?x bf:credits "Джеки Гланвилл." |
    Then I should find 1 match

