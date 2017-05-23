Feature: 880$6545 - BIOGRAPHICAL OR HISTORICAL DATA
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  0\$6545-01$aАвтор и реформатор."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 545 creates a note property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                      |
      | ?x bf:note ?y                         |
      | ?y a bf:Note                          |
      | ?y rdfs:label "Автор и реформатор."   |
    Then I should find 1 match

