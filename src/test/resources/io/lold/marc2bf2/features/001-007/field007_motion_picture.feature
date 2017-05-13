Feature: 007 - MOTION PICTURE - PHYSICAL DESCRIPTION FIXED FIELD
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=007  mr caaadmaartauac198606"
    When converted by a field converter io.lold.marc2bf2.converters.field001to007.Field007Converter

  Scenario: pos 0 = 'm' sets Instance rdf:type to 'MovingImage'
            and creates a genreForm property of the Work
    When I search with patterns:
      | ?x a bf:Work                                                    |
      | ?x a bf:MovingImage                                             |
      | ?x bf:genreForm <http://id.loc.gov/authorities/genreForms/mot>  |
      | <http://id.loc.gov/authorities/genreForms/mot> a bf:GenreForm   |
    Then I should find 1 match
    
  Scenario: pos 0 = 'm' creates a media property of the Instance
            and sets the rdfs:label property of the Media
    When I search with patterns:
      | ?x a bf:Instance                                                   |
      | ?x bf:media <http://id.loc.gov/vocabulary/mediaTypes/g>            |
      | <http://id.loc.gov/vocabulary/mediaTypes/g> a bf:Media             |
      | <http://id.loc.gov/vocabulary/mediaTypes/g> rdfs:label "projected" |
    Then I should find 1 match

  Scenario: pos 1 creates a carrier property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:carrier ?y                |
      | ?y a bf:Carrier                 |
      | ?y rdfs:label "film reel"       |
    Then I should find 1 match

  Scenario: pos 3 creates a colorContent property of the Work
    When I search with patterns:
      | ?x a bf:Work                    |
      | ?x bf:colorContent ?y           |
      | ?y a bf:ColorContent            |
      | ?y rdfs:label "multicolored"    |
    Then I should find 1 match

  Scenario: pos 4 creates a projectionCharacteristic property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:projectionCharacteristic ?y   |
      | ?y a bf:ProjectionCharacteristic    |
      | ?y rdfs:label "standard sound aperture (reduced frame)" |
    Then I should find 1 match

  Scenario: pos 5 creates a soundContent property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:soundContent ?y           |
      | ?y a bf:SoundContent            |
      | ?y rdfs:label "sound on medium" |
    Then I should find 1 match

  Scenario: pos 6 creates a soundCharacteristic/RecordingMedium property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                 |
      | ?x bf:soundCharacteristic ?y     |
      | ?y a bf:RecordingMedium          |
      | ?y rdfs:label "optical sound track on motion picture film"  |
    Then I should find 1 match

  Scenario: pos 7 creates a dimensions property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                        |
      | ?x bf:dimensions "16 mm."               |
    Then I should find 1 match

  Scenario: pos 8 creates a soundCharacteristic/PlaybackChannels property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                 |
      | ?x bf:soundCharacteristic ?y     |
      | ?y a bf:PlaybackChannels         |
      | ?y rdfs:label "monaural"         |
    Then I should find 1 match

  Scenario: pos 9 creates a genreForm property of the Work
    When I search with patterns:
      | ?x a bf:Work              |
      | ?x bf:genreForm ?y        |
      | ?y a bf:GenreForm         |
      | ?y rdfs:label "workprint" |
    Then I should find 1 match

  Scenario: pos 10 creates a polarity property of the Instance
    When I search with patterns:
      | ?x a bf:Instance          |
      | ?x bf:polarity ?y         |
      | ?y a bf:Polarity          |
      | ?y rdfs:label "positive"  |
    Then I should find 1 match

  Scenario: pos 11 creates a generation property of the Instance
    When I search with patterns:
      | ?x a bf:Instance          |
      | ?x bf:generation ?y       |
      | ?y a bf:Generation        |
      | ?y rdfs:label "reference print, viewing copy"  |
    Then I should find 1 match

  Scenario: pos 12 creates a baseMaterial property of the Instance
    When I search with patterns:
      | ?x a bf:Instance             |
      | ?x bf:baseMaterial ?y        |
      | ?y a bf:BaseMaterial         |
      | ?y rdfs:label "triacetate"   |
    Then I should find 1 match

  Scenario: pos 16 creates a note property (completeness) of the Instance
    When I search with patterns:
      | ?x a bf:Instance          |
      | ?x bf:note ?y             |
      | ?y a bf:Note              |
      | ?y rdfs:label "complete"  |
    Then I should find 1 match

  Scenario: pos 17-22 creates a note property (film inspection date) of the Instance
    When I search with patterns:
      | ?x a bf:Instance          |
      | ?x bf:note ?y             |
      | ?y a bf:Note              |
      | ?y rdfs:label "1986-06"^^<http://www.w3.org/2001/XMLSchema#gYearMonth>   |
    Then I should find 1 match
