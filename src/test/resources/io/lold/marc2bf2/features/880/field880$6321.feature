Feature: 880$6321 - ALTERNATE GRAPHIC REPRESENTATION - FORMER PUBLICATION FREQ
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6321-01$aПолугодовой,$b1981-1982"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 321 creates a frequency property of the Instance" test="//bf:Instance[1]/bf:frequency[2]/bf:Frequency/rdfs:label = 'Полугодовой'"/>
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:frequency ?y                  |
      | ?y a bf:Frequency                   |
      | ?y rdfs:label "Полугодовой"         |
    Then I should find 1 match

