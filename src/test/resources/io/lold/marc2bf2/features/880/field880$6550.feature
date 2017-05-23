Feature: 880$6550 - ISSUING BODY NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6550-01$aОрган клуба Потомак на стороне естествоиспытателей."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 550 creates a note property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                      |
      | ?x bf:note ?y                         |
      | ?y a bf:Note                          |
      | ?y rdfs:label "Орган клуба Потомак на стороне естествоиспытателей." |
    Then I should find 1 match

