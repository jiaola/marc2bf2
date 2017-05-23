Feature: 880$6830 - SERIES ADDED ENTRY - UNIFORM TITLE

  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  0\$6830-01$aملحمة دانيال"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 830 should make a series Work
  " test="//bf:Work[1]/bf:hasSeries[2]/bf:Work/bf:title/bf:Title/bf:mainTitle = 'ملحمة دانيال'"/>
    When I search with patterns:
      | ?x a bf:Work                          |
      | ?x bf:hasSeries ?y                    |
      | ?y a bf:Work                          |
      | ?y bf:title ?z                        |
      | ?z a bf:Title                         |
      | ?z bf:mainTitle "ملحمة دانيال"           |
    Then I should find 1 match

