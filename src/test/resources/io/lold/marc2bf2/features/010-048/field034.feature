Feature: 034 - CODED CARTOGRAPHIC MATHEMATICAL DATA 
  Background: 
    Given a marc field "=034  1\$aa$b744000$c96000$dW1800000$eE1800000$fN0840000$gS0700000$3map"
    And a marc field "=034  0\$aa"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field034Converter

  Scenario: $a = 'a'  alone creates a scale property of the Work with a note 'Linear scale'
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:scale ?y                       |
      | ?y a bf:Scale                        |
      | ?y bf:note ?z                        |
      | ?z a bf:Note                         |
      | ?z rdfs:label "linear scale"         |
    Then I should find 1 match

  Scenario: $b creates a scale property of the Work
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:scale ?y                       |
      | ?y a bf:Scale                        |
      | ?y rdfs:label "744000"               |
    Then I should find 1 match

  Scenario: $c creates a scale property of the Work
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:scale ?y                       |
      | ?y a bf:Scale                        |
      | ?y rdfs:label "96000"                |
    Then I should find 1 match

  Scenario: $defg are the coordinates property of the Cartographic entity
    When I search with patterns:
      | ?x a bf:Work                                            |
      | ?x bf:cartographicAttributes ?y                         |
      | ?y a bf:Cartographic                                    |
      | ?y bf:coordinates "W1800000 E1800000 N0840000 S0700000" |
    Then I should find 1 match

  Scenario: $3 creates a bf:appliesTo property of the Scale and Cartographic
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:scale ?y                       |
      | ?y a bf:Scale                        |
      | ?y bflc:appliesTo ?z                 |
      | ?z a bflc:AppliesTo                  |
      | ?z rdfs:label "map"                  |
    Then I should find 2 match
