Feature: 880$6780 - PRECEEDING ENTRY

  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  06$6780-01$tГрафические уведомления и дополнительные данные$w(OCoLC)4276671"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 7XX linking field
    When I search with patterns:
      | ?x a bf:Work                          |
      | ?x bf:absorbed ?y                     |
      | ?y a bf:Work                          |
      | ?y bf:hasInstance ?z                  |
      | ?z a bf:Instance                      |
      | ?z bf:title ?t                        |
      | ?t a bf:Title                         |
      | ?t rdfs:label "Графические уведомления и дополнительные данные" |
    Then I should find 1 match

