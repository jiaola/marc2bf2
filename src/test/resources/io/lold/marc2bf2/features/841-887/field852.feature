Feature: 850 - LOCATION
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=852  \\$aCSf$bSci$uhttp://hdl.loc.gov/loc.pnp/pp.print$e10, rue du Général Camou$e75007 Paris$nfr$x1-54 on order in Microfiche$zSigned by author"
    When converted by a field converter io.lold.marc2bf2.converters.field841to887.Field852Converter

  Scenario: 852 creates an Item of the Instance
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:hasItem ?y                     |
      | ?y a bf:Item                         |
    Then I should find 1 match

  Scenario: $a becomes the heldBy/Agent property of the Item
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:hasItem ?y                     |
      | ?y a bf:Item                         |
      | ?y bf:heldBy <http://id.loc.gov/vocabulary/organizations/CSf>  |
      | <http://id.loc.gov/vocabulary/organizations/CSf> a bf:Agent    |
    Then I should find 1 match

  Scenario: $b creates a subLocation property of the Item
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:hasItem ?y                     |
      | ?y a bf:Item                         |
      | ?y bf:sublocation ?z                 |
      | ?z a bf:Sublocation                  |
      | ?z rdfs:label "Sci"                  |
    Then I should find 1 match

  Scenario: $e$n create a subLocation property of the Item (concatenated, with commas)
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:hasItem ?y                     |
      | ?y a bf:Item                         |
      | ?y bf:sublocation ?z                 |
      | ?z a bf:Sublocation                  |
      | ?z rdfs:label "10, rue du Général Camou, 75007 Paris, fr" |
    Then I should find 1 match

  Scenario: $u creates an electronicLocator property of the Item
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:hasItem ?y                     |
      | ?y a bf:Item                         |
      | ?y bf:electronicLocator <http://hdl.loc.gov/loc.pnp/pp.print> |
    Then I should find 1 match

  Scenario: $x creates a note property of the Item
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:hasItem ?y                     |
      | ?y a bf:Item                         |
      | ?y bf:note ?z                        |
      | ?z a bf:Note                         |
      | ?z rdfs:label "1-54 on order in Microfiche" |
    Then I should find 1 match

  Scenario: $z creates a note property of the Item
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:hasItem ?y                     |
      | ?y a bf:Item                         |
      | ?y bf:note ?z                        |
      | ?z a bf:Note                         |
      | ?z rdfs:label "Signed by author"     |
    Then I should find 1 match
