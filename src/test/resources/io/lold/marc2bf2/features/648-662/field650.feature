Feature: 650 - SUBJECT ADDED ENTRY--TOPICAL TERM
  
  Background:
    Given a marc field "=650  \0$aCaracas$bBolivar Statue$cFar away$dLong ago$edepicted$gMiscellania$xHistory"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field650Converter

  Scenario: 650 creates a subject/Topic property of the Work
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:subject  ?y                    |
      | ?y a bf:Topic                        |
    Then I should find 1 match

  Scenario: $abcdvxyz create the rdfs:label property of the Topic
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:subject  ?y                    |
      | ?y a bf:Topic                        |
      | ?y rdfs:label "Caracas--Bolivar Statue--Far away--Long ago--History" |
    Then I should find 1 match

  Scenario: $e creates a bflc:relationship property of the Topic
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:subject  ?y                    |
      | ?y a bf:Topic                        |
      | ?y bflc:relationship ?z              |
      | ?z a bflc:Relationship               |
      | ?z bflc:relation ?r                  |
      | ?r a bflc:Relation                   |
      | ?r rdfs:label "depicted"             |
    Then I should find 1 match

  Scenario: $g creates a note property of the Topic
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:subject  ?y                    |
      | ?y a bf:Topic                        |
      | ?y bf:note ?z                        |
      | ?z a bf:Note                         |
      | ?z rdfs:label "Miscellania"          |
    Then I should find 1 match

  Scenario: $abcdvxyz create a madsrdf:componentList property of the Topic
            with components from the subfields
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:subject  ?y                    |
      | ?y a bf:Topic                        |
      | ?y madsrdf:componentList ?list       |
      | ?list rdf:rest*/rdf:first ?z         |
      | ?z a madsrdf:Topic                   |
      | ?z madsrdf:authoritativeLabel "Bolivar Statue" |
    Then I should find 1 match

