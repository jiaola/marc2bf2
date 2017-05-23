Feature: 880$6648 - SUBJECT ADDED ENTRY--CHRONOLOGICAL TERM
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \0$6648-01$aДоисторические времена"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 648 creates a subject/Temporal property of the Work
    When I search with patterns:
      | ?x a bf:Work                          |
      | ?x bf:subject ?y                      |
      | ?y a bf:Temporal                      |
      | ?y rdfs:label "Доисторические времена"|
    Then I should find 1 match

