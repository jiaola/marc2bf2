Feature: 508 - CREATION/PRODUCTION CREDITS NOTE
  Background:
    Given a marc field "=508  \\$aMusic, Michael Fishbein ; camera, George Mo."
    When converted by a field converter io.lold.marc2bf2.converters.field5XX.Field508Converter

  Scenario: 508 creates a credits property of the Work
    When I search with patterns:
      | ?x a bf:Work  |
      | ?x bf:credits "Music, Michael Fishbein ; camera, George Mo." |
    Then I should find 1 match
