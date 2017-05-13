Feature: 222 - KEY TITLE

  Background:
    Given a marc field "=222  \0$aIdler$b(London)"
    And a marc field "=222  \4$aThe Vagrant$b(London)"
    When converted by a field converter io.lold.marc2bf2.converters.field200to247not240.Field222Converter
  
  Scenario: multiple 222 should create title properties in Instance
    When I search with patterns:
      | ?x a bf:Instance            |
      | ?x bf:title ?y              |
    Then I should find 2 matches

  Scenario: @ind2 should create the appropriate titleSortKey
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bflc:titleSortKey "Vagrant (London)" |
    Then I should find 1 match

  Scenario: $a $b in record order creates a rdfs:label for Title
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x bf:title ?y                 |
      | ?y a bf:Title                  |
      | ?y rdfs:label "Idler (London)" |
    Then I should find 1 match

  Scenario: $a becomes the mainTitle
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bf:mainTitle "Idler"       |
    Then I should find 1 match

  Scenario: $b becomes the qualifier
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bf:qualifier "London"      |
    Then I should find 2 matches
