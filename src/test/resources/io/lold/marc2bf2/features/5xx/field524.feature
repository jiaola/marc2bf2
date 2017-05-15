Feature: 524 - PREFERRED CITATION OF DESCRIBED MATERIALS NOTE
  Background:
    Given a marc field "=524  \\$aJames Hazen Hyde Papers, 1891-1941, New York Historical Society."
    When converted by a field converter io.lold.marc2bf2.converters.field5XX.Field524Converter

  Scenario: 524 creates a preferredCitation property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:preferredCitation "James Hazen Hyde Papers, 1891-1941, New York Historical Society." |
    Then I should find 1 match
