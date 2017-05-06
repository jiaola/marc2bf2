Feature: 383 - NUMERIC DESIGNATION OF MUSICAL WORK
  Background:
    Given a marc field "=383  \\$ano. 14,$bop. 27, no. 2"
    And a marc field "=383  \\$cF. I, 22-25$dFanna$2mlati"
    When converted by a field converter io.lold.marc2bf2.converters.Field383Converter

  Scenario: $a creates a musicSerialNumber property of the Work
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:musicSerialNumber "no. 14" |
    Then I should find 1 match

  Scenario: $b creates a musicOpusNumber property of the Work
    When I search with patterns:
      | ?x a bf:Work                          |
      | ?x bf:musicOpusNumber "op. 27, no. 2" |
    Then I should find 1 match

  Scenario: $c creates a musicThematicNumber property of the Work
    When I search with patterns:
      | ?x a bf:Work                            |
      | ?x bf:musicThematicNumber "F. I, 22-25" |
    Then I should find 1 match

  Scenario: $d creates a musicThematicNumber property of the Work
    When I search with patterns:
      | ?x a bf:Work                      |
      | ?x bf:musicThematicNumber "Fanna" |
    Then I should find 1 match