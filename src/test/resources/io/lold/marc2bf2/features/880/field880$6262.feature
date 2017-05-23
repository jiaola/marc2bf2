Feature: 880$6262 - ALTERNATE GRAPHIC REPRESENTATION - IMPRINT STATEMENT FOR SOUND RECORDINGS (Pre-AACR 2)
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6262-01$aЛуисвилл, Кентукки.,$bЛуисвилл оркестр,$c[1967]$kLS 671."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 262 creates a provisionActivity property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:provisionActivity ?y          |
      | ?y a bf:ProvisionActivity           |
      | ?y bf:place ?z                      |
      | ?z a bf:Place                       |
      | ?z rdfs:label "Луисвилл, Кентукки." |
    Then I should find 1 match
