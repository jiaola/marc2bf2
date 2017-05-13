Feature: 352 - DIGITAL GRAPHIC REPRESENTATION
  Background:
    Given a marc field "=352  \\$aVector :$bPoint$c(13671),$bstring$c(20171) ;$qARC/INFO export."
    When converted by a field converter io.lold.marc2bf2.converters.field3XX.Field352Converter

  Scenario: $a creates a digitalCharacteristic/CartographicDataType property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                      |
      | ?x bf:digitalCharacteristic ?y        |
      | ?y a bf:CartographicDataType          |
      | ?y rdfs:label "Vector"                |
    Then I should find 1 match

  Scenario: $b creates a digitalCharacteristic/CartographicObjectType property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                      |
      | ?x bf:digitalCharacteristic ?y        |
      | ?y a bf:CartographicObjectType        |
      | ?y rdfs:label "Point"                 |
    Then I should find 1 match

  Scenario: $c creates a digitalCharacteristic/ObjectCount property of the CartographicObjectType
    When I search with patterns:
      | ?x a bf:Instance                      |
      | ?x bf:digitalCharacteristic ?y        |
      | ?y a bf:CartographicObjectType        |
      | ?y bf:count "13671"                   |
    Then I should find 1 match

  Scenario: $q creates a digitalCharacteristic/EncodingFormat property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                      |
      | ?x bf:digitalCharacteristic ?y        |
      | ?y a bf:EncodingFormat                |
      | ?y rdfs:label "ARC/INFO export"       |
    Then I should find 1 match
