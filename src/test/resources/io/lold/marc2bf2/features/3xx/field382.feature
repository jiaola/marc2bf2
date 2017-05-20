Feature: 382 - MEDIUM OF PERFORMANCE
  Background:
    Given a marc field "=382  \\$3Nach Bach$apiano$n1$vplayed on altered piano$pbaritone horn$n2$dsynthesizer$n1$s2$vpiano is prominent, but not all other instruments are not identified$2lcmpt"
    And a marc field "=382  \\$3Stuff$bflute$n1$aorchestra$e1$r1$t1$2lcmpt"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field382Converter

  Scenario: $a creates a musicMedium property of the Work
    When I search with patterns:
      | ?x a bf:Work                |
      | ?x bf:musicMedium ?y        |
      | ?y a bf:MusicMedium         |
      | ?y rdfs:label "piano"       |
    Then I should find 1 match

  Scenario: $n creates a count property of the MusicMedium
    When I search with patterns:
      | ?x a bf:Work                  |
      | ?x bf:musicMedium ?y          |
      | ?y a bf:MusicMedium           |
      | ?y rdfs:label "baritone horn" |
      | ?y bf:count "2"               |
    Then I should find 1 match

  Scenario: $e creates a count property of the MusicMedium
    When I search with patterns:
      | ?x a bf:Work                  |
      | ?x bf:musicMedium ?y          |
      | ?y a bf:MusicMedium           |
      | ?y rdfs:label "orchestra"     |
      | ?y bf:count "1"               |
    Then I should find 1 match

  Scenario: $v immediately following $a/$b/$d/$p creates a note property of the MusicMedium
    When I search with patterns:
      | ?x a bf:Work                  |
      | ?x bf:musicMedium ?y          |
      | ?y a bf:MusicMedium           |
      | ?y rdfs:label "piano"         |
      | ?y bf:count "1"               |
      | ?y bf:note ?z                 |
      | ?z a bf:Note                  |
      | ?z rdfs:label "played on altered piano" |
    Then I should find 1 match

  Scenario: $b creates a musicMedium property of the Work
    When I search with patterns:
      | ?x a bf:Work                |
      | ?x bf:musicMedium ?y        |
      | ?y a bf:MusicMedium         |
      | ?y rdfs:label "flute"       |
    Then I should find 1 match

  Scenario: $d creates a musicMedium/MusicMedium property of the Work with 'doubling' status
    When I search with patterns:
      | ?x a bf:Work                  |
      | ?x bf:musicMedium ?y          |
      | ?y a bf:MusicMedium           |
      | ?y rdfs:label "synthesizer"   |
      | ?y bf:status ?z               |
      | ?z a bf:Status                |
      | ?z rdfs:label "doubling"      |
    Then I should find 1 match

  Scenario: $p creates a musicMedium/MusicMedium property of the Work with 'alternative' status
    When I search with patterns:
      | ?x a bf:Work                  |
      | ?x bf:musicMedium ?y          |
      | ?y a bf:MusicMedium           |
      | ?y rdfs:label "baritone horn" |
      | ?y bf:status ?z               |
      | ?z a bf:Status                |
      | ?z rdfs:label "alternative"   |
    Then I should find 1 match

  Scenario: $r creates a musicMedium/MusicMedium property of the Work with a note property
    When I search with patterns:
      | ?x a bf:Work                  |
      | ?x bf:musicMedium ?y          |
      | ?y a bf:MusicMedium           |
      | ?y bf:note ?z                 |
      | ?z a bf:Note                  |
      | ?z rdfs:label "Total performers alongside ensembles: 1"   |
    Then I should find 1 match

  Scenario: $s creates a musicMedium/MusicMedium property of the Work with a note property
    When I search with patterns:
      | ?x a bf:Work                  |
      | ?x bf:musicMedium ?y          |
      | ?y a bf:MusicMedium           |
      | ?y bf:note ?z                 |
      | ?z a bf:Note                  |
      | ?z rdfs:label "Total performers: 2"   |
    Then I should find 1 match

  Scenario: $t creates a musicMedium/MusicMedium property of the Work with a note property
    When I search with patterns:
      | ?x a bf:Work                  |
      | ?x bf:musicMedium ?y          |
      | ?y a bf:MusicMedium           |
      | ?y bf:note ?z                 |
      | ?z a bf:Note                  |
      | ?z rdfs:label "Total ensembles: 1"   |
    Then I should find 1 match

  Scenario: $v after $r/$s/$t creates a musicMedium/MusicMedium property of the Work with a note property
    When I search with patterns:
      | ?x a bf:Work                  |
      | ?x bf:musicMedium ?y          |
      | ?y a bf:MusicMedium           |
      | ?y bf:note ?z                 |
      | ?z a bf:Note                  |
      | ?z rdfs:label "piano is prominent, but not all other instruments are not identified"   |
    Then I should find 1 match

  Scenario: $2 creates a source property of the MusicMedium
    When I search with patterns:
      | ?x a bf:Work                  |
      | ?x bf:musicMedium ?y          |
      | ?y a bf:MusicMedium           |
      | ?y bf:source ?z               |
      | ?z a bf:Source                |
      | ?z rdfs:label "lcmpt"         |
    Then I should find 2 match

  Scenario: $3 creates a bflc:appliesTo property of the MusicMedium
    When I search with patterns:
      | ?x a bf:Work                  |
      | ?x bf:musicMedium ?y          |
      | ?y a bf:MusicMedium           |
      | ?y bflc:appliesTo ?z          |
      | ?z a bflc:AppliesTo           |
      | ?z rdfs:label "Nach Bach"     |
    Then I should find 1 match
