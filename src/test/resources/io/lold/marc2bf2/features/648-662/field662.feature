Feature: 662 - SUBJECT ADDED ENTRY--HIERARCHICAL PLACE NAME

  Background:
    Given a marc field "=662  \\$aJapan$cHokkaido$gAsahi-dake.$2pemracs"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field662Converter
    
  Scenario: 662 creates a subject/Place property of the Work
            with rdf:type of madsrdf:HierarchicalGeographic
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:subject ?y                     |
      | ?y a bf:Place                        |
      | ?y a madsrdf:HierarchicalGeographic  |
    Then I should find 1 match

  Scenario: $abcdfgh creates an rdfs:label property of the Topic
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:subject ?y                     |
      | ?y a bf:Place                        |
      | ?y rdfs:label "Japan--Hokkaido--Asahi-dake." |
    Then I should find 1 match

  Scenario: $abcdfgh becomes components in the madsrdf:componentList of the Topic
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:subject  ?y                    |
      | ?y a bf:Place                        |
      | ?y madsrdf:componentList ?list       |
      | ?list rdf:rest*/rdf:first ?z         |
      | ?z a madsrdf:Region                  |
      | ?z rdfs:label "Asahi-dake"           |
    Then I should find 1 match
