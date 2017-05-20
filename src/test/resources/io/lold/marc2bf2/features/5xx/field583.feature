Feature: 583 - ACTION NOTE
  Background:
    Given a marc field "=583  \\$adowngraded$c19910110$hJoe Smith$kThomas Swing$lmutilated$uhttp://karamelik.eastlib.ufl.edu/cgi-bin/conserve/rara.pl$zInstitute for Museum and Library Services grant$2pda"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field583Converter

  Scenario: 583 creates a note/Note property of an Item with a noteType 'action'
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:hasItem ?y                   |
      | ?y a bf:Item                       |
      | ?y bf:note ?z                      |
      | ?z a bf:Note                       |
      | ?z bf:noteType "action"            |
    Then I should find 1 match

  Scenario: $a creates an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:hasItem ?y                   |
      | ?y a bf:Item                       |
      | ?y bf:note ?z                      |
      | ?z a bf:Note                       |
      | ?z rdfs:label "downgraded"         |
    Then I should find 1 match

  Scenario: $c creates a date property of the Note
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:hasItem ?y                   |
      | ?y a bf:Item                       |
      | ?y bf:note ?z                      |
      | ?z a bf:Note                       |
      | ?z bf:date "19910110"              |
    Then I should find 1 match

  Scenario: $h creates an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:hasItem ?y                   |
      | ?y a bf:Item                       |
      | ?y bf:note ?z                      |
      | ?z a bf:Note                       |
      | ?z rdfs:label "Joe Smith"          |
    Then I should find 1 match

  Scenario: $k creates an agent property of the Note
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:hasItem ?y                   |
      | ?y a bf:Item                       |
      | ?y bf:note ?z                      |
      | ?z a bf:Note                       |
      | ?z bf:agent ?a                     |
      | ?a a bf:Agent                      |
      | ?a rdfs:label "Thomas Swing"       |
    Then I should find 1 match

  Scenario: $l creates a status property of the Note
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:hasItem ?y                   |
      | ?y a bf:Item                       |
      | ?y bf:note ?z                      |
      | ?z a bf:Note                       |
      | ?z bf:status ?a                    |
      | ?a a bf:Status                     |
      | ?a rdfs:label "mutilated"          |
    Then I should find 1 match

  Scenario: $u creates an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:hasItem ?y                   |
      | ?y a bf:Item                       |
      | ?y bf:note ?z                      |
      | ?z a bf:Note                       |
      | ?z rdfs:label "http://karamelik.eastlib.ufl.edu/cgi-bin/conserve/rara.pl"^^xsd:anyURI |
    Then I should find 1 match

  Scenario: $z creates a note/Note property of the Note
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:hasItem ?y                   |
      | ?y a bf:Item                       |
      | ?y bf:note ?z                      |
      | ?z a bf:Note                       |
      | ?z bf:note ?n                      |
      | ?n a bf:Note                       |
      | ?n rdfs:label "Institute for Museum and Library Services grant" |
    Then I should find 1 match

  Scenario: $2 creates a source property of the Note
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:hasItem ?y                   |
      | ?y a bf:Item                       |
      | ?y bf:note ?z                      |
      | ?z a bf:Note                       |
      | ?z bf:source ?a                    |
      | ?a a bf:Source                     |
      | ?a rdfs:label "pda"                |
    Then I should find 1 match

