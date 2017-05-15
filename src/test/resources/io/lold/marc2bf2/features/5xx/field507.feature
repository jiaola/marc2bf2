Feature: 507 - SCALE NOTE FOR GRAPHIC MATERIAL
  Background:
    Given a marc field "=507  \\$aScale 1:500,000;$b1 in. equals 8 miles."
    When converted by a field converter io.lold.marc2bf2.converters.field5XX.Field507Converter

  Scenario: 507 creates a scale property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:scale "Scale 1:500,000; 1 in. equals 8 miles." |
    Then I should find 1 match
