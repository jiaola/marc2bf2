Feature: 880$6650 - SUBJECT ADDED ENTRY--TOPICAL TERM

  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \0$6650-01$aединороги"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 650 creates a subject/Topic property of the Work
    When I search with patterns:
      | ?x a bf:Work                          |
      | ?x bf:subject ?y                      |
      | ?y a bf:Topic                         |
      | ?y rdfs:label "единороги"             |
    Then I should find 1 match

