Feature: 880$6351 - ALTERNATE GRAPHIC REPRESENTATION - ORGANIZATION AND ARRANGEMENT OF MATERIALS

  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6351-01$aSPSS системный файл."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 351 creates an arrangement property of the Work
    When I search with patterns:
      | ?x a bf:Work                        |
      | ?x bf:arrangement ?y                |
      | ?y a bf:Arrangement                 |
      | ?y bf:organization "SPSS системный файл" |
    Then I should find 1 match

