Feature: 261 - IMPRINT STATEMENT FOR FILMS (Pre-AACR 1 Revised)
  
  Background:
    Given a marc field "=261  \\$aProduzioni europee associate.$fRome;$aArturo Gonzalez,$fMadrid;$aConstantin Film,$fMunich.$eMade by D.C. Chipperfield.$bReleased in the U.S. by United Artists Corp.,$d1957."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field261Converter

  Scenario: 261 creates a provisionActivity/Production property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:ProvisionActivity       |
      | ?y a bf:Production              |
    Then I should find 1 match

  Scenario: 261 creates a provisionActivityStatement property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivityStatement "Produzioni europee associate. Rome; Arturo Gonzalez, Madrid; Constantin Film, Munich. Released in the U.S. by United Artists Corp., 1957." |
    Then I should find 1 match

  Scenario: if $e, 261 also creates a provisionActivity/Manufacture property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:ProvisionActivity       |
      | ?y a bf:Manufacture             |
    Then I should find 1 match

  Scenario: $a creates an agent property of the Production
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:ProvisionActivity       |
      | ?y bf:agent ?z                  |
      | ?z a bf:Agent                   |
      | ?z rdfs:label "Produzioni europee associate" |
    Then I should find 1 match

  Scenario: $b creates an agent property of the Production
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:Production              |
      | ?y bf:agent ?z                  |
      | ?z a bf:Agent                   |
      | ?z rdfs:label "Released in the U.S. by United Artists Corp" |
    Then I should find 1 match

  Scenario: $d creates a date property of the Production
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:Production              |
      | ?y bf:date "1957"               |
    Then I should find 1 match

  Scenario: $e creates an agent property of the Manufacture
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:ProvisionActivity       |
      | ?y a bf:Manufacture             |
      | ?y bf:agent ?z                  |
      | ?z a bf:Agent                   |
      | ?z rdfs:label "Made by D.C. Chipperfield" |
    Then I should find 1 match

  Scenario: $f creates a place property of the Production
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:Production              |
      | ?y bf:place ?z                  |
      | ?z a bf:Place                   |
      | ?z rdfs:label "Rome"            |
    Then I should find 1 match
