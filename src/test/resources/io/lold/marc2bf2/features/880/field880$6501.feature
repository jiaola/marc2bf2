Feature: 880$6501 - ALTERNATE GRAPHIC REPRESENTATION - WITH NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6501-01$aС: реформированная школа / John Дьюри. Лондон: Печатный для Р. Wasnothe, [1850]"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 501 creates a note property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:note ?y                       |
      | ?y a bf:Note                        |
      | ?y rdfs:label "С: реформированная школа / John Дьюри. Лондон: Печатный для Р. Wasnothe, [1850]" |
    Then I should find 1 match

