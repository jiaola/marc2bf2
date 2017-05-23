Feature: 880$6508 - ALTERNATE GRAPHIC REPRESENTATION - CREATION/PRODUCTION CREDITS NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6508-01$aМузыка, Майкл Фишбейн; камера, Джордж Mo."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 508 creates a credits property of the Work
    When I search with patterns:
      | ?x a bf:Work                    |
      | ?x bf:credits "Музыка, Майкл Фишбейн; камера, Джордж Mo." |
    Then I should find 1 match

