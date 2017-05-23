Feature: 880$6264 - ALTERNATE GRAPHIC REPRESENTATION - PRODUCTION, PUBLICATIRON, DISTRIBUTION, MANUFACTURE, AND COPYRIGHT NOTICE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \2$6264-01$aСиэтл :$bАйверсон компании"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 264 creates a provisionActivity property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:provisionActivity ?y          |
      | ?y a bf:ProvisionActivity           |
      | ?y bf:place ?z                      |
      | ?z a bf:Place                       |
      | ?z rdfs:label "Сиэтл"               |
    Then I should find 1 match

