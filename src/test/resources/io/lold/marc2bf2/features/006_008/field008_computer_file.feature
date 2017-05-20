Feature: 008 - COMPUTER FILES - FIXED-LENGTH DATA ELEMENTS
  Scenario: pos 26 creates a genreForm for the computer file type
    Given a marc leader "=LDR  01832cmma 2200349 a 4500"
    And a marc field "=008  000407m19949999dcu    go  m        eng d"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field008Converter
    When I search with patterns:
      | ?x a bf:Work                              |
      | ?x bf:genreForm ?y                        |
      | ?y a bf:GenreForm                         |
      | ?y rdfs:label "computer file combination" |
    Then I should find 1 match
