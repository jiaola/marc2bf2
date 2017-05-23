Feature: 880$6515 - NUMBERING PECULIARITIES NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6515-01$aВыпущенные в частях."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 515 creates a note property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:note ?y                       |
      | ?y a bf:Note                        |
      | ?y rdfs:label "Выпущенные в частях."|
    Then I should find 1 match

