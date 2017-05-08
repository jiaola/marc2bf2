Feature: 010 - LIBRARY OF CONGRESS CONTROL NUMBER
  Background: 
    Given a marc field "=010  \\$a  2004436018$z  2004436017"
    When converted by a field converter io.lold.marc2bf2.converters.Field010Converter

  Scenario: $a creates an identifiedBy/Lccn property of the Instance
    When I search with patterns:
      | ?x a bf:Instance             |
      | ?x bf:identifiedBy ?y        |
      | ?y a bf:Lccn                 |
      | ?y rdf:value "  2004436018"  |
    Then I should find 1 match

  Scenario: $z creates an status/Status property of the Lccn with rdfs:label 'invalid'
    When I search with patterns:
      | ?x a bf:Instance             |
      | ?x bf:identifiedBy ?y        |
      | ?y a bf:Lccn                 |
      | ?y bf:status ?z              |
      | ?z a bf:Status               |
      | ?z rdfs:label "invalid"      |
    Then I should find 1 match
