Feature: 773 - HOST ITEM ENTRY
  
  Background:
    Given a marc field "=773  0\$3Pamphlet$tEntomologists' monthly magazine$pENTOMOL MON MAG$kWonders of man series.$dWallingford : Gem Publishing Company$q24:B:9<235$x0013-8908$yFNMMA"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field773Converter

  Scenario: 773 creates a partOf property of the Work
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:partOf ?y                  |
    Then I should find 1 match

  Scenario: $d creates a provisionActivityStatement of the linked Instance
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:partOf ?y                  |
      | ?y a bf:Work                     |
      | ?y bf:hasInstance ?z             |
      | ?z a bf:Instance                 |
      | ?z bf:provisionActivityStatement "Wallingford : Gem Publishing Company" |
    Then I should find 1 match

  Scenario: $k creates a seriesStatement property of the linked Instance
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:partOf ?y                  |
      | ?y a bf:Work                     |
      | ?y bf:hasInstance ?z             |
      | ?z a bf:Instance                 |
      | ?z bf:seriesStatement "Wonders of man series." |
    Then I should find 1 match

  Scenario: $y creates an identifiedBy/Coden property of the linked Instance
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:partOf ?y                  |
      | ?y a bf:Work                     |
      | ?y bf:hasInstance ?z             |
      | ?z a bf:Instance                 |
      | ?z bf:identifiedBy ?i            |
      | ?i a bf:Coden                    |
      | ?i rdf:value "FNMMA"             |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property of the linked Work
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:partOf ?y                  |
      | ?y a bf:Work                     |
      | ?y bflc:appliesTo ?z             |
      | ?z a bflc:AppliesTo              |
      | ?z rdfs:label "Pamphlet"         |
    Then I should find 1 match