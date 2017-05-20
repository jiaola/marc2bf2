Feature: 561 - OWNERSHIP AND CUSTODIAL HISTORY
  Background:
    Given a marc field "=561  \\$aOn permanent loan from the collection of Paul Mellon."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field561Converter

  Scenario: 561 creates a custodialHistory property of an Item
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:hasItem ?y                   |
      | ?y a bf:Item                       |
      | ?y bf:custodialHistory "On permanent loan from the collection of Paul Mellon." |
    Then I should find 1 match
