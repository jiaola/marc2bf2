Feature: 656 - INDEX TERM--OCCUPATION

  Background:
    Given a marc field "=656  \7$aChauffeurs$zFrance.$2someCode"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field656Converter

  Scenario: 656 creates a subject/Topic property of the Work
            with rdf:type of madsrdf:ComplexSubject
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:subject ?y                     |
      | ?y a bf:Topic                        |
      | ?y a madsrdf:ComplexSubject          |
    Then I should find 1 match

  Scenario: $akvxyz creates an rdfs:label property of the Topic
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:subject ?y                     |
      | ?y a bf:Topic                        |
      | ?y rdfs:label "Chauffeurs--France."  |
    Then I should find 1 match

  Scenario: $akvxyz becomes components in the madsrdf:componentList of the Topic
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:subject  ?y                    |
      | ?y a bf:Topic                        |
      | ?y madsrdf:componentList ?list       |
      | ?list rdf:rest*/rdf:first ?z         |
      | ?z a madsrdf:Occupation              |
      | ?z rdfs:label "Chauffeurs"           |
    Then I should find 1 match

  Scenario: $2 creates a source property of the Topic
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:subject  ?y                    |
      | ?y a bf:Topic                        |
      | ?y bf:source ?z                      |
      | ?z a bf:Source                       |
      | ?z rdfs:label "someCode"             |
    Then I should find 1 match

