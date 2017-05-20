Feature: 772 - SUPPLEMENT PARENT ENTRY
  
  Background:
    Given a marc field "=772  1\$tStatistiques pour l'économie normande$g1979-$w(OCoLC)6260766"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field772Converter

  Scenario: 772 creates a supplementTo property of the Work
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:supplementTo ?y            |
    Then I should find 1 match

