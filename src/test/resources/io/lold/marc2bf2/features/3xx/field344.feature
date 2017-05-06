Feature: 344 - SOUND CHARACTERISTICS
  Background:
    Given a marc field "=344  \\$3audio disc$adigital$boptical$gsurround$hDolby Digital 5.1$2rda"
    And a marc field "=344  \\$3record$aanalog$c78 rpm$dcoarse groove"
    And a marc field "=344  \\$3audio tape$aanalog$eedge track$f12 track"
    When converted by a field converter io.lold.marc2bf2.converters.Field344Converter

  Scenario: $a creates a soundCharacteristic/RecordingMethod property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                  |
      | ?x bf:soundCharacteristic ?y      |
      | ?y a bf:RecordingMethod           |
      | ?y rdfs:label "digital"           |
    Then I should find 1 match

  Scenario: $b creates a soundCharacteristic/RecordingMedium property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                  |
      | ?x bf:soundCharacteristic ?y      |
      | ?y a bf:RecordingMedium           |
      | ?y rdfs:label "optical"           |
    Then I should find 1 match

  Scenario: $c creates a soundCharacteristic/PlayingSpeed property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                  |
      | ?x bf:soundCharacteristic ?y      |
      | ?y a bf:PlayingSpeed              |
      | ?y rdfs:label "78 rpm"            |
    Then I should find 1 match

  Scenario: $d creates a soundCharacteristic/GrooveCharacteristic property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                  |
      | ?x bf:soundCharacteristic ?y      |
      | ?y a bf:GrooveCharacteristic      |
      | ?y rdfs:label "coarse groove"     |
    Then I should find 1 match

  Scenario: $e creates a soundCharacteristic/TrackConfig property of the Instance" test=
    When I search with patterns:
      | ?x a bf:Instance                  |
      | ?x bf:soundCharacteristic ?y      |
      | ?y a bf:TrackConfig               |
      | ?y rdfs:label "edge track"        |
    Then I should find 1 match

  Scenario: $f creates a soundCharacteristic/TapeConfig property of the Instance" test=
    When I search with patterns:
      | ?x a bf:Instance                  |
      | ?x bf:soundCharacteristic ?y      |
      | ?y a bf:TapeConfig                |
      | ?y rdfs:label "12 track"          |
    Then I should find 1 match

  Scenario: $g creates a soundCharacteristic/PlaybackChannels property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                  |
      | ?x bf:soundCharacteristic ?y      |
      | ?y a bf:PlaybackChannels          |
      | ?y rdfs:label "surround"          |
    Then I should find 1 match

  Scenario: $h creates a soundCharacteristic/PlaybackCharacteristic property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                  |
      | ?x bf:soundCharacteristic ?y      |
      | ?y a bf:PlaybackCharacteristic    |
      | ?y rdfs:label "Dolby Digital 5.1" |
    Then I should find 1 match

  Scenario: $2 creates a source property on generated Resources
    When I search with patterns:
      | ?x a bf:RecordingMethod           |
      | ?x bf:source ?y                   |
      | ?y a bf:Source                    |
      | ?y rdfs:label "rda"               |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property on generated Resources
    When I search with patterns:
      | ?x a bf:RecordingMethod           |
      | ?x bflc:appliesTo ?y              |
      | ?y a bflc:AppliesTo               |
      | ?y rdfs:label "audio disc"        |
    Then I should find 1 match
