Feature: 880$6504 - ALTERNATE GRAPHIC REPRESENTATION - BIBLIOGRAPHY, ETC. NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6504-01$a"Литература цитируется": стр. 67-68."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 504 creates a note property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:note ?y                       |
      | ?y a bf:Note                        |
      | ?y rdfs:label "\"Литература цитируется\": стр. 67-68." |
    Then I should find 1 match

