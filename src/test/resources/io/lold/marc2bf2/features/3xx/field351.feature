Feature: 351 - ORGANIZATION AND ARRANGEMENT OF MATERIALS
  Background:
    Given a marc field "=351  \\$3Records$cSeries;$aHierarchical;$bArranged by form of material."
    When converted by a field converter io.lold.marc2bf2.converters.field3xx.Field351Converter

  Scenario: 351 creates an arrangement/Arrangement property of the Work
    When I search with patterns:
      | ?x a bf:Work          |
      | ?x bf:arrangement ?y  |
      | ?y a bf:Arrangement   |
    Then I should find 1 match

  Scenario: $a creates an organization property of the Arrangement
    When I search with patterns:
      | ?x a bf:Work                      |
      | ?x bf:arrangement ?y              |
      | ?y a bf:Arrangement               |
      | ?y bf:organization "Hierarchical" |
    Then I should find 1 match

  Scenario: $b creates a pattern property of the Arrangement
    When I search with patterns:
      | ?x a bf:Work                                 |
      | ?x bf:arrangement ?y                         |
      | ?y a bf:Arrangement                          |
      | ?y bf:pattern "Arranged by form of material" |
    Then I should find 1 match

  Scenario: $0 creates an identifiedBy property of the MusicFormat
    When I search with patterns:
      | ?x a bf:Work                                 |
      | ?x bf:arrangement ?y                         |
      | ?y a bf:Arrangement                          |
      | ?y bf:hierarchicalLevel "Series"             |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property of the Arrangement
    When I search with patterns:
      | ?x a bf:Arrangement               |
      | ?x bflc:appliesTo ?y              |
      | ?y a bflc:AppliesTo               |
      | ?y rdfs:label "Records"           |
    Then I should find 1 match
