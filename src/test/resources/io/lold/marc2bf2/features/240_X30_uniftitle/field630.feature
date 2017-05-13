Feature: 630 - SUBJECT ADDED ENTRY - UNIFORM TITLE
  
  Background:
    Given a marc field "=630  00$aUkrainian weekly$edepicted.$vIndexes$vPeriodicals$0(uri)http://example.org/9999#Work"
    When converted by a field converter io.lold.marc2bf2.converters.field240_X30.Field630Converter

  Scenario: 630 creates a new Work entry
    When I search with patterns:
      | ?x a bf:Work      |
      | ?x bf:subject ?y  |
      | ?y a bf:Work      |
    Then I should find 1 match

  Scenario: 630 becomes a subject/Work of the main Work
    When I search with patterns:
      | ?x a bf:Work                       |
      | ?x bf:subject ?y                   |
      | ?y a bf:Work                       |
      | ?y bf:title ?z                     |
      | ?z a bf:Title                      |
      | ?z bf:mainTitle "Ukrainian weekly" |
    Then I should find 1 match

  Scenario: 630 become a subject/Work with bflc:relationship carried over from $e/$4
    When I search with patterns:
      | ?x a bf:Work                       |
      | ?x bf:subject ?y                   |
      | ?y a bf:Work                       |
      | ?y bflc:relationship ?z            |
      | ?z a bflc:Relationship             |
      | ?z bflc:relation ?r                |
      | ?r a bflc:Relation                 |
      | ?r rdfs:label "depicted."          |
    Then I should find 1 match

  Scenario: $0/$w, if a URI, becomes the rdf:about attribute of the bf:Work for 630, 730, 830
    When I search with patterns:
      | ?x a bf:Work                                 |
      | ?x bf:subject <http://example.org/9999#Work> |
      | <http://example.org/9999#Work> a bf:Work     |
    Then I should find 1 match
