Feature: 880$6662 - SUBJECT ADDED ENTRY--HIERARCHICAL PLACE NAME

  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6662-01$a日本$c北海道$2pemrecs"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 662 creates a subject/Place property of the Work
    When I search with patterns:
      | ?x a bf:Work                          |
      | ?x bf:subject ?y                      |
      | ?y a bf:Place                         |
      | ?y rdfs:label "日本--北海道"               |
    Then I should find 1 match

