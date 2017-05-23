Feature: 880$6256 - ALTERNATE GRAPHIC REPRESENTATION - COMPUTER FILE CHARACTERISTICS
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6256-01$aКомпьютерные данные (2 файлов: 876,000, 775,000 записей)."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 256 creates a note property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:note ?y                       |
      | ?y a bf:Note                        |
      | ?y rdfs:label "Компьютерные данные (2 файлов: 876,000, 775,000 записей)" |
    Then I should find 1 match


