Feature: 651 - SUBJECT ADDED ENTRY--GEOGRAPHIC NAME
  
  Background:
    Given a marc field "=651  \0$aKenwood (Chicago, Ill.)$0http://id.loc.gov/authorities/names/n97057532"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field651Converter

  Scenario: 651 creates a subject/Place property of the Work
            with a Class from MADSRDF
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:subject  ?y                    |
      | ?y a bf:Place                        |
      | ?y a madsrdf:Geographic              |
    Then I should find 1 match

  Scenario: $0 with a URI sets the rdf:about property of the Place
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:subject  <http://id.loc.gov/authorities/names/n97057532> |
      | <http://id.loc.gov/authorities/names/n97057532> a bf:Place     |
    Then I should find 1 match

