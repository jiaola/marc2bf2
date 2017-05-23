Feature: 880$6254 - ALTERNATE GRAPHIC REPRESENTATION -  MUSICAL PRESENTATION STATEMENT
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6254-01$aПолный счет."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 254 creates a note property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:note ?y                        |
      | ?y a bf:Note                         |
      | ?y rdfs:label "Полный счет"          |
    Then I should find 1 match











