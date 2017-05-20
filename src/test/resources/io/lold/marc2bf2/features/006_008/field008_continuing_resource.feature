Feature: 008 - CONTINUING RESOURCES - FIXED-LENGTH DATA ELEMENTS
  Background:
    Given a marc leader "=LDR  02579cas a2200577 a 4500"
    And a marc field "=008  911115d19692013iluwn pso     0   a0eng c"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field008Converter

  Scenario: pos 18 creates a frequency property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                          |
      | ?x bf:frequency ?y                        |
      | ?y a bf:Frequency                         |
      | ?y rdfs:label "Weekly"                    |
    Then I should find 1 match

  Scenario: pos 19 creates another frequency property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                          |
      | ?x bf:frequency ?y                        |
      | ?y a bf:Frequency                         |
      | ?y rdfs:label "normalized irregular"      |
    Then I should find 1 match

  Scenario: pos 21 creates a genreForm property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                          |
      | ?x bf:genreForm ?y                        |
      | ?y a bf:GenreForm                         |
      | ?y rdfs:label "periodical"                |
    Then I should find 1 match

  Scenario: pos 22 creates a note property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                          |
      | ?x bf:note ?y                             |
      | ?y a bf:Note                              |
      | ?y rdfs:label "electronic"               |
    Then I should find 1 match

  Scenario: pos 33 creates a notation property of the Work
    When I search with patterns:
      | ?x a bf:Work                              |
      | ?x bf:notation ?y                         |
      | ?y a bf:Script                            |
      | ?y rdfs:label "basic roman"               |
    Then I should find 1 match

  Scenario: pos 34 creates a note property of the AdminMetadata
    When I search with patterns:
      | ?x a bf:Work                              |
      | ?x bf:adminMetadata ?y                    |
      | ?y a bf:AdminMetadata                     |
      | ?y bf:note ?z                             |
      | ?z a bf:Note                              |
      | ?z rdfs:label "0 - successive"            |
    Then I should find 1 match
