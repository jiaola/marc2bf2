Feature: 525 - SUPPLEMENT NOTE
  Background:
    Given a marc field "=525  \\$aHas numerous supplements."
    When converted by a field converter io.lold.marc2bf2.converters.field5XX.Field525Converter

  Scenario: 522 creates a geographicCoverage/GeographicCoverage property of the Work
    When I search with patterns:
      | ?x a bf:Instance                          |
      | ?x bf:supplementaryContent ?y             |
      | ?y a bf:SupplementaryContent              |
      | ?y rdfs:label "Has numerous supplements." |
    Then I should find 1 match
