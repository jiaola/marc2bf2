Feature: 024 - OTHER STANDARD IDENTIFIER
  Background: 
    Given a marc field "=024  0\$aNLC018413261"
    And a marc field "=024  1\$z5539143515"
    And a marc field "=024  2\$aM570406203$qscore$qsewn$cEUR28.50"
    And a marc field "=024  3\$a9780449906200$d51000"
    And a marc field "=024  4\$a8756-2324(198603/04)65:2L.4:QTP:1-P"
    And a marc field "=024  7\$a0A3200912B4A1057$2istc"
    And a marc field "=024  8\$a12345678"
    When converted by a field converter io.lold.marc2bf2.converters.field010to048.Field024Converter

  Scenario: ind 1 determines the class of Identifier for the identifiedBy property
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:Isrc                         |
    Then I should find 1 match

  Scenario: $a creates an identifiedBy/Identifier property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:Isrc                         |
      | ?y rdf:value "NLC018413261"          |
    Then I should find 1 match

  Scenario: $c creates an acquisitionTerms property of the Identifier
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:Ismn                         |
      | ?y bf:acquisitionTerms "EUR28.50"    |
    Then I should find 1 match

  Scenario: $d creates a note property of the Identifier
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:Ean                          |
      | ?y bf:note ?z                        |
      | ?z a bf:Note                         |
      | ?z rdfs:label "51000"                |
    Then I should find 1 match

  Scenario: $q creates a qualifier property of the Identifier
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:Ismn                         |
      | ?y bf:qualifier "sewn"               |
    Then I should find 1 match

  Scenario: $z creates a status/Status property of the Identifier with rdfs:label 'invalid'
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:Upc                          |
      | ?y bf:status ?z                      |
      | ?z a bf:Status                       |
      | ?z rdfs:label "invalid"              |
    Then I should find 1 match

  Scenario: $2 creates an rdfs:label property of the Identifier" test="//bf:Instance[1]/bf:identifiedBy[19]/bf:Identifier/rdfs:label = 'istc'"/>
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:Identifier                   |
      | ?y rdfs:label "istc"                 |
    Then I should find 1 match
