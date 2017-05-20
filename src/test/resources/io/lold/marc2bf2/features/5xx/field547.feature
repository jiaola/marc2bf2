Feature: 547 - FORMER TITLE COMPLEXITY NOTE
  Background:
    Given a marc field "=547  \\$aEdition varies: 1916, New York edition."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field547Converter

  Scenario: 547 creates a note property of the Instance
    When I search with patterns:
      | ?x a bf:Instance           |
      | ?x bf:note ?y              |
      | ?y a bf:Note               |
      | ?y rdfs:label "Edition varies: 1916, New York edition." |
    Then I should find 1 match
