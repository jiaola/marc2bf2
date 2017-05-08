Feature: 041 - LANGUAGE CODE

  Background:
    Given a marc field "=041  1\$aeng$hgerswe"
    And a marc field "=041  07$aen$afr$git$2iso639-1"
    When converted by a field converter io.lold.marc2bf2.converters.Field041Converter

  Scenario: 041 create language properties of the Work
    When I search with patterns:
      | ?x a bf:Work                                                |
      | ?x bf:language <http://id.loc.gov/vocabulary/languages/eng> |
      | <http://id.loc.gov/vocabulary/languages/eng> a bf:Language  |
    Then I should find 1 match

  Scenario: ind1 creates a note property of the Work
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:note ?y                        |
      | ?y a bf:Note                         |
      | ?y rdfs:label "Includes translation" |
    Then I should find 1 match

  Scenario: ind2 determines the source property of the Language
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:language ?y                    |
      | ?y a bf:Language                     |
      | ?y bf:source <http://id.loc.gov/vocabulary/iso639-1> |
      | <http://id.loc.gov/vocabulary/iso639-1> a bf:Source  |
    Then I should find 3 match

  Scenario: subfields other than $a create a part property of the Language" test="//bf:Work/bf:language[2]/bf:Language/bf:part = 'original'"/>
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:language ?y                    |
      | ?y a bf:Language                     |
      | ?y bf:part "original"                |
    Then I should find 2 match

