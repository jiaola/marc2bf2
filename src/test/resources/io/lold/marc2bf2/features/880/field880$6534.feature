Feature: 880$6534 - ORIGINAL VERSION NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6534-01$pПервоначально опубликовано:$cНью-Йорк: Garland, 1987."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 534 creates a hasInstance/Instance property of the Work
    When I search with patterns:
      | ?x a bf:Work                          |
      | ?x bf:hasInstance ?y                  |
      | ?y a bf:Instance                      |
      | ?y bf:provisionActivityStatement "Нью-Йорк: Garland, 1987" |
    Then I should find 1 match

