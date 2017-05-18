Feature: 777 - ISSUED WITH ENTRY
  
  Background:
    Given a marc field "=777  0\$tMythprint$x0146-9347"
    When converted by a field converter io.lold.marc2bf2.converters.field760to788.Field777Converter

  Scenario: 777 creates an issuedWith property of the Work
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:issuedWith ?y              |
    Then I should find 1 match
