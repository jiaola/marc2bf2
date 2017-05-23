Feature: 880$6502 - ALTERNATE GRAPHIC REPRESENTATION - DISSERTATION NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6502-01$aтезис Карла Шмидта (докторская) - Людвиг-Максимилиана Universität, Munich, 1965."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 502 creates a dissertation property of the Work

  " test="//bf:Work[1]/bf:dissertation[1]/bf:Dissertation/rdfs:label = 'тезис Карла Шмидта (докторская) - Людвиг-Максимилиана Universität, Munich, 1965.'"/>
    When I search with patterns:
      | ?x a bf:Work                    |
      | ?x bf:dissertation ?y           |
      | ?y a bf:Dissertation            |
      | ?y rdfs:label "тезис Карла Шмидта (докторская) - Людвиг-Максимилиана Universität, Munich, 1965." |
    Then I should find 1 match

