Feature: 516 - TYPE OF COMPUTER FILE OR DATA NOTE
  Background:
    Given a marc field "=516  \\$aComputer programs."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field516Converter

  Scenario: 516 creates a note/Note property of the Instance with noteType 'type of computer data'
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y bf:noteType "type of computer data" |
    Then I should find 1 match

  Scenario: $a create an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:note ?y                      |
      | ?y a bf:Note                       |
      | ?y rdfs:label "Computer programs." |
    Then I should find 1 match
