Feature: 880$6556 - INFORMATION ABOUT DOCUMENTATION NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6556-01$aBASIC ссылка. 3-е изд. Бока-Ратон, штат Флорида. : IBM, c1984. (Персональный компьютер аппаратные справочная библиотека); 6361132."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 556 creates a note property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                      |
      | ?x bf:note ?y                         |
      | ?y a bf:Note                          |
      | ?y rdfs:label "BASIC ссылка. 3-е изд. Бока-Ратон, штат Флорида. : IBM, c1984. (Персональный компьютер аппаратные справочная библиотека); 6361132." |
    Then I should find 1 match

