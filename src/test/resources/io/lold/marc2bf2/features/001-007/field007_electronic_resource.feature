Feature: 007 - ELECTRONIC RESOURCE - PHYSICAL DESCRIPTION FIXED FIELD
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=007  co cgammmaadda"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field007Converter

  Scenario: pos 0 = 'c' sets Instance rdf:type to 'Electronic'
    When I search with patterns:
      | ?x a bf:Instance      |
      | ?x a bf:Electronic    |
    Then I should find 1 match

  Scenario: pos 0 = 'c' sets the media property of the Instance
            and sets the rdfs:label property of the Media
    When I search with patterns:
      | ?x a bf:Instance                                                  |
      | ?x bf:media <http://id.loc.gov/vocabulary/mediaTypes/c>           |
      | <http://id.loc.gov/vocabulary/mediaTypes/c> a bf:Media            |
      | <http://id.loc.gov/vocabulary/mediaTypes/c> rdfs:label "computer" |
    Then I should find 1 match

  Scenario: pos 1 sets the carrier property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:carrier ?y                |
      | ?y a bf:Carrier                 |
      | ?y rdfs:label "computer disc"   |
    Then I should find 1 match

  Scenario: pos 3 sets the colorContent property of the Work
    When I search with patterns:
      | ?x a bf:Work                    |
      | ?x bf:colorContent ?y           |
      | ?y a bf:ColorContent            |
      | ?y rdfs:label "multicolored"    |
    Then I should find 1 match

  Scenario: pos 4 sets the dimensions property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                        |
      | ?x bf:dimensions "4 3/4 in. or 12 cm."  |
    Then I should find 1 match

  Scenario: pos 5 sets the soundContent property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:soundContent ?y           |
      | ?y a bf:SoundContent            |
      | ?y rdfs:label "sound on medium" |
    Then I should find 1 match

  Scenario: pos 6-8 creates an ImageBitDepth digitalCharacteristic property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                 |
      | ?x bf:digitalCharacteristic ?y   |
      | ?y a bf:DigitalCharacteristic    |
      | ?y rdfs:label "multiple"         |
    Then I should find 1 match

  Scenario: pos 9 creates an EncodingFormat digitalCharacteristic property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                 |
      | ?x bf:digitalCharacteristic ?y   |
      | ?y a bf:DigitalCharacteristic    |
      | ?y rdfs:label "one file format"  |
    Then I should find 1 match
