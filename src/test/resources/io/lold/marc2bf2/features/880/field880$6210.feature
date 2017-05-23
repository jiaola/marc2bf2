Feature: 880$6210 - ALTERNATE GRAPHIC REPRESENTATION - ABBREVIATED TITLE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  00$6210-01$a東海道五十三次絵卷."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 210-xx should make an AbbreviatedTitle
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:title ?y                       |
      | ?y a bf:Title                        |
      | ?y a bf:AbbreviatedTitle             |
    Then I should find 1 match
