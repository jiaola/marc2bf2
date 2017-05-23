Feature: 880$6337 - ALTERNATE GRAPHIC REPRESENTATION - MEDIA TYPE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6337-01$aаудио$2rdamedia"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 337 creates a media property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:media ?y                      |
      | ?y a bf:Media                       |
      | ?y rdfs:label "аудио"               |
    Then I should find 1 match

