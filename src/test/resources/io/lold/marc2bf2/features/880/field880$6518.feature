Feature: 880$6518 - DATE/TIME AND PLACE OF AN EVENT NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6518-01$aРаботы по звуковым сопровождением диск записан 1955-1963 в различных местах."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 518 creates a capture property of the Work
    When I search with patterns:
      | ?x a bf:Work                        |
      | ?x bf:capture ?y                    |
      | ?y a bf:Capture                     |
      | ?y rdfs:label "Работы по звуковым сопровождением диск записан 1955-1963 в различных местах." |
    Then I should find 1 match

