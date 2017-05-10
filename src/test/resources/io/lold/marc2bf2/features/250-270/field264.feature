Feature: 264 - PRODUCTION, PUBLICATIRON, DISTRIBUTION, MANUFACTURE, AND COPYRIGHT NOTICE
  
  Background:
    Given a marc field "=264  31$31981-$aWashington :$bU.S. G.P.O.,$c1981-"
    When converted by a field converter io.lold.marc2bf2.converters.Field264Converter

  Scenario: 264 creates a provisionActivity or copyrightDate property of the Instance
            with resource class determined by ind2
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:ProvisionActivity       |
      | ?y a bf:Publication             |
    Then I should find 1 match

  Scenario: 264 creates a provisionActivityStatement property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivityStatement "Washington : U.S. G.P.O., 1981-" |
    Then I should find 1 match

  Scenario: ind1 = 3 creates a status property of the ProvisionActivity
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:ProvisionActivity       |
      | ?y bf:status ?z                 |
      | ?z a bf:Status                  |
      | ?z rdfs:label "current"         |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property of the ProvisionActivity
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:ProvisionActivity       |
      | ?y bflc:appliesTo ?z            |
      | ?z a bflc:AppliesTo             |
      | ?z rdfs:label "1981-"           |
    Then I should find 1 match

  Scenario: $a creates a place property of the ProvisionActivity
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:ProvisionActivity       |
      | ?y bf:place ?z                  |
      | ?z a bf:Place                   |
      | ?z rdfs:label "Washington"      |
    Then I should find 1 match

  Scenario: $b creates an agent property of the ProvisionActivity
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:ProvisionActivity       |
      | ?y bf:agent ?z                  |
      | ?z a bf:Agent                   |
      | ?z rdfs:label "U.S. G.P.O."     |
    Then I should find 1 match

  Scenario: $c creates a date property of the ProvisionActivity
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:Publication             |
      | ?y bf:date "1981-"              |
    Then I should find 1 match
