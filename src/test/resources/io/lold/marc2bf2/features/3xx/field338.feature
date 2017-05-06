Feature: 338 - CARRIER TYPE
  Background:
    Given a marc field "=338  \\$aaudio disc$bsd$0(uri)http://id.loc.gov/vocabulary/carriers/sd$2rdacarrier$3soundtrack"
    When converted by a field converter io.lold.marc2bf2.converters.Field336_337_338Converter

  Scenario: 338 creates a carrier property of the Instance
    When I search with patterns:
      | ?x a bf:Instance       |
      | ?x bf:carrier ?y       |
    Then I should find 1 match

  Scenario: $a creates an rdfs:label property of the Carrier
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:carrier ?y                |
      | ?y a bf:Carrier                 |
      | ?y rdfs:label "audio disc"      |
    Then I should find matches

  Scenario: $b creates a URI for the Carrier (an rdf:about attribute in RDF XML)
    When I search with patterns:
      | ?x a bf:Instance                                              |
      | ?x bf:carrier <http://id.loc.gov/vocabulary/carrier/sd>       |
      | <http://id.loc.gov/vocabulary/carrier/sd> a bf:Carrier        |
    Then I should find matches

  Scenario: $2 creates a source property of the Carrier
    When I search with patterns:
      | ?x a bf:Carrier            |
      | ?x bf:source ?y            |
      | ?y a bf:Source             |
      | ?y rdfs:label "rdacarrier" |
    Then I should find matches

  Scenario: $0 creates an identifiedBy property of the Carrier
    When I search with patterns:
      | ?x a bf:Carrier                                          |
      | ?x bf:identifiedBy ?y                                    |
      | ?y a bf:Identifier                                       |
      | ?y rdf:value "http://id.loc.gov/vocabulary/carriers/sd"  |
    Then I should find matches

  Scenario: $3 creates a bflc:appliesTo property of the Carrier
    When I search with patterns:
      | ?x a bf:Carrier              |
      | ?x bflc:appliesTo ?y         |
      | ?y a bflc:AppliesTo          |
      | ?y rdfs:label "soundtrack"   |
    Then I should find matches