Feature: 880$6507 - ALTERNATE GRAPHIC REPRESENTATION - SCALE NOTE FOR GRAPHIC MATERIAL
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6507-01$bПерспектива карта не в масштабе."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 507 creates a scale property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:scale "Перспектива карта не в масштабе." |
    Then I should find 1 match

