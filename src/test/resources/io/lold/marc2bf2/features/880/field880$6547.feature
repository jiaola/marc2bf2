Feature: 880$6547 - FORMER TITLE COMPLEXITY NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6547-01$aИздание меняется: 1916, Нью-Йорк издания."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 547 creates a note property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                      |
      | ?x bf:note ?y                         |
      | ?y a bf:Note                          |
      | ?y rdfs:label "Издание меняется: 1916, Нью-Йорк издания." |
    Then I should find 1 match

