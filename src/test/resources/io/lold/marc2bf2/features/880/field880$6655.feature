Feature: 880$6655 - INDEX TERM--GENRE/FORM

  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \2$6655-01$aсборник статей"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 655 creates a genreForm/GenreForm property of the Work
  " test="//bf:Work[1]/bf:genreForm[2]/bf:GenreForm/rdfs:label = 'сборник статей'"/>
    When I search with patterns:
      | ?x a bf:Work                          |
      | ?x bf:genreForm ?y                    |
      | ?y a bf:GenreForm                     |
      | ?y rdfs:label "сборник статей"        |
    Then I should find 1 match

