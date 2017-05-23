Feature: 880$6246 - ALTERNATE GRAPHIC REPRESENTATION - VARYING FORM OF TITLE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  01$6246-06$a東海道五十三次絵卷."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 246-xx should make a VariantTitle
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:title ?y                       |
      | ?y a bf:Title                        |
      | ?y a bf:VariantTitle                 |
    Then I should find 1 match








