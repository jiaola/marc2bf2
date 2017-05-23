Feature: 880$6243 - ALTERNATE GRAPHIC REPRESENTATION - COLLECTIVE UNIFORM TITLE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  00$6243-04$a東海道五十三次絵卷."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 243-xx should make a CollectiveTitle
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:title ?y                       |
      | ?y a bf:Title                        |
      | ?y a bf:CollectiveTitle              |
    Then I should find 1 match



