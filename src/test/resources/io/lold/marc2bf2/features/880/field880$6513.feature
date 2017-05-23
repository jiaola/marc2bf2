Feature: 880$6513 - ALTERNATE GRAPHIC REPRESENTATION - TYPE OF REPORT AND PERIOD COVERED NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6513-01$aЕжеквартальный отчет технический прогресс;$bЯнварь-апрель 1, 1977."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 513 creates a note property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:note ?y                       |
      | ?y a bf:Note                        |
      | ?y rdfs:label "Ежеквартальный отчет технический прогресс; Январь-апрель 1, 1977." |
    Then I should find 1 match

