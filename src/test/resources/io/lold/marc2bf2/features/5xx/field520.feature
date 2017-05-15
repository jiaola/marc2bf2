Feature: 520 - SUMMARY, ETC.
  Background:
    Given a marc field "=520  4\$3Some other part$aContains swear words, sex scenes and violence,$bbut otherwise it's ok.$cCentral County Library$uhttp://www.mpaa.org$2mpaa"
    When converted by a field converter io.lold.marc2bf2.converters.field5XX.Field520Converter

  Scenario: 520 creates a summary/Summary property of the Work
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:summary ?y                 |
      | ?y a bf:Summary                  |
    Then I should find 1 match

  Scenario: $ab create an rdfs:label property of the Summary
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:summary ?y                 |
      | ?y a bf:Summary                  |
      | ?y rdfs:label "Contains swear words, sex scenes and violence, but otherwise it's ok." |
    Then I should find 1 match

  Scenario: $c creates a source property of the Summary
    When I search with patterns:
      | ?x a bf:Work                           |
      | ?x bf:summary ?y                       |
      | ?y a bf:Summary                        |
      | ?y bf:source ?z                        |
      | ?z a bf:Source                         |
      | ?z rdfs:label "Central County Library" |
    Then I should find 1 match

  Scenario: $u creates a source property of the Summary
    When I search with patterns:
      | ?x a bf:Work                           |
      | ?x bf:summary ?y                       |
      | ?y a bf:Summary                        |
      | ?y bf:source ?z                        |
      | ?z a bf:Source                         |
      | ?z rdfs:label "http://www.mpaa.org"^^xsd:anyURI |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property of the Summary
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:summary ?y                 |
      | ?y a bf:Summary                  |
      | ?y bflc:appliesTo ?z             |
      | ?z a bflc:AppliesTo              |
      | ?z rdfs:label "Some other part"  |
    Then I should find 1 match
