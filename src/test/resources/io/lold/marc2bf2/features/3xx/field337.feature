Feature: 337 - MEDIA TYPE
  Background:
    Given a marc field "=337  \\$aaudio$bs$0(uri)http://id.loc.gov/vocabulary/mediaTypes/s$2rdamedia$3soundtrack"
    When converted by a field converter io.lold.marc2bf2.converters.Field336_337_338Converter

  Scenario: 337 creates a media property of the Instance
    When I search with patterns:
      | ?x a bf:Instance       |
      | ?x bf:media ?y         |
    Then I should find 1 match

  Scenario: $a creates an rdfs:label property of the Media
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:media ?y                  |
      | ?y a bf:Media                   |
      | ?y rdfs:label "audio"           |
    Then I should find matches

  Scenario: $b creates a URI for the Media (an rdf:about attribute in RDF XML)
    When I search with patterns:
      | ?x a bf:Instance                                              |
      | ?x bf:media <http://id.loc.gov/vocabulary/mediaType/s>        |
      | <http://id.loc.gov/vocabulary/mediaType/s> a bf:Media         |
    Then I should find matches

  Scenario: $2 creates a source property of the Media
    When I search with patterns:
      | ?x a bf:Media            |
      | ?x bf:source ?y          |
      | ?y a bf:Source           |
      | ?y rdfs:label "rdamedia" |
    Then I should find matches

  Scenario: $0 creates an identifiedBy property of the Media
    When I search with patterns:
      | ?x a bf:Media                                            |
      | ?x bf:identifiedBy ?y                                    |
      | ?y a bf:Identifier                                       |
      | ?y rdf:value "http://id.loc.gov/vocabulary/mediaTypes/s" |
    Then I should find matches

  Scenario: $3 creates a bflc:appliesTo property of the Media
    When I search with patterns:
      | ?x a bf:Media                |
      | ?x bflc:appliesTo ?y         |
      | ?y a bflc:AppliesTo          |
      | ?y rdfs:label "soundtrack"   |