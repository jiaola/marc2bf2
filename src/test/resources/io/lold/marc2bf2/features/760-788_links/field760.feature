Feature: 760 - MRAIN SERIES ENTRY
  
  Background:
    Given a marc field "=760  0\$aUnited States. Geological Survey.$tWater supply papers"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field760Converter

  Scenario: 760 creates a hasSeries property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:hasSeries ?y                   |
    Then I should find 1 match

  Scenario: $a creates a contribution property of the linked Work
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:hasSeries ?y                   |
      | ?y a bf:Work                         |
      | ?y bf:contribution ?z                |
      | ?z a bflc:PrimaryContribution        |
      | ?z bf:agent ?a                       |
      | ?a a bf:Agent                        |
      | ?a rdfs:label "United States. Geological Survey." |
    Then I should find 1 match

  Scenario: $t creates a title property of the linked Instance
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:hasSeries ?y                   |
      | ?y a bf:Work                         |
      | ?y bf:hasInstance ?z                 |
      | ?z a bf:Instance                     |
      | ?z bf:title ?a                       |
      | ?a a bf:Title                        |
      | ?a rdfs:label "Water supply papers"  |
    Then I should find 1 match
