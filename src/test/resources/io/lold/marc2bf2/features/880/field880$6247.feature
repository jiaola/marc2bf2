Feature: 880$6247 - ALTERNATE GRAPHIC REPRESENTATION - FORMER TITLE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  01$6247-07$a東海道五十三次絵卷."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 247-xx should make a VariantTitle with varientType 'former'
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:title ?y                       |
      | ?y a bf:Title                        |
      | ?y bf:variantType "former"           |
    Then I should find 1 match










