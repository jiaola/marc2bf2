Feature: 346 - VIDEO CHARACTERISTICS
  Background:
    Given a marc field "=346  \\$3videotape$aBeta$bPAL$2rda"
    When converted by a field converter io.lold.marc2bf2.converters.Field346Converter

  Scenario: $a creates a videoCharacteristic/VideoFormat property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                  |
      | ?x bf:videoCharacteristic ?y      |
      | ?y a bf:VideoFormat               |
      | ?y rdfs:label "Beta"              |
    Then I should find 1 match

  Scenario: $b creates a videoCharacteristic/BroadcastStandard property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                  |
      | ?x bf:videoCharacteristic ?y      |
      | ?y a bf:BroadcastStandard         |
      | ?y rdfs:label "PAL"               |
    Then I should find 1 match

  Scenario: $2 creates a source property on generated Resources
    When I search with patterns:
      | ?x a bf:VideoFormat               |
      | ?x bf:source ?y                   |
      | ?y a bf:Source                    |
      | ?y rdfs:label "rda"               |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property on generated Resources
    When I search with patterns:
      | ?x a bf:VideoFormat               |
      | ?x bflc:appliesTo ?y              |
      | ?y a bflc:AppliesTo               |
      | ?y rdfs:label "videotape"         |
    Then I should find 1 match
