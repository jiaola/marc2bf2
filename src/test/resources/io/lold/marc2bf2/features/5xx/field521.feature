Feature: 521 - TARGET AUDIENCE NOTE
  Background:
    Given a marc field "=521  2\$3Puzzles$aK-3.$a4-6.$bFollett Library Book Co."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field521Converter

  Scenario: $a creates an intendedAudience/IntendedAudience property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                 |
      | ?x bf:intendedAudience ?y        |
      | ?y a bf:IntendedAudience         |
      | ?y rdfs:label "4-6."             |
    Then I should find 1 match

  Scenario: ind1 can create a note property of the IntendedAudience
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:intendedAudience ?y            |
      | ?y a bf:IntendedAudience             |
      | ?y bf:note ?z                        |
      | ?z a bf:Note                         |
      | ?z rdfs:label "interest grade level" |
    Then I should find 2 matches

  Scenario: $b creates a source property of the IntendedAudience
    When I search with patterns:
      | ?x a bf:Instance                         |
      | ?x bf:intendedAudience ?y                |
      | ?y a bf:IntendedAudience                 |
      | ?y bf:source ?z                          |
      | ?z a bf:Source                           |
      | ?z rdfs:label "Follett Library Book Co." |
    Then I should find 2 matches

  Scenario: $c creates a bflc:appliesTo property of the IntendedAudience
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:intendedAudience ?y            |
      | ?y a bf:IntendedAudience             |
      | ?y bflc:appliesTo ?z                 |
      | ?z a bflc:AppliesTo                  |
      | ?z rdfs:label "Puzzles"              |
    Then I should find 2 matches
