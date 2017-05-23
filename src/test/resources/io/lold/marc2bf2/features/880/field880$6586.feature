Feature: 880$6586 - AWARDS NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6586-01$aНациональная книжная премия, 1981"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 586 creates a note property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                      |
      | ?x bf:note ?y                         |
      | ?y a bf:Note                          |
      | ?y rdfs:label "Национальная книжная премия, 1981" |
    Then I should find 1 match

