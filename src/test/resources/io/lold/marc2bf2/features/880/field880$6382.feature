Feature: 880$6382 - ALTERNATE GRAPHIC REPRESENTATION - MEDIUM OF PERFORMANCE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6382-01$aпианино"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 382 creates musicMedium properties of the Work
    When I search with patterns:
      | ?x a bf:Work                        |
      | ?x bf:musicMedium ?y                |
      | ?y a bf:MusicMedium                 |
      | ?y rdfs:label "пианино"             |
    Then I should find 1 match

