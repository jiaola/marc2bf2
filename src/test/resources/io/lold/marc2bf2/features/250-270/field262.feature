Feature: 262 - IMPRINT STATEMENT FOR SOUND RECORDINGS (Pre-AACR 2)
  
  Background:
    Given a marc field "=262  \\$aLouisville, KY.,$bLouisville Orchestra,$c[1967]$kLS 671."
    When converted by a field converter io.lold.marc2bf2.converters.Field262Converter

  Scenario: 262 creates a provisionActivity/Production property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:ProvisionActivity       |
      | ?y a bf:Publication             |
    Then I should find 1 match

  Scenario: 262 creates a provisionActivityStatement property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivityStatement "Louisville, KY., Louisville Orchestra, [1967]" |
    Then I should find 1 match

  Scenario: $a creates a place property of the Publication
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:Publication             |
      | ?y bf:place ?z                  |
      | ?z a bf:Place                   |
      | ?z rdfs:label "Louisville, KY." |
    Then I should find 1 match

  Scenario: $b creates an agent property of the Publication
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:ProvisionActivity       |
      | ?y a bf:Publication             |
      | ?y bf:agent ?z                  |
      | ?z a bf:Agent                   |
      | ?z rdfs:label "Louisville Orchestra" |
    Then I should find 1 match

  Scenario: $c creates a date property of the Publication
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:Publication             |
      | ?y bf:date "1967"               |
    Then I should find 1 match
