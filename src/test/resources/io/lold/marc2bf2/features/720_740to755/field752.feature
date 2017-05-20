Feature: 752 - ADDED ENTRY--HIERARCHICAL PLACE NAME
  
  Background:
    Given a marc field "=752  \\$aEngland$dLondon$epublication place.$4pup$2tgn"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field752Converter

  Scenario: 752 creates a place/Place property of the Work
            with a madsrdf:HierarchicalGeopgraphic Class
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:place ?y                       |
      | ?y a bf:Place                        |
      | ?y rdfs:label "England--London"      |
      | ?y a madsrdf:HierarchicalGeographic  |
    Then I should find 1 match

  Scenario: $abcdfgh become part of the madsrdf:componentList of the Place
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:place ?y                       |
      | ?y a bf:Place                        |
      | ?y madsrdf:componentList ?list       |
      | ?list rdf:rest*/rdf:first ?z         |
      | ?z rdfs:label "London"               |
    Then I should find 1 match


