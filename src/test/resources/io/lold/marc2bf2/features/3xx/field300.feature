Feature: 300 - PHYSICAL DESCRIPTION
  Background:
    Given a marc field "=300  \\$3records$a1$fbox$g2 x 4 x 3 1/2 ft."
    And a marc field "=300  \\$a271 p. :$bill. ;$c21 cm. +$e1 answer book"
    When converted by a field converter io.lold.marc2bf2.converters.field3xx.Field300Converter

  Scenario: 300 creates an extent property of the Instance
    When I search with patterns:
      | ?x a bf:Instance |
      | ?x bf:extent ?y |
      | ?y a bf:Extent |
    Then I should find 2 matches

  Scenario: $a, $f, $g create the rdfs:label property of the Extent
    When I search with patterns:
      | ?x a bf:Extent |
      | ?x rdfs:label "1 box 2 x 4 x 3 1/2 ft." |
    Then I should find matches

  Scenario: $b creates a 'Physical details' note property of the Instance
    When I search with patterns:
      | ?x a bf:Instance |
      | ?x bf:note $y |
      | ?y a bf:Note |
      | ?y rdfs:label "ill." |
    Then I should find matches

  Scenario: $c creates a dimensions property of the Instance
    When I search with patterns:
      | ?x a bf:Instance |
      | ?x bf:dimensions "21 cm." |
    Then I should find matches

  Scenario: $e creates an 'Accompanying materials' note property of the Instance
    When I search with patterns:
      | ?x a bf:Instance |
      | ?x bf:note ?y    |
      | ?y a bf:Note     |
      | ?y bf:noteType "Accompanying materials" |
    Then I should find matches

  Scenario:  $3 creates a bflc:appliesTo property of the Extent and the Notes
    When I search with patterns:
      | ?x a bf:Instance        |
      | ?x bf:extent ?y         |
      | ?y a bf:Extent          |
      | ?y bflc:appliesTo ?z    |
      | ?z a bflc:AppliesTo     |
      | ?z rdfs:label "records" |
    Then I should find matches