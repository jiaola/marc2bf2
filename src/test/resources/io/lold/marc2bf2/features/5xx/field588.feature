Feature: 588 - SOURCE OF DESCRIPTION NOTE
  Background:
    Given a marc field "=588  \\$aPublication to be resumed by F&W Publications, Inc.in Oct. 2009.$5EZB"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field588Converter

  Scenario: 588 creates a note/Note property of the Instance with noteType 'award'
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:note ?z                      |
      | ?z a bf:Note                       |
      | ?z bf:noteType "description source"|
    Then I should find 1 match

  Scenario: $a creates an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:note ?z                      |
      | ?z a bf:Note                       |
      | ?z rdfs:label "Publication to be resumed by F&W Publications, Inc.in Oct. 2009." |
    Then I should find 1 match

  Scenario: $5 creates a bflc:applicableInstitution property of the Note
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y bflc:applicableInstitution ?z       |
      | ?z a bf:Agent                          |
      | ?z bf:code "EZB"                       |
    Then I should find 1 match



