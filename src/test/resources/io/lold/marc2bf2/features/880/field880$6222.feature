Feature: 880$6222 - ALTERNATE GRAPHIC REPRESENTATION - KEY TITLE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \0$6222-02$a東海道五十三次絵卷."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 222-xx should make a KeyTitle
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:title ?y                       |
      | ?y a bf:Title                        |
      | ?y a bf:KeyTitle                     |
    Then I should find 1 match
