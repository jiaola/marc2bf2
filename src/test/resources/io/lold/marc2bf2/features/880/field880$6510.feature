Feature: 880$6510 - ALTERNATE GRAPHIC REPRESENTATION - CITATION/REFERENCES NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  2\$6510-01$aхимический рефераты,$x0009-2258"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 510 creates a bflc:indexedIn property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bflc:indexedIn ?y                |
      | ?y a bf:Instance                    |
      | ?y bf:title ?z                      |
      | ?z a bf:Title                       |
      | ?z rdfs:label "химический рефераты" |
    Then I should find 1 match

