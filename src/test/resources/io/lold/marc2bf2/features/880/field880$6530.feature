Feature: 880$6530 - ADDITIONAL PHYSICAL FORM AVAILABLE NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6530-01$aДоступно на микрофиши."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 530 creates a hasInstance/Instance property of the Work
    When I search with patterns:
      | ?x a bf:Work                          |
      | ?x bf:hasInstance ?y                  |
      | ?y a bf:Instance                      |
      | ?y bf:note ?z                         |
      | ?z a bf:Note                          |
      | ?z rdfs:label "Доступно на микрофиши" |
    Then I should find 1 match

