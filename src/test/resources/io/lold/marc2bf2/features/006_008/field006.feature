Feature: 006 - FIXED-LENGTH DATA ELEMENTS-- ADDITIONAL MATERIAL CHARACTERISTICS
  Scenario: pos 1-17 processed as 008/18-34
    Given a marc leader "=LDR  01402nas a2200385 a 4500"
    And a marc field "=006  ab   |||||||||||||"
    When converted by a field converter io.lold.marc2bf2.converters.field006_008.Field006Converter
    When I search with patterns:
      | ?x a bf:Instance             |
      | ?x bf:illustrativeContent ?y |
      | ?y a bf:Illustration         |
      | ?y rdfs:label "Maps"         |
    Then I should find 1 match
