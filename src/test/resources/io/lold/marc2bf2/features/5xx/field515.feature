Feature: 515 - NUMBERING PECULIARITIES NOTE
  Background:
    Given a marc field "=515  \\$aIssued in parts."
    When converted by a field converter io.lold.marc2bf2.converters.field5XX.Field515Converter

  Scenario: 515 creates a note/Note property of the Instance with noteType 'issuance information'
    When I search with patterns:
      | ?x a bf:Instance                      |
      | ?x bf:note ?y                         |
      | ?y a bf:Note                          |
      | ?y bf:noteType "issuance information" |
    Then I should find 1 match

  Scenario: $a create an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance                 |
      | ?x bf:note ?y                    |
      | ?y a bf:Note                     |
      | ?y rdfs:label "Issued in parts." |
    Then I should find 1 match
