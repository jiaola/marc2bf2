Feature: 544 - LOCATION OF OTHER ARCHIVAL MATERIALS NOTE
  Background:
    Given a marc field "=544  \\$3Flamingo statues$dBurt Barnes papers;$eAlso located at;$aState Historical Society of Wisconsin."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field544Converter

  Scenario: 544 creates a note/Note property of the Instance with noteType 'related material'
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y bf:noteType "related material"      |
    Then I should find 1 match

  Scenario: $abcden create an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y rdfs:label "Burt Barnes papers; Also located at; State Historical Society of Wisconsin." |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property of the Note" test="//bf:Instance[1]/bf:note[8]/bf:Note/bflc:appliesTo/bflc:AppliesTo/rdfs:label = 'Flamingo statues'"/>
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y bflc:appliesTo ?z                   |
      | ?z a bflc:AppliesTo                    |
      | ?z rdfs:label "Flamingo statues"       |
    Then I should find 1 match

