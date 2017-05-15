Feature: 563 - BINDING INFORMATION
  Background:
    Given a marc field "=563  \\$aLate 16th century blind-tooled centrepiece binding, dark brown calf."
    When converted by a field converter io.lold.marc2bf2.converters.field5XX.Field563Converter

  Scenario: 563 creates a note/Note property of an Item with noteType 'binding'
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:hasItem ?y                   |
      | ?y a bf:Item                       |
      | ?y bf:note ?z                      |
      | ?z a bf:Note                       |
      | ?z bf:noteType "binding"           |
    Then I should find 1 match

  Scenario: $a creates an rdfs:label property of the note
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:hasItem ?y                   |
      | ?y a bf:Item                       |
      | ?y bf:note ?z                      |
      | ?z a bf:Note                       |
      | ?z rdfs:label "Late 16th century blind-tooled centrepiece binding, dark brown calf." |
    Then I should find 1 match
