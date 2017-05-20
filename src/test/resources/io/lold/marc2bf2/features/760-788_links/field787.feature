Feature: 787 - OTHER RELATIONSHIP ENTRY
  
  Background:
    Given a marc field "=787  18$iRelated source work$o(istc)0A3200912B4A1057"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field787Converter

  Scenario: 787 creates a relatedTo property of the WorkK
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:relatedTo ?y               |
    Then I should find 1 match

  Scenario: $i creates a bflc:relationship property of the linked Work
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:relatedTo ?y               |
      | ?y a bf:Work                     |
      | ?y bflc:relationship ?z          |
      | ?z a bflc:Relationship           |
      | ?z bflc:relation ?r              |
      | ?r a bflc:Relation               |
      | ?r rdfs:label "Related source work" |
    Then I should find 1 match
