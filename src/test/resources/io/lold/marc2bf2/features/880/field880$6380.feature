Feature: 880$6380 - ALTERNATE GRAPHIC REPRESENTATION - FORM OF WORK
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6380-01$aТелевизионная программа"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 380 creates genreForm properties of the Work" test="//bf:Work[1]/bf:genreForm[1]/bf:GenreForm/rdfs:label = 'Телевизионная программа'"/>
    When I search with patterns:
      | ?x a bf:Work                        |
      | ?x bf:genreForm ?y                  |
      | ?y a bf:GenreForm                   |
      | ?y rdfs:label "Телевизионная программа" |
    Then I should find 1 match

