Feature: 880$6538 - SYSTEM DETAILS NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6538-01$aРежим доступа: Интернет."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 538 creates a systemRequirement property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                      |
      | ?x bf:systemRequirement ?y            |
      | ?y a bf:SystemRequirement             |
      | ?y rdfs:label "Режим доступа: Интернет." |
    Then I should find 1 match

