Feature: 348 - FORMAT OF NOTATED MUSIC
  Background:
    Given a marc field "=348  \\$3enclosed score$avocal score$bvoc$0(foo)12345$2foo"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field348Converter

  Scenario: 348 creates a musicFormat/MusicFormat property of the Instance
    When I search with patterns:
      | ?x a bf:Instance      |
      | ?x bf:musicFormat ?y  |
    Then I should find 1 match

  Scenario: $a creates an rdfs:label property of the MusicFormat
    When I search with patterns:
      | ?x a bf:Instance                  |
      | ?x bf:musicFormat ?y              |
      | ?y a bf:MusicFormat               |
      | ?y rdfs:label "vocal score"       |
    Then I should find 1 match

  Scenario: $b creates a code property of the MusicForma
    When I search with patterns:
      | ?x a bf:Instance                  |
      | ?x bf:musicFormat ?y              |
      | ?y a bf:MusicFormat               |
      | ?y bf:code "voc"                  |
    Then I should find 1 match

  Scenario: $0 creates an identifiedBy property of the MusicFormat
    When I search with patterns:
      | ?x a bf:MusicFormat               |
      | ?x bf:identifiedBy ?y             |
      | ?y a bf:Identifier                |
      | ?y rdf:value "12345"              |
    Then I should find 1 match

  Scenario: $2 creates a source property on generated Resources
    When I search with patterns:
      | ?x a bf:MusicFormat               |
      | ?x bf:source ?y                   |
      | ?y a bf:Source                    |
      | ?y rdfs:label "foo"               |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property on generated Resources
    When I search with patterns:
      | ?x a bf:MusicFormat               |
      | ?x bflc:appliesTo ?y              |
      | ?y a bflc:AppliesTo               |
      | ?y rdfs:label "enclosed score"    |
    Then I should find 1 match
