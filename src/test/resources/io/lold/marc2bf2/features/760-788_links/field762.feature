Feature: 760 - SUBSERIES ENTRY
  
  Background:
    Given a marc field "=762  0\$tQuality of surface waters of the United States"
    When converted by a field converter io.lold.marc2bf2.converters.field760to788.Field762Converter

  Scenario: 762 creates a hasSubseries property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:hasSubseries ?y                |
    Then I should find 1 match
