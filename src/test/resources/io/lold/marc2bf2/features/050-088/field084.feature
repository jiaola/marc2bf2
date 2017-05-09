Feature: 084 - OTHER CLASSIFICATION NUMBER

  Background:
    Given a marc field "=084  \\$aKB112.554$bU62 1980$qDE-101$2sdnb"
    When converted by a field converter io.lold.marc2bf2.converters.Field084Converter

  Scenario: 084 creates a classification/Classification property of the Work
    When I search with patterns:
      | ?x a bf:Work                                  |
      | ?x bf:classification ?y                       |
      | ?y a bf:Classification                        |
    Then I should find 1 match

  Scenario: $a creates the classificationPortion of the Classification
    When I search with patterns:
      | ?x a bf:Work                                  |
      | ?x bf:classification ?y                       |
      | ?y a bf:Classification                        |
      | ?y bf:classificationPortion "KB112.554"       |
    Then I should find 1 match

  Scenario: $b creates an itemPortion of the Classification
    When I search with patterns:
      | ?x a bf:Work                                  |
      | ?x bf:classification ?y                       |
      | ?y a bf:Classification                        |
      | ?y bf:itemPortion "U62 1980"                  |
    Then I should find 1 match


  Scenario: $q creates an adminMetadata/AdminMetadata/assigner property of the Classification
    When I search with patterns:
      | ?x a bf:Work                                  |
      | ?x bf:classification ?y                       |
      | ?y a bf:Classification                        |
      | ?y bf:adminMetadata ?z                        |
      | ?z a bf:AdminMetadata                         |
      | ?z bf:assigner ?a                             |
      | ?a a bf:Agent                                 |
      | ?a rdfs:label "DE-101"                        |
    Then I should find 1 match

  Scenario: $2 creates a source property of the Classification
    When I search with patterns:
      | ?x a bf:Work                                  |
      | ?x bf:classification ?y                       |
      | ?y a bf:Classification                        |
      | ?y bf:source ?z                               |
      | ?z a bf:Source                                |
      | ?z rdfs:label "sdnb"                          |
    Then I should find 1 match
