Feature: 007 - PROJECTED GRAPHIC - PHYSICAL DESCRIPTION FIXED FIELD
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=007  gs cjbdjd"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field007Converter

  Scenario: pos 0 = 'g' sets Work rdf:type to 'StillImage'
    When I search with patterns:
      | ?x a bf:Work          |
      | ?x a bf:StillImage    |
    Then I should find 1 match

  Scenario: pos 0 = 'g' creates a media property of the Instance
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
      | ?y rdfs:label "slide"           |
    Then I should find 1 match

  Scenario: pos 3 creates a colorContent property of the Work
    When I search with patterns:
      | ?x a bf:Work                    |
      | ?x bf:colorContent ?y           |
      | ?y a bf:ColorContent            |
      | ?y rdfs:label "multicolored"    |
    Then I should find 1 match

  Scenario: pos 4 creates a baseMaterial property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                  |
      | ?x bf:baseMaterial ?y             |
      | ?y a bf:BaseMaterial              |
      | ?y rdfs:label "safety film"       |
    Then I should find 1 match

  Scenario: pos 5 creates a generation property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                           |
      | ?x bf:soundContent ?y                      |
      | ?y a bf:SoundContent                       |
      | ?y rdfs:label "sound separate from medium" |
    Then I should find 1 match

  Scenario: pos 6 creates a soundCharacteristic property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                 |
      | ?x bf:soundCharacteristic ?y     |
      | ?y a bf:RecordingMedium          |
      | ?y rdfs:label "sound disc"       |
    Then I should find 1 match

  Scenario: pos 7 creates a dimensions property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                        |
      | ?x bf:dimensions "2x2 in. or 5x5 cm."   |
    Then I should find 1 match

  Scenario: pos 8 creates a mount property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:mount ?y                  |
      | ?y a bf:Mount                   |
      | ?y rdfs:label "glass"           |
    Then I should find 1 match
