Feature: 880$6580 - LINKING ENTRY COMPLEXITY NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6580-01$aФормы часть Frances Benjamin Johnston Collection."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 580 creates a note property of the Work
    When I search with patterns:
      | ?x a bf:Work                          |
      | ?x bf:note ?y                         |
      | ?y a bf:Note                          |
      | ?y rdfs:label "Формы часть Frances Benjamin Johnston Collection." |
    Then I should find 1 match

