Feature: 581 - PUBLICATIONS ABOUT DESCRIBED MATERIALS NOTE
  Background:
    Given a marc field "=581  \\$3Preliminary reports$aReproduction: Antiques, June 1952, p. 76.$z1234567890"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field581Converter

  Scenario: 581 creates a note/Note property of the Instance with noteType 'related material'
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y bf:noteType "related material"      |
    Then I should find 1 match

  Scenario: $a creates an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y rdfs:label "Reproduction: Antiques, June 1952, p. 76." |
    Then I should find 1 match

  Scenario: $z creates an identifiedBy property of the Note
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y bf:identifiedBy ?z                  |
      | ?z a bf:Isbn                           |
      | ?z rdf:value "1234567890"              |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property of the Note
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y bflc:appliesTo ?z                   |
      | ?z a bflc:AppliesTo                    |
      | ?z rdfs:label "Preliminary reports"    |
    Then I should find 1 match

