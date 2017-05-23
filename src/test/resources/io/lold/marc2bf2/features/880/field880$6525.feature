Feature: 880$6525 - SUPPLEMENT NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6525-01$aИмеет многочисленные добавки."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 525 creates a supplementaryContent/SupplementaryContent property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                      |
      | ?x bf:supplementaryContent ?y         |
      | ?y a bf:SupplementaryContent          |
      | ?y rdfs:label "Имеет многочисленные добавки." |
    Then I should find 1 match

