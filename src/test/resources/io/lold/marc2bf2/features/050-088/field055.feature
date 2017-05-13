Feature: 055 - CLASSIFICATION NUMBERS ASSIGNED IN CANADA

  Background:
    Given a marc field "=055  00$aF5050 .2$bT5"
    When converted by a field converter io.lold.marc2bf2.converters.field050to088.Field055Converter

  Scenario: 055 creates a classification/ClassificationLcc property of the Work
    When I search with patterns:
      | ?x a bf:Work                |
      | ?x bf:classification ?y     |
      | ?y a bf:ClassificationLcc   |
    Then I should find 1 match

  Scenario: $a creates the classificationPortion of the ClassificationLcc
    When I search with patterns:
      | ?x a bf:Work                           |
      | ?x bf:classification ?y                |
      | ?y a bf:ClassificationLcc              |
      | ?y bf:classificationPortion "F5050 .2" |
    Then I should find 1 match

  Scenario: $b creates an itemPortion of the ClassificationLcc
    When I search with patterns:
      | ?x a bf:Work                  |
      | ?x bf:classification ?y       |
      | ?y a bf:ClassificationLcc     |
      | ?y bf:itemPortion "T5"        |
    Then I should find 1 match

  Scenario: ind2 = 0,1,2 creates a source of the ClassificationLcc
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:classification ?y                     |
      | ?y a bf:ClassificationLcc                   |
      | ?y bf:source ?z                             |
      | ?z a bf:Source                              |
      | ?z rdfs:label "Library and Archives Canada" |
    Then I should find 1 match

