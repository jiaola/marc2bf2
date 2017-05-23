Feature: 880$6261 - ALTERNATE GRAPHIC REPRESENTATION - IMPRINT STATEMENT FOR FILMS (Pre-AACR 1 Revised)
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6261-01$aCoronet Фильмы,$d1967."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 261 creates a provisionActivity property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:provisionActivity ?y          |
      | ?y a bf:ProvisionActivity           |
      | ?y bf:agent ?z                      |
      | ?z a bf:Agent                       |
      | ?z rdfs:label "Coronet Фильмы"      |
    Then I should find 1 match
