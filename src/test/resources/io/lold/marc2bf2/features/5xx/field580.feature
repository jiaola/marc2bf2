Feature: 580 - LINKING ENTRY COMPLEXITY NOTE
  Background:
    Given a marc field "=580  \\$aForms part of the Frances Benjamin Johnston Collection."
    When converted by a field converter io.lold.marc2bf2.converters.field5XX.Field580Converter

  Scenario: 561 creates a custodialHistory property of an Item
    When I search with patterns:
      | ?x a bf:Work                       |
      | ?x bf:note ?y                      |
      | ?y a bf:Note                       |
      | ?y rdfs:label "Forms part of the Frances Benjamin Johnston Collection." |
    Then I should find 1 match
