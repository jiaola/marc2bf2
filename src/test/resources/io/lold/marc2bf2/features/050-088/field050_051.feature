Feature: 050 - LIBRARY OF CONGRESS CALL NUMBER

  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=050  00$aZ7164.N3$bL34 no. 9$aZ7165.R42$aHC517.R42$a123.45"
    And a marc field "=051  \\$aQE75$b.G4$c2d set."
    And a marc field "=050  00$aAtlantic 1259"
    When converted by a field converter io.lold.marc2bf2.converters.Field050Converter

  Scenario: 050 creates an Item
    When I search with patterns:
      | ?x a bf:Item |
    Then I should find matches

  Scenario: 050 creates  a hasItem property of the Instance
    When I search with patterns:
      | ?x a bf:Instance  |
      | ?x bf:hasItem ?y  |
    Then I should find 2 matches

  Scenario: 050 creates classification properties of the Work
    When I search with patterns:
      | ?x a bf:Work                 |
      | ?x bf:classification ?y      |
      | ?y a bf:ClassificationLcc    |
    Then I should find 3 matches

  Scenario: 050 creates a shelfMark property of the Item
    When I search with patterns:
      | ?x a bf:Item           |
      | ?x bf:shelfMark ?y     |
    Then I should find 4 matches

  Scenario: ind2 = '0' creates a source property of the ClassificationLcc
    When I search with patterns:
      | ?x a bf:Work                 |
      | ?x bf:classification ?y      |
      | ?y a bf:ClassificationLcc    |
      | ?y bf:source <http://id.loc.gov/vocabulary/organizations/dlc> |
      | <http://id.loc.gov/vocabulary/organizations/dlc> a bf:Source  |
    Then I should find 3 matches

  Scenario: ind2 = '0' creates a source of the ShelfMarkLcc"
    When I search with patterns:
      | ?x a bf:Item                 |
      | ?x bf:shelfMark ?y           |
      | ?y a bf:ShelfMarkLcc         |
      | ?y bf:source <http://id.loc.gov/vocabulary/organizations/dlc> |
      | <http://id.loc.gov/vocabulary/organizations/dlc> a bf:Source  |
    Then I should find 1 match

  Scenario: $a creates a classificationPortion of the ClassificationLcc
    When I search with patterns:
      | ?x a bf:Work                 |
      | ?x bf:classification ?y      |
      | ?y a bf:ClassificationLcc    |
      | ?y bf:classificationPortion "Z7164.N3" |
    Then I should find 1 match

  Scenario: $b creates an itemPortion of the ClassificationLcc
    When I search with patterns:
      | ?x a bf:Work                  |
      | ?x bf:classification ?y       |
      | ?y a bf:ClassificationLcc     |
      | ?y bf:itemPortion "L34 no. 9" |
    Then I should find 1 match

  Scenario: $ab become the rdfs:label of the ShelfMark
    When I search with patterns:
      | ?x a bf:Item                       |
      | ?x bf:shelfMark ?y                 |
      | ?y a bf:ShelfMarkLcc               |
      | ?y rdfs:label "Z7164.N3 L34 no. 9" |
    Then I should find 1 match

  Scenario: $a not of form ABC123 does not create a bf:classification property
    When I search with patterns:
      | ?y a bf:ClassificationLcc            |
      | ?y bf:classificationPortion "123.45" |
    Then I should find 0 matches

  Scenario: If the first $a is of form ABC123, 050 creates a ShelfMarkLcc object of the shelfMark property
    When I search with patterns:
      | ?x a bf:Item                       |
      | ?x bf:shelfMark ?y                 |
      | ?y a bf:ShelfMarkLcc               |
      | ?y rdfs:label "Z7164.N3 L34 no. 9" |
    Then I should find 1 match

  Scenario: If the first $a is not of form ABC123, 050 creates a ShelfMark object of the shelfMark property
    When I search with patterns:
      | ?x a bf:Item                       |
      | ?x bf:shelfMark ?y                 |
      | ?y a bf:ShelfMark                  |
      | ?y rdfs:label "Atlantic 1259"      |
    Then I should find 1 match

  Scenario: 051 creates a shelfMark property of the Item
    When I search with patterns:
      | ?x a bf:Item           |
      | ?x bf:shelfMark ?y     |
    Then I should find 4 matches

  Scenario: 051 creates rdfs:label of the ShelfMark is $abc
    When I search with patterns:
      | ?x a bf:Item                       |
      | ?x bf:shelfMark ?y                 |
      | ?y a bf:ShelfMarkLcc               |
      | ?y rdfs:label "QE75.G4 2d set"     |
    Then I should find 2 matches