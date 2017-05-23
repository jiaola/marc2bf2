Feature: 880$6585 - EXHIBITIONS NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6585-01$aВыставлены: "Видения City & Country: эстампов и фотографий девятнадцатом веке во Франции", организованной Ворчестер художественного музея и Американской федерации искусств, 1982."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 585 creates a note property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                      |
      | ?x bf:note ?y                         |
      | ?y a bf:Note                          |
      | ?y rdfs:label "Выставлены: \"Видения City & Country: эстампов и фотографий девятнадцатом веке во Франции\", организованной Ворчестер художественного музея и Американской федерации искусств, 1982." |
    Then I should find 1 match

