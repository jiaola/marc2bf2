Feature: 880$6084 - ALTERNATE GRAPHIC REPRESENTATION - OTHER CLASSIFICATION NUMBER
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6084-01$aקב112.554$bו62 1980$qDE-101$2sdnb"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 084 creates a classification property of the Work
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:classification ?y              |
      | ?y a bf:Classification               |
      | ?y bf:classificationPortion "קב112.554" |
    Then I should find 1 match
