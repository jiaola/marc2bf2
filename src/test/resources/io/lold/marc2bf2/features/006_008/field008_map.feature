Feature: 008 - MAPS - FIXED-LENGTH DATA ELEMENTS
  Background:
    Given a marc leader "=LDR  01354cem a2200325   4500"
    And a marc field "=008  830317s1977    pl ab  bk e  f    epeng  "
    When converted by a field converter io.lold.marc2bf2.converters.Field006_008Converter

  Scenario: pos 18-21 create cartographicAttributes properties of the Instance
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:cartographicAttributes ?y    |
      | ?y a bf:Cartographic               |
      | ?y bf:note ?z                      |
      | ?z a bf:Note                       |
      | ?z rdfs:label "shading"            |
    Then I should find 1 match

  Scenario: pos 22-23 creates a project property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:projection ?y                |
      | ?y a bf:Cartographic               |
      | ?y rdfs:label "Krovak"             |
    Then I should find 1 match

  Scenario: pos 25 creates an issuance or genreForm property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                          |
      | ?x bf:genreForm ?y                        |
      | ?y a bf:GenreForm                         |
      | ?y rdfs:label "atlas"                     |
    Then I should find 1 match

  Scenario: pos 33-34 create genreForm properties of the Work
    When I search with patterns:
      | ?x a bf:Work                              |
      | ?x bf:genreForm ?y                        |
      | ?y a bf:GenreForm                         |
      | ?y rdfs:label "playing cards"             |
    Then I should find 1 match
