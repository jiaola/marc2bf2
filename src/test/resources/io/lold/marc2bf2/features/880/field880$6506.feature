Feature: 880$6506 - ALTERNATE GRAPHIC REPRESENTATION - RESTRICTIONS ON ACCESS NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6506-01$aКлассифицированная информация."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 506 creates a usageAndAccessPolicy property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:usageAndAccessPolicy ?y       |
      | ?y a bf:UsageAndAccessPolicy        |
      | ?y rdfs:label "Классифицированная информация." |
    Then I should find 1 match

