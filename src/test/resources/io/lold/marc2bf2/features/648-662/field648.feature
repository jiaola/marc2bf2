Feature: 648 - SUBJECT ADDED ENTRY--CHRONOLOGICAL TERM
  
  Background:
    Given a marc field "=648  \7$a1900-1999$2fast"
    When converted by a field converter io.lold.marc2bf2.converters.field648to662.Field648Converter

  Scenario: 648 creates a subject/Temporal property of the Work
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:subject  ?y                    |
      | ?y a bf:Temporal                     |
    Then I should find 1 match

  Scenario: ind2 creates a source property of the Temporal
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:subject  ?y                    |
      | ?y a bf:Temporal                     |
      | ?y bf:source ?z                      |
      | ?z a bf:Source                       |
      | ?z bf:code "fast"                    |
    Then I should find 1 match

  Scenario: Temporal also has Class from MADSRDF
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:subject  ?y                    |
      | ?y a bf:Temporal                     |
      | ?y a madsrdf:Temporal                |
    Then I should find 1 match

  Scenario: $avxyz create the rdfs:label property of the Topic
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:subject  ?y                    |
      | ?y a bf:Temporal                     |
      | ?y rdfs:label "1900-1999"            |
    Then I should find 1 match

  Scenario: $avxyz creates the madsrdf:authoritativeLabel property
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:subject  ?y                    |
      | ?y a bf:Temporal                     |
      | ?y madsrdf:authoritativeLabel "1900-1999" |
    Then I should find 1 match


