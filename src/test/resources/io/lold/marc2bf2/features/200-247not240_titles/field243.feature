Feature: 243 - COLLECTIVE UNIFORM TITLE

  Background:
    Given a marc field "=243  04$aThe Compleat works of William Shakespeare,$sAbridged.$kSelections"
    When converted by a field converter io.lold.marc2bf2.converters.Field243Converter

  Scenario: @ind2 should create the appropriate titleSortKey
    When I search with patterns:
      | ?x a bf:Work                  |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bflc:titleSortKey "Compleat works of William Shakespeare, Abridged. Selections" |
    Then I should find 1 match

  Scenario: $adfgklmnoprs in record order creates a rdfs:label for Title
    When I search with patterns:
      | ?x a bf:Work                   |
      | ?x bf:title ?y                 |
      | ?y a bf:Title                  |
      | ?y rdfs:label "The Compleat works of William Shakespeare, Abridged. Selections" |
    Then I should find 1 match

  Scenario: $a becomes the mainTitle
    When I search with patterns:
      | ?x a bf:Work                  |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bf:mainTitle "The Compleat works of William Shakespeare" |
    Then I should find 1 match
