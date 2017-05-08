Feature: 008 - MUSIC - FIXED-LENGTH DATA ELEMENTS
  Background:
    Given a marc leader "=LDR  01478cjm a22002653a 4500"
    And a marc field "=008  070511s2001    tnuhya   ab    bd   zxx  "
    When converted by a field converter io.lold.marc2bf2.converters.Field006_008Converter

  Scenario: pos 18-19 creates a genreForm property of the Work
    When I search with patterns:
      | ?x a bf:Work                              |
      | ?x bf:genreForm ?y                        |
      | ?y a bf:GenreForm                         |
      | ?y rdfs:label "hymns"                     |
    Then I should find 1 match


  Scenario: pos 20 creates a musicFormat property of the Work
    When I search with patterns:
      | ?x a bf:Work                       |
      | ?x bf:musicFormat ?y               |
      | ?y a bf:MusicFormat                |
      | ?y rdfs:label "full score"         |
    Then I should find 1 match

  Scenario: pos 24-29 create supplementaryContent properties of the Work
    When I search with patterns:
      | ?x a bf:Work                       |
      | ?x bf:supplementaryContent ?y      |
      | ?y a bf:SupplementaryContent       |
      | ?y rdfs:label "bibliography"       |
    Then I should find 1 match

  Scenario: pos 30-31 create genreForm properties of the Work
    When I search with patterns:
      | ?x a bf:Work                              |
      | ?x bf:genreForm ?y                        |
      | ?y a bf:GenreForm                         |
      | ?y rdfs:label "drama"                     |
    Then I should find 1 match
