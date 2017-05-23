Feature: 880$6338 - ALTERNATE GRAPHIC REPRESENTATION - CARRIER TYPE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6338-01$aаудиодиск$2rdacarrier"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 338 creates a carrier property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:carrier ?y                    |
      | ?y a bf:Carrier                     |
      | ?y rdfs:label "аудиодиск"           |
    Then I should find 1 match

