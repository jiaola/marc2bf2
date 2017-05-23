Feature: 880$6546 - LANGUAGE NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6546-01$aНа французском."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

    Scenario: $6 546 creates a language property of the Work
    When I search with patterns:
      | ?x a bf:Work                          |
      | ?x bf:language ?y                     |
      | ?y a bf:Language                      |
      | ?y bf:note ?z                         |
      | ?z a bf:Note                          |
      | ?z rdfs:label "На французском"        |
    Then I should find 1 match

