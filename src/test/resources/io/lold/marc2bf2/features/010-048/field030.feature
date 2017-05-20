Feature: 030 - CODEN DESIGNATION
  Background: 
    Given a marc field "=030  \\$aASIRAF$zASITAF"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field030Converter

  Scenario: $a creates an identifiedBy/Coden property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:Coden                        |
      | ?y rdf:value "ASIRAF"                |
    Then I should find 1 match

  Scenario: $z creates a status/Status property of the Coden with rdfs:label = 'invalid'
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:Coden                        |
      | ?y bf:status ?z                      |
      | ?z a bf:Status                       |
      | ?z rdfs:label "invalid"              |
    Then I should find 1 match
