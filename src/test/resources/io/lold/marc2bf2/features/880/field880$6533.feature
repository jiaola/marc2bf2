Feature: 880$6533 - REPRODUCTION NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6533-01$aМикрофильмов."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 533 creates a hasInstance/Instance property of the Work
    When I search with patterns:
      | ?x a bf:Work                          |
      | ?x bf:hasInstance ?y                  |
      | ?y a bf:Instance                      |
      | ?y bf:carrier ?z                      |
      | ?z a bf:Carrier                       |
      | ?z rdfs:label "Микрофильмов"          |
    Then I should find 1 match

