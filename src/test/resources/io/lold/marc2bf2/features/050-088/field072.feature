Feature: 072 - SUBJECT CATEGORY CODE

  Background:
    Given a marc field "=072  \0$aK800"
    And a marc field "=072  \7$aZ1$x.630$2mesh"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field072Converter

  Scenario: 072 creates a subject property of the Work
    When I search with patterns:
      | ?x a bf:Work       |
      | ?x bf:subject ?y   |
      | ?y a rdfs:Resource |
    Then I should find 2 matches

  Scenario: $ax becomes the rdf:value of the rdfs:Resource
    When I search with patterns:
      | ?x a bf:Work               |
      | ?x bf:subject ?y           |
      | ?y a rdfs:Resource         |
      | ?y rdf:value "Z1 .630"     |
    Then I should find 1 match

  Scenario: $2 creates source property of the rdfs:Resource
    When I search with patterns:
      | ?x a bf:Work               |
      | ?x bf:subject ?y           |
      | ?y a rdfs:Resource         |
      | ?y bf:source ?z            |
      | ?z a bf:Source             |
      | ?z rdfs:label "mesh"       |
    Then I should find 1 match

  Scenario: ind2 = '0' creates 'agricola' source property of the rdfs:Resource
    When I search with patterns:
      | ?x a bf:Work               |
      | ?x bf:subject ?y           |
      | ?y a rdfs:Resource         |
      | ?y bf:source ?z            |
      | ?z a bf:Source             |
      | ?z rdfs:label "agricola"   |
    Then I should find 1 match
