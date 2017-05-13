Feature: 017 - COPYRIGHT OR LEGAL DEPOSIT NUMBER
  Background: 
    Given a marc field "=017  \8$iSuppl. reg.:$aJP732$bU.S. Copyright Office$d19510504"
    And a marc field "=017  \\$aM44120-2006 $bU.S. Copyright Office$zM444120-2006"
    When converted by a field converter io.lold.marc2bf2.converters.field010to048.Field017Converter

  Scenario: $a creates an identifiedBy/CopyrightNumber property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                            |
      | ?x bf:identifiedBy ?y                       |
      | ?y a bf:CopyrightNumber                     |
      | ?y rdf:value "JP732"                        |
    Then I should find 1 match

  Scenario: $b creates a source property of the CopyrightNumber
    When I search with patterns:
      | ?x a bf:Instance                            |
      | ?x bf:identifiedBy ?y                       |
      | ?y a bf:CopyrightNumber                     |
      | ?y bf:source ?z                             |
      | ?z a bf:Source                              |
      | ?z rdfs:label "U.S. Copyright Office"       |
    Then I should find 3 match

  Scenario: $d creates a date property of the CopyrightNumber
    When I search with patterns:
      | ?x a bf:Instance                            |
      | ?x bf:identifiedBy ?y                       |
      | ?y a bf:CopyrightNumber                     |
      | ?y bf:date "1951-05-04"^^xsd:date           |
    Then I should find 1 match

  Scenario: $i creates a note property of the CopyrightNumber
    When I search with patterns:
      | ?x a bf:Instance                            |
      | ?x bf:identifiedBy ?y                       |
      | ?y a bf:CopyrightNumber                     |
      | ?y bf:note ?z                               |
      | ?z a bf:Note                                |
      | ?z rdfs:label "Suppl. reg."                 |
    Then I should find 1 match

  Scenario: $z creates a status/Status property of the CopyrightNumber with rdfs:label 'invalid'
    When I search with patterns:
      | ?x a bf:Instance             |
      | ?x bf:identifiedBy ?y        |
      | ?y a bf:CopyrightNumber      |
      | ?y bf:status ?z              |
      | ?z a bf:Status               |
      | ?z rdfs:label "invalid"      |
    Then I should find 1 match