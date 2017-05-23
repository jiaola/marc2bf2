Feature: 880$6700 - NAMES - Added Entry Fields

  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  0\$6700-01$aملحمة دانيال"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 7XX should make an Agent

  " test="//bf:Work[1]/bf:contribution[2]/bf:Contribution/bf:agent/bf:Agent/rdfs:label = 'ملحمة دانيال'"/>
    When I search with patterns:
      | ?x a bf:Work                          |
      | ?x bf:contribution ?y                 |
      | ?y a bf:Contribution                  |
      | ?y bf:agent ?z                        |
      | ?z a bf:Agent                         |
      | ?z rdfs:label "ملحمة دانيال"          |
    Then I should find 1 match

