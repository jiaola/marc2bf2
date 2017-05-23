Feature: 880$6544 - LOCATION OF OTHER ARCHIVAL MATERIALS NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  1\$6544-01$dДепартамент здравоохранения Fonds, Услуги здравоохранения Отдел файлов."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 544 creates a note property of the Instance" test="//bf:Instance[1]/bf:note[11]/bf:Note/rdfs:label = 'Департамент здравоохранения Fonds, Услуги здравоохранения Отдел файлов.'"/>
    When I search with patterns:
      | ?x a bf:Instance                      |
      | ?x bf:note ?y                         |
      | ?y a bf:Note                          |
      | ?y rdfs:label "Департамент здравоохранения Fonds, Услуги здравоохранения Отдел файлов." |
    Then I should find 1 match

