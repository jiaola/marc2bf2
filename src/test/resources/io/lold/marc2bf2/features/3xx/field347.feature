Feature: 347 - DIGITAL FILE CHARACTERISTICS
  Background:
    Given a marc field "=347  \\$3computer disc$aimage file$bJPEG$c182 KB$d3.1 megapixels$eregion 4$f32 kbps$2rda"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field347Converter

  Scenario: $a creates a digitalCharacteristic/FileType property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                  |
      | ?x bf:digitalCharacteristic ?y    |
      | ?y a bf:FileType                  |
      | ?y rdfs:label "image file"        |
    Then I should find 1 match

  Scenario: $b creates a digitalCharacteristic/EncodingFormat property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                  |
      | ?x bf:digitalCharacteristic ?y    |
      | ?y a bf:EncodingFormat            |
      | ?y rdfs:label "JPEG"              |
    Then I should find 1 match

  Scenario: $c creates a digitalCharacteristic/FileSize property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                  |
      | ?x bf:digitalCharacteristic ?y    |
      | ?y a bf:FileSize                  |
      | ?y rdfs:label "182 KB"            |
    Then I should find 1 match

  Scenario: $d creates a digitalCharacteristic/Resolution property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                  |
      | ?x bf:digitalCharacteristic ?y    |
      | ?y a bf:Resolution                |
      | ?y rdfs:label "3.1 megapixels"    |
    Then I should find 1 match

  Scenario: $e creates a digitalCharacteristic/RegionalEncoding property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                  |
      | ?x bf:digitalCharacteristic ?y    |
      | ?y a bf:RegionalEncoding          |
      | ?y rdfs:label "region 4"          |
    Then I should find 1 match

  Scenario: $f creates a digitalCharacteristic/EncodedBitrate property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                  |
      | ?x bf:digitalCharacteristic ?y    |
      | ?y a bf:EncodedBitrate            |
      | ?y rdfs:label "32 kbps"           |
    Then I should find 1 match

  Scenario: $2 creates a source property on generated Resources
    When I search with patterns:
      | ?x a bf:FileType                  |
      | ?x bf:source ?y                   |
      | ?y a bf:Source                    |
      | ?y rdfs:label "rda"               |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property on generated Resources
    When I search with patterns:
      | ?x a bf:FileType                  |
      | ?x bflc:appliesTo ?y              |
      | ?y a bflc:AppliesTo               |
      | ?y rdfs:label "computer disc"     |
    Then I should find 1 match
