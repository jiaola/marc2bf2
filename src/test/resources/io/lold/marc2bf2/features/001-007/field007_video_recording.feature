Feature: 007 - VIDEO RECORDING - PHYSICAL DESCRIPTION FIXED FIELD
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=007  vf caahos"
    When converted by a field converter io.lold.marc2bf2.converters.Field007Converter

  Scenario: pos 0 = 'v' creates a genreForm property of the Instance
    When I search with patterns:
      | ?x a bf:Work                                                            |
      | ?x bf:genreForm <http://id.loc.gov/authorities/genreForms/gf2011026723> |
      | <http://id.loc.gov/authorities/genreForms/gf2011026723> a bf:GenreForm  |
    Then I should find 1 match

  Scenario: pos 0 = 'v' creates a media property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                                                   |
      | ?x bf:media <http://id.loc.gov/vocabulary/mediaTypes/v>            |
      | <http://id.loc.gov/vocabulary/mediaTypes/v> a bf:Media             |
    Then I should find 1 match

  Scenario: pos 1 creates a carrier property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:carrier ?y                |
      | ?y a bf:Carrier                 |
      | ?y rdfs:label "videocassette"   |
    Then I should find 1 match

  Scenario: pos 3 sets the colorContent property of the Work
    When I search with patterns:
      | ?x a bf:Work                 |
      | ?x bf:colorContent ?y        |
      | ?y a bf:ColorContent         |
      | ?y rdfs:label "multicolored" |
    Then I should find 1 match

  Scenario: pos 4 creates a videoCharacteristic/VideoFormat property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                            |
      | ?x bf:videoCharacteristic ?y                |
      | ?y a bf:VideoFormat                         |
      | ?y rdfs:label "Beta (1/2 in.videocassette)" |
    Then I should find 1 match

  Scenario: pos 5 creates a soundContent property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                            |
      | ?x bf:soundContent ?y                       |
      | ?y a bf:SoundContent                        |
      | ?y rdfs:label "sound on medium"             |
    Then I should find 1 match

  Scenario: pos 6 creates a soundCharacteristic/RecordingMedium property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                            |
      | ?x bf:soundCharacteristic ?y                |
      | ?y a bf:RecordingMedium                     |
      | ?y rdfs:label "videotape"                   |
    Then I should find 1 match

  Scenario: pos 7 creates a dimensions property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                        |
      | ?x bf:dimensions "1/2 in."              |
    Then I should find 1 match

  Scenario: pos 8 creates a soundCharacteristic/PlaybackChannels property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:soundCharacteristic ?y        |
      | ?y a bf:PlaybackChannels            |
      | ?y rdfs:label "stereophonic"        |
    Then I should find 1 match
