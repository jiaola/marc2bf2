Feature: 586 - AWARDS NOTE
  Background:
    Given a marc field "=586  \\$3Accompanying material$aNational Book Award, 1981"
    When converted by a field converter io.lold.marc2bf2.converters.field5XX.Field586Converter

  Scenario: 586 creates a note/Note property of the Instance with noteType 'award'
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:note ?z                      |
      | ?z a bf:Note                       |
      | ?z bf:noteType "award"             |
    Then I should find 1 match

  Scenario: $a creates an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:note ?z                      |
      | ?z a bf:Note                       |
      | ?z rdfs:label "National Book Award, 1981" |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property of the Note
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y bflc:appliesTo ?z                   |
      | ?z a bflc:AppliesTo                    |
      | ?z rdfs:label "Accompanying material"  |
    Then I should find 1 match



