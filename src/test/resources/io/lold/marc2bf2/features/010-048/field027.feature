Feature: 027 - STANDARD TECHNICAL REPORT NUMBER
  Background: 
    Given a marc field "=027  \\$aMETPRO/CB/TR--74/216+PR.ENVR.WI$zMPC-387"
    When converted by a field converter io.lold.marc2bf2.converters.Field027Converter

  Scenario: $a creates an identifiedBy/Strn property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:Strn                         |
      | ?y rdf:value "METPRO/CB/TR--74/216+PR.ENVR.WI"          |
    Then I should find 1 match

  Scenario: $z creates a status/Status property of the Strn with rdfs:label = 'invalid'
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:Strn                         |
      | ?y bf:status ?z                      |
      | ?z a bf:Status                       |
      | ?z rdfs:label "invalid"              |
    Then I should find 1 match
