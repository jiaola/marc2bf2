Feature: 511 - PARTICIPANT OR PERFORMER NOTE
  Background:
    Given a marc field "=511  1\$aJackie Glanville."
    When converted by a field converter io.lold.marc2bf2.converters.field5XX.Field511Converter

  Scenario: 511 creates a credits property of the Work, with display constant based on ind1
    When I search with patterns:
      | ?x a bf:Work  |
      | ?x bf:credits "Cast: Jackie Glanville." |
    Then I should find 1 match
