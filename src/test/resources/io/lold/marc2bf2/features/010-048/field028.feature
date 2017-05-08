Feature: 028 - PUBLISHER NUMBER
  Background: 
    Given a marc field "=028  02$a440 073 033-9$bDeutsche Grammophon$q(disc 1)"
    When converted by a field converter io.lold.marc2bf2.converters.Field028Converter

  Scenario: ind1 determines the class of Identifier for the identifiedBy property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:AudioIssueNumber             |
    Then I should find 1 match

  Scenario: $a creates an identifiedBy/Identifier property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:AudioIssueNumber             |
      | ?y rdf:value "440 073 033-9"         |
    Then I should find 1 match

  Scenario: $b creates a source property of the Identifier
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:AudioIssueNumber             |
      | ?y bf:source ?z                      |
      | ?z a bf:Source                       |
      | ?z rdfs:label "Deutsche Grammophon"  |
    Then I should find 1 match

  Scenario: $q creates a qualifier property of the Identifier
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:AudioIssueNumber             |
      | ?y bf:qualifier "(disc 1)"           |
    Then I should find 1 match
