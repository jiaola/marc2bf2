Feature: 880$6752 - ADDED ENTRY--HIERARCHICAL PLACE NAME

  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6752-01$aАнглия$dЛондон"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 752 creates a place property of the Work
    When I search with patterns:
      | ?x a bf:Work                          |
      | ?x bf:place ?y                        |
      | ?y a bf:Place                         |
      | ?y rdfs:label "Англия--Лондон"        |
    Then I should find 1 match

