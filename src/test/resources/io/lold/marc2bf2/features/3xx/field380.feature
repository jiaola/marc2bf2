Feature: MARC field 380 - FORM OF WORK
  Background:
    Given a marc field "=380  \\$aPlay"
    When converted by a field converter io.lold.marc2bf2.converters.Field380Converter

  Scenario: 380 creates genreForm properties of the Work
    When I search with patterns:
      | ?x a bf:Work             |
      | ?x bf:genreForm ?y       |
      | ?y a bf:GenreForm        |
      | ?y rdfs:label "Play"     |
    Then I should find 1 match

