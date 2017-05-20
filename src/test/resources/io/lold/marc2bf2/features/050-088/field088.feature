Feature: 088 - REPORT NUMBER

  Background:
    Given a marc field "=088  \\$aNASA-RP-1124-REV-3$zNASA-RP-1124-REV-2"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field088Converter
  
  Scenario: 088 creates an identifiedBy/ReportNumber property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                              |
      | ?x bf:identifiedBy ?y                         |
      | ?y a bf:ReportNumber                          |
    Then I should find 2 matches

  Scenario: $a is the rdf:value of the Identifier
    When I search with patterns:
      | ?x a bf:Instance                              |
      | ?x bf:identifiedBy ?y                         |
      | ?y a bf:ReportNumber                          |
      | ?y rdf:value "NASA-RP-1124-REV-3"             |
    Then I should find 1 match

  Scenario: $z creates a status/Status property of an Identifier with rdfs:label 'invalid'
    When I search with patterns:
      | ?x a bf:Instance                              |
      | ?x bf:identifiedBy ?y                         |
      | ?y a bf:ReportNumber                          |
      | ?y bf:status ?z                               |
      | ?z a bf:Status                                |
      | ?z rdfs:label "invalid"                       |
    Then I should find 1 match
