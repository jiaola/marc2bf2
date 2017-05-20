Feature: 070 - NATIONAL AGRICULTURAL LIBRARY CALL NUMBER

  Background:
    Given a marc field "=070  0\$aHD3492.H8$bL3"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field070Converter

  Scenario: 070 creates a classification/Classification property of the Work
            with a source property
    When I search with patterns:
      | ?x a bf:Work                                  |
      | ?x bf:classification ?y                       |
      | ?y a bf:Classification                        |
      | ?y bf:source ?z                               |
      | ?z a bf:Source                                |
      | ?z rdfs:label "National Agricultural Library" |
    Then I should find 1 match

  Scenario: $a creates the classificationPortion of the Classification
    When I search with patterns:
      | ?x a bf:Work                                  |
      | ?x bf:classification ?y                       |
      | ?y a bf:Classification                        |
      | ?y bf:classificationPortion "HD3492.H8"       |
    Then I should find 1 match

  Scenario: $b creates an itemPortion of the Classification
    When I search with patterns:
      | ?x a bf:Work                                  |
      | ?x bf:classification ?y                       |
      | ?y a bf:Classification                        |
      | ?y bf:itemPortion "L3"                        |
    Then I should find 1 match
