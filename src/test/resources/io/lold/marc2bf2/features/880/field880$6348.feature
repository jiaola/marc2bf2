Feature: 880$6348 - ALTERNATE GRAPHIC REPRESENTATION - FORMAT OF NOTATED MUSIC
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6348-01$aклавир"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 348 creates a musicFormat property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:musicFormat ?y                |
      | ?y a bf:MusicFormat                 |
      | ?y rdfs:label "клавир"              |
    Then I should find 1 match

