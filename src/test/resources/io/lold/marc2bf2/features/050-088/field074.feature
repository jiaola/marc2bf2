Feature: 074 - GPO ITEM NUMBER

  Background:
    Given a marc field "=074  \\$a1022-A$z1012-A"
    When converted by a field converter io.lold.marc2bf2.converters.field050to088.Field074Converter

  Scenario: 074 creates an identifiedBy/Identifier property of the Instance
            with a source labelled 'US GPO'
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:identifiedBy ?y         |
      | ?y a bf:Identifier            |
      | ?y bf:source ?z               |
      | ?z a bf:Source                |
      | ?z rdfs:label "US GPO"        |
    Then I should find 2 matches

  Scenario: $a is the rdf:value of the Identifier
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:identifiedBy ?y         |
      | ?y a bf:Identifier            |
      | ?y rdf:value "1022-A"         |
    Then I should find 1 match

  Scenario: $z creates an status/Status property of an Identifier with rdfs:label 'invalid'
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:identifiedBy ?y         |
      | ?y a bf:Identifier            |
      | ?y bf:status ?z               |
      | ?z a bf:Status                |
      | ?z rdfs:label "invalid"       |
    Then I should find 1 match
