Feature: 007 - SOUND RECORDING - PHYSICAL DESCRIPTION FIXED FIELD
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=007  st osscmcmglce"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field007Converter

  Scenario: pos 0 = 's' sets Instance rdf:type to 'Audio'
    When I search with patterns:
      | ?x a bf:Work   |
      | ?x a bf:Audio  |
    Then I should find 1 match

  Scenario: pos 0 = 's' creates a media property of the Instance
            and sets the rdfs:label property of the Media
    When I search with patterns:
      | ?x a bf:Instance                                                   |
      | ?x bf:media <http://id.loc.gov/vocabulary/mediaTypes/s>            |
      | <http://id.loc.gov/vocabulary/mediaTypes/s> a bf:Media             |
      | <http://id.loc.gov/vocabulary/mediaTypes/s> rdfs:label "audio"     |
    Then I should find 1 match

  Scenario: pos 1 creates a carrier property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:carrier ?y                |
      | ?y a bf:Carrier                 |
      | ?y rdfs:label "sound-tape reel" |
    Then I should find 1 match

  Scenario: pos 3 creates a soundCharacteristic/PlayingSpeed property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:soundCharacteristic ?y        |
      | ?y a bf:PlayingSpeed                |
      | ?y rdfs:label "7 1/2 ips"           |
    Then I should find 1 match

  Scenario: pos 4 creates a soundCharacteristic/PlaybackChannels property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:soundCharacteristic ?y        |
      | ?y a bf:PlaybackChannels            |
      | ?y rdfs:label "stereophonic"        |
    Then I should find 1 match

  Scenario: pos 5 creates a soundCharacteristic/GrooveCharacteristic property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:soundCharacteristic ?y        |
      | ?y a bf:GrooveCharacteristic        |
      | ?y rdfs:label "coarse/standard"     |
    Then I should find 1 match

  Scenario: pos 6 creates a dimensions property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                        |
      | ?x bf:dimensions "7 in."                |
    Then I should find 1 match

  Scenario: pos 7 creates a dimensions property of the Instance (tape width)
    When I search with patterns:
      | ?x a bf:Instance                        |
      | ?x bf:dimensions "1/4 in. tape width"   |
    Then I should find 1 match

  Scenario: pos 8 creates a soundCharacteristic/TapeConfig property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:soundCharacteristic ?y        |
      | ?y a bf:TapeConfig                  |
      | ?y rdfs:label "quarter (4)"         |
    Then I should find 1 match

  Scenario: pos 9 creates a generation property of the Instance
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:generation ?y           |
      | ?y a bf:Generation            |
      | ?y rdfs:label "mass produced" |
    Then I should find 1 match

  Scenario: pos 10 creates a baseMaterial property of the Instance
    When I search with patterns:
      | ?x a bf:Instance             |
      | ?x bf:baseMaterial ?y        |
      | ?y a bf:BaseMaterial         |
      | ?y rdfs:label "glass"        |
    Then I should find 1 match

  Scenario: pos 10 can create an emulsion property of the Instance
    When I search with patterns:
      | ?x a bf:Instance             |
      | ?x bf:emulsion ?y            |
      | ?y a bf:Emulsion             |
      | ?y rdfs:label "lacquer"      |
    Then I should find 1 match

  Scenario: pos 11 creates a soundCharacteristic/GrooveCharacteristic property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                            |
      | ?x bf:soundCharacteristic ?y                |
      | ?y a bf:GrooveCharacteristic                |
      | ?y rdfs:label "lateral or combined cutting" |
    Then I should find 1 match

  Scenario: pos 12 creates a soundCharacteristic/PlaybackCharacteristic property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                            |
      | ?x bf:soundCharacteristic ?y                |
      | ?y a bf:PlaybackCharacteristic              |
      | ?y rdfs:label "Dolby-B encoded"             |
    Then I should find 1 match
