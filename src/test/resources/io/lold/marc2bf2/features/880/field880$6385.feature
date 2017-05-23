Feature: 880$6385 - ALTERNATE GRAPHIC REPRESENTATION - AUDIENCE CHARACTERISTICS
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6385-01$aДети$2lcsh"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 385 creates intendedAudience properties of the Work
    When I search with patterns:
      | ?x a bf:Work                        |
      | ?x bf:intendedAudience ?y           |
      | ?y a bf:IntendedAudience            |
      | ?y rdfs:label "Дети"                |
    Then I should find 1 match

