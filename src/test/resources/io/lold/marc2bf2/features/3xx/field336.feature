Feature: 336 - CONTENT TYPE
  Background:
    Given a marc field "=336  \\$aperformed music$bprm$0(uri)http://id.loc.gov/vocabulary/contentTypes/prm$2rdacontent$3record"
    When converted by a field converter io.lold.marc2bf2.converters.field3xx.Field336_337_338Converter

  Scenario: 336 creates a content property of the Work
    When I search with patterns:
      | ?x a bf:Work           |
      | ?x bf:content ?y       |
    Then I should find 1 match

  Scenario: $a creates an rdfs:label property of the Content
    When I search with patterns:
      | ?x a bf:Work                    |
      | ?x bf:content ?y                |
      | ?y a bf:Content                 |
      | ?y rdfs:label "performed music" |
    Then I should find matches

  Scenario: $b creates a URI of the Content (an rdf:about attribute in RDF XML)
    When I search with patterns:
      | ?x a bf:Work                                                  |
      | ?x bf:content <http://id.loc.gov/vocabulary/contentType/prm>  |
      | <http://id.loc.gov/vocabulary/contentType/prm> a bf:Content   |
    Then I should find matches

  Scenario: $2 creates a source property of the Content
    When I search with patterns:
      | ?x a bf:Content            |
      | ?x bf:source ?y            |
      | ?y a bf:Source             |
      | ?y rdfs:label "rdacontent" |
    Then I should find matches

  Scenario: $0 creates an identifiedBy property of the Content
    When I search with patterns:
      | ?x a bf:Content                                              |
      | ?x bf:identifiedBy ?y                                        |
      | ?y a bf:Identifier                                           |
      | ?y rdf:value "http://id.loc.gov/vocabulary/contentTypes/prm" |
    Then I should find matches

  Scenario: $3 creates a bflc:appliesTo property of the Content
    When I search with patterns:
      | ?x a bf:Content                |
      | ?x bflc:appliesTo ?y           |
      | ?y a bflc:AppliesTo            |
      | ?y rdfs:label "record"         |