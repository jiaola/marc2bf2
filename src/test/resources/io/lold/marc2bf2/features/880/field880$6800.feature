Feature: 880$6800 - NAMES - Series Added Entry Fields

  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  0\$6800-01$aملحمة دانيال$t東海道五十三次絵卷."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 8XX should make a series Work
  " test="//bf:Work[1]/bf:hasSeries[1]/bf:Work/bf:title/bf:Title/bf:mainTitle = '東海道五十三次絵卷'"/>
    When I search with patterns:
      | ?x a bf:Work                          |
      | ?x bf:hasSeries ?y                    |
      | ?y a bf:Work                          |
      | ?y bf:title ?z                        |
      | ?z a bf:Title                         |
      | ?z bf:mainTitle "東海道五十三次絵卷"           |
    Then I should find 1 match

