Feature: 008 - VISUAL MATERIALS - FIXED-LENGTH DATA ELEMENTS
  Background:
    Given a marc leader "=LDR  03778cgm a22005057a 4500"
    And a marc field "=008  030311s2002    xxu161            mleng  "
    When converted by a field converter io.lold.marc2bf2.converters.field006_008.Field008Converter
    
  Scenario: pos 18-21 create a duration property of the Work
    When I search with patterns:
      | ?x a bf:Work                        |
      | ?x bf:duration "P161M"^^xsd:duration  |
    Then I should find 1 match

  Scenario: pos 33 creates a genreForm property of the Work
    When I search with patterns:
      | ?x a bf:Work                              |
      | ?x bf:genreForm ?y                        |
      | ?y a bf:GenreForm                         |
      | ?y rdfs:label "motion picture"            |
    Then I should find 1 match

  Scenario: pos 34 creates a note property of the Instance" test="//bf:Instance[8]/bf:note/bf:Note/rdfs:label = 'live action'"/>
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:note ?y                      |
      | ?y a bf:Note                       |
      | ?y rdfs:label "live action"        |
    Then I should find 1 match

