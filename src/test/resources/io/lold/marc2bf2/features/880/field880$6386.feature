Feature: 880$6386 - ALTERNATE GRAPHIC REPRESENTATION - CREATOR/CONTRIBUTOR CHARACTERISTICS
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6386-01$aДети$2lcsh"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 386 creates creatorCharacteristic properties of the Work
    When I search with patterns:
      | ?x a bf:Work                        |
      | ?x bflc:creatorCharacteristic ?y    |
      | ?y a bflc:CreatorCharacteristic     |
      | ?y rdfs:label "Дети"                |
    Then I should find 1 match

