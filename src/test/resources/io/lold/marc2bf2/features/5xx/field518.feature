Feature: 518 - DATE/TIME AND PLACE OF AN EVENT NOTE
  Background:
    Given a marc field "=518  \\$aFound on March 5, 1975, in Richmond, Tex."
    And a marc field "=518  \\$3Part of the work$oFilmed on location$pRome and Venice$d1976 January through June."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field518Converter

  Scenario: 518 creates a capture/Capture property of the Work
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:capture ?y                 |
      | ?y a bf:Capture                  |
    Then I should find 2 matches

  Scenario: $adop create an rdfs:label property of the Capture
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:capture ?y                 |
      | ?y a bf:Capture                  |
      | ?y rdfs:label "Filmed on location Rome and Venice 1976 January through June." |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property of the Capture
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:capture ?y                 |
      | ?y a bf:Capture                  |
      | ?y bflc:appliesTo ?z             |
      | ?z a bflc:AppliesTo              |
      | ?z rdfs:label "Part of the work" |
    Then I should find 1 match

