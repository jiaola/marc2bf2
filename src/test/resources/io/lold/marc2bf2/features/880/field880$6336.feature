Feature: 880$6336 - ALTERNATE GRAPHIC REPRESENTATION - CONTENT TYPE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6336-01$aисполняемая музыка$2rdacontent"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 336 creates a content property of the Work
    When I search with patterns:
      | ?x a bf:Work                        |
      | ?x bf:content ?y                    |
      | ?y a bf:Content                     |
      | ?y rdfs:label "исполняемая музыка"         |
    Then I should find 1 match

