Feature: 880$61XX - ALTERNATE GRAPHIC REPRESENTATION - NAMES - Main Entries
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  00$6100-01$a東海道五十三次絵卷."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 1XX should make an Agent
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:contribution ?y                |
      | ?y a bf:Contribution                 |
      | ?y bf:agent ?z                       |
      | ?z a bf:Agent                        |
      | ?z rdfs:label "東海道五十三次絵卷."   |
    Then I should find 1 match