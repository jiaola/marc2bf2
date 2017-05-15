Feature: 536 - FUNDING INFORMATION NOTE
  Background:
    Given a marc field "=536  \\$aSponsored by the U.S. Air Force$bN00014-68-A-0245-0007$cEF-77-C-01-2556$d910 3450$e601101F$f1LIR$g5H$hWUAFGLILIR5H01"
    When converted by a field converter io.lold.marc2bf2.converters.field5XX.Field536Converter

  Scenario: 536 creates a note/Note property of the Instance with noteType 'funding information'
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y bf:noteType "funding information"   |
    Then I should find 1 match

  Scenario: $a creates an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y rdfs:label "Sponsored by the U.S. Air Force" |
    Then I should find 1 match

  Scenario: $b creates an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y rdfs:label "Contract: N00014-68-A-0245-0007"  |
    Then I should find 1 match

  Scenario: $c creates an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y rdfs:label "Grant: EF-77-C-01-2556" |
    Then I should find 1 match

  Scenario: $d creates an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y rdfs:label "910 3450"               |
    Then I should find 1 match

  Scenario: $e creates an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y rdfs:label "Program element: 601101F" |
    Then I should find 1 match

  Scenario: $f creates an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y rdfs:label "Project: 1LIR"          |
    Then I should find 1 match

  Scenario: $g creates an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y rdfs:label "Task: 5H"               |
    Then I should find 1 match

  Scenario: $h creates an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y rdfs:label "Work unit: WUAFGLILIR5H01" |
    Then I should find 1 match
