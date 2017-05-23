Feature: 880$6383 - ALTERNATE GRAPHIC REPRESENTATION - NUMERIC DESIGNATION OF MUSICAL WORK
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6383-01$a没有。 14"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 383 creates music number properties of the Work
    When I search with patterns:
      | ?x a bf:Work                        |
      | ?x bf:musicSerialNumber "没有。 14"    |
    Then I should find 1 match

