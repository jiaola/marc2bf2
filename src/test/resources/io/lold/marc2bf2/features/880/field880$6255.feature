Feature: 880$6255 - ALTERNATE GRAPHIC REPRESENTATION - CARTOGRAPHIC MATHEMATICAL DATA
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6255-01$aМасштаб не дано."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 255 $a creates a scale property of the Work
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:scale ?y                       |
      | ?y a bf:Scale                        |
      | ?y rdfs:label "Масштаб не дано"      |
    Then I should find 1 match
