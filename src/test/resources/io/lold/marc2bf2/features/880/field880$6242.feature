Feature: 880$6242 - ALTERNATE GRAPHIC REPRESENTATION - TRANSLATION OF TITLE BY CATALOGING AGENCY
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  00$6242-03$a東海道五十三次絵卷."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 242-xx should make a VariantTitle with varientType 'translated'
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:title ?y                       |
      | ?y a bf:Title                        |
      | ?y bf:variantType "translated"       |
    Then I should find 1 match


