Feature: 880$6651 - SUBJECT ADDED ENTRY--GEOGRAPHIC NAME

  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \0$6651-01$aРека Амазонка."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 651 creates a subject/Place property of the Work
    When I search with patterns:
      | ?x a bf:Work                          |
      | ?x bf:subject ?y                      |
      | ?y a bf:Place                         |
      | ?y rdfs:label "Река Амазонка."        |
    Then I should find 1 match

