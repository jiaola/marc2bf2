Feature: 880$6344 - ALTERNATE GRAPHIC REPRESENTATION - SOUND CHARACTERISTICS
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6344-01$3аудиодиск$aцифровой"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 344 creates a bunch of Instance soundCharacteristic properties
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:soundCharacteristic ?y        |
      | ?y a bf:RecordingMethod             |
      | ?y rdfs:label "цифровой"            |
    Then I should find 1 match

