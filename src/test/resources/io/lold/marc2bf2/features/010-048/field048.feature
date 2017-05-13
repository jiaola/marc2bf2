Feature: 048 - NUMBER OF MUSICAL INSTRUMENTS OR VOICES CODE

  Background:
    Given a marc field "=048  \\$bva02$bvc01$bvd01$bvf01$aca04$aoc"
    When converted by a field converter io.lold.marc2bf2.converters.field010to048.Field048Converter
  
  Scenario: 048 $a creates an instrument, ensemble, or voice property of the Work
    When I search with patterns:
      | ?x a bf:Work                 |
      | ?x bf:voice ?y               |
      | ?y a bf:MusicVoice           |
      | ?y rdfs:label "mixed chorus" |
    Then I should find 1 match

  Scenario: 048 $b creates an instrument, ensemble, or voice property of the Work
    When I search with patterns:
      | ?x a bf:Work                 |
      | ?x bf:voice ?y               |
      | ?y a bf:MusicVoice           |
      | ?y rdfs:label "soprano"      |
    Then I should find 1 match

  Scenario: position 2-3 of the subfield create a count property of the MusicInstrument, MusicEnsemble, or MusicVoice
    When I search with patterns:
      | ?x a bf:Work                 |
      | ?x bf:voice ?y               |
      | ?y a bf:MusicVoice           |
      | ?y bf:count "4"              |
    Then I should find 1 match
