Feature: 880$6130 - ALTERNATE GRAPHIC REPRESENTATION - UNIFORM TITLE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=008  040520s2001    dk a   j      000 1 dan"
    And a marc field "=880  0\$6130-01/(3$aملحمة دانيال"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 X30-xx/240-xx should make a Title
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:title ?y                       |
      | ?y a bf:Title                        |
      | ?y bf:mainTitle "ملحمة دانيال"@da-arab       |
    Then I should find 1 match

