Feature: 007 - MAP - PHYSICAL DESCRIPTION FIXED FIELD
  Background:
    Given a marc leader "=LDR  01094cam a2200229 a 4500"
    And a marc field "=007  ay cafbm"
    When converted by a field converter io.lold.marc2bf2.converters.Field007Converter

  Scenario: pos 0 = 'a' sets Work rdf:type to Cartography
    When I search with patterns:
      | ?x a bf:Work          |
      | ?x a bf:Cartography   |
    Then I should find 1 match

  Scenario: pos 1 sets the genreForm property of the Work
    When I search with patterns:
      | ?x a bf:Work             |
      | ?x bf:genreForm ?y       |
      | ?y a bf:GenreForm        |
      | ?y rdfs:label "map view" |
    Then I should find 1 match

  Scenario: pos 3 sets the colorContent property of the Work
    When I search with patterns:
      | ?x a bf:Work                 |
      | ?x bf:colorContent ?y        |
      | ?y a bf:ColorContent         |
      | ?y rdfs:label "multicolored" |
    Then I should find 1 match

  Scenario: pos 4 sets the baseMaterial property of the Work
    When I search with patterns:
      | ?x a bf:Work                 |
      | ?x bf:baseMaterial ?y        |
      | ?y a bf:BaseMaterial         |
      | ?y rdfs:label "paper"        |
    Then I should find 1 match

  Scenario: pos 5 sets the generation property of the Instance
    When I search with patterns:
      | ?x a bf:Instance             |
      | ?x bf:generation ?y          |
      | ?y a bf:Generation           |
      | ?y rdfs:label "facsimile"    |
    Then I should find 1 match

  Scenario: pos 6 sets another generation property of the Instance
    When I search with patterns:
      | ?x a bf:Instance             |
      | ?x bf:generation ?y          |
      | ?y a bf:Generation           |
      | ?y rdfs:label "photocopy"    |
    Then I should find 1 match

  Scenario: pos 7 sets the polarity property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:polarity ?y               |
      | ?y a bf:Polarity                |
      | ?y rdfs:label "mixed polarity"  |
    Then I should find 1 match


