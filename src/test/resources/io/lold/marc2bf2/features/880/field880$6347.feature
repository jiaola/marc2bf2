Feature: 880$6347 - ALTERNATE GRAPHIC REPRESENTATION - DIGITAL FILE CHARACTERISTICS
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6347-01$aфайл изображения"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 347 creates Instance digitalCharacteristic properties
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:digitalCharacteristic ?y      |
      | ?y a bf:FileType                    |
      | ?y rdfs:label "файл изображения"    |
    Then I should find 1 match

