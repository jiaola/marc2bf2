Feature: 880$6656 - INDEX TERM--OCCUPATION

  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \7$6656-01$aАнтропологи.$2nyet"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 656 creates a subject property of the Work
    When I search with patterns:
      | ?x a bf:Work                          |
      | ?x bf:subject ?y                      |
      | ?y a bf:Topic                         |
      | ?y madsrdf:componentList ?list        |
      | ?list rdf:rest*/rdf:first ?z          |
      | ?z a madsrdf:Occupation               |
      | ?z rdfs:label "Антропологи"           |
    Then I should find 1 match

