Feature: 022 - INTERNATIONAL STANDARD SERIAL NUMBER
  Background: 
    Given a marc field "=022  \\$a1234-1231$l1234-1231$m1560-1560$y0046-2254$z0361-7106$2DEU-6"
    When converted by a field converter io.lold.marc2bf2.converters.field010to048.Field022Converter

  Scenario: $a creates an identifiedBy/Issn property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                            |
      | ?x bf:identifiedBy ?y                       |
      | ?y a bf:Issn                                |
      | ?y rdf:value "1234-1231"                    |
    Then I should find 1 match

  Scenario: $l creates an identifiedBy/IssnL property of the Work
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:identifiedBy ?y                       |
      | ?y a bf:IssnL                               |
      | ?y rdf:value "1234-1231"                    |
    Then I should find 1 match

  Scenario: $m creates an IssnL with status/Status/rdfs:label 'canceled'
    When I search with patterns:
      | ?x a bf:Work                 |
      | ?x bf:identifiedBy ?y        |
      | ?y a bf:IssnL                |
      | ?y bf:status ?z              |
      | ?z a bf:Status               |
      | ?z rdfs:label "canceled"     |
    Then I should find 1 match

  Scenario: $y creates a status/Status property of the Issn with rdfs:label 'incorrect'
    When I search with patterns:
      | ?x a bf:Instance             |
      | ?x bf:identifiedBy ?y        |
      | ?y a bf:Issn                 |
      | ?y bf:status ?z              |
      | ?z a bf:Status               |
      | ?z rdfs:label "incorrect"    |
    Then I should find 1 match

  Scenario: $z creates a status/Status property of the Issn with rdfs:label 'canceled'
    When I search with patterns:
      | ?x a bf:Instance             |
      | ?x bf:identifiedBy ?y        |
      | ?y a bf:Issn                 |
      | ?y bf:status ?z              |
      | ?z a bf:Status               |
      | ?z rdfs:label "canceled"     |
    Then I should find 1 match

  Scenario: $2 creates a source property of the Issn
    When I search with patterns:
      | ?x a bf:Instance             |
      | ?x bf:identifiedBy ?y        |
      | ?y a bf:Issn                 |
      | ?y bf:source ?z              |
      | ?z a bf:Source               |
      | ?z rdfs:label "DEU-6"        |
    Then I should find 3 match
