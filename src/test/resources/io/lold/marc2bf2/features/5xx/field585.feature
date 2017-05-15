Feature: 585 - EXHIBITIONS NOTE
  Background:
    Given a marc field "=585  \\$3Color lithographs$aExhibited: "Le Brun à Versailles," sponsored by the Cabinet des dessins, Musée du Louvre, 1985-1986.$5DLC"
    When converted by a field converter io.lold.marc2bf2.converters.field5XX.Field585Converter

  Scenario: 585 creates a note/Note property of the Instance with noteType 'exhibition'
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:note ?z                      |
      | ?z a bf:Note                       |
      | ?z bf:noteType "exhibition"        |
    Then I should find 1 match

  Scenario: $a creates an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:note ?z                      |
      | ?z a bf:Note                       |
      | ?z rdfs:label "Exhibited: \"Le Brun à Versailles,\" sponsored by the Cabinet des dessins, Musée du Louvre, 1985-1986."         |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property of the Note
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y bflc:appliesTo ?z                   |
      | ?z a bflc:AppliesTo                    |
      | ?z rdfs:label "Color lithographs"      |
    Then I should find 1 match

  Scenario: $5 creates a bflc:applicableInstitution property of the Note
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y bflc:applicableInstitution ?z       |
      | ?z a bf:Agent                          |
      | ?z bf:code "DLC"                       |
    Then I should find 1 match


