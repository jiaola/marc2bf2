Feature: 880$6245 - ALTERNATE GRAPHIC REPRESENTATION - TITLE STATEMENT
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  00$6245-05$a東海道五十三次絵卷."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 245-xx should make a Title
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:title ?y                       |
      | ?y a bf:Title                        |
      | ?y rdfs:label "東海道五十三次絵卷."           |
    Then I should find 1 match






