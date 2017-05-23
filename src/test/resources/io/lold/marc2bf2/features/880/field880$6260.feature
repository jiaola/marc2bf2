Feature: 880$6260 - ALTERNATE GRAPHIC REPRESENTATION - PUBLICATION, DISTRIBUTION, ETC. (IMPRINT)
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  3\$6260-01$aЛондон :$bСовет искусств Великобритании,$c1981"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 260 creates a provisionActivity property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:provisionActivity ?y          |
      | ?y a bf:ProvisionActivity           |
      | ?y bf:place ?z                      |
      | ?z a bf:Place                       |
      | ?z rdfs:label "Лондон"              |
    Then I should find 1 match
