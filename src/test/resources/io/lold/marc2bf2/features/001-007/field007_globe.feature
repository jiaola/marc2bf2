Feature: 007 - GLOBE - PHYSICAL DESCRIPTION FIXED FIELD
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=007  dc cef"
    When converted by a field converter io.lold.marc2bf2.converters.Field007Converter

  Scenario: pos 0 = 'd' sets Work rdf:type to 'Cartography'
    When I search with patterns:
      | ?x a bf:Work          |
      | ?x a bf:Cartography   |
    Then I should find 1 match

  Scenario: pos 0 = 'd' creates a genreForm property of the Work
    When I search with patterns:
      | ?x a bf:Work            |
      | ?x bf:genreForm ?y      |
      | ?y a bf:GenreForm       |
      | ?y rdfs:label "globe"   |
    Then I should find 1 match

  Scenario: pos 1 creates a genreForm property of the Work
    When I search with patterns:
      | ?x a bf:Work                      |
      | ?x bf:genreForm ?y                |
      | ?y a bf:GenreForm                 |
      | ?y rdfs:label "terrestrial globe" |
    Then I should find 1 match

  Scenario: pos 3 creates a colorContent property of the Work
    When I search with patterns:
      | ?x a bf:Work                      |
      | ?x bf:colorContent ?y             |
      | ?y a bf:ColorContent              |
      | ?y rdfs:label "multicolored"      |
    Then I should find 1 match

  Scenario: pos 4 creates a baseMaterial property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                  |
      | ?x bf:baseMaterial ?y             |
      | ?y a bf:BaseMaterial              |
      | ?y rdfs:label "synthetic"         |
    Then I should find 1 match

  Scenario: pos 5 creates a generation property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                  |
      | ?x bf:generation ?y               |
      | ?y a bf:Generation                |
      | ?y rdfs:label "facsimile"         |
    Then I should find 1 match
