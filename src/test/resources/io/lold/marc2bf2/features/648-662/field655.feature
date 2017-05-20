Feature: 655 - INDEX TERM--GENRE/FORM

  Background:
    Given a marc field "=655  \2$aFestschrift."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field655Converter

  Scenario: 655 creates a genreForm/GenreForm property of the Work
            with a class from MADSRDF
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:genreForm ?y                   |
      | ?y a bf:GenreForm                    |
      | ?y a madsrdf:GenreForm               |
    Then I should find 1 match

