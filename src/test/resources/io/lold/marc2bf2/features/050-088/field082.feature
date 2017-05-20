Feature: 082 - DEWEY DECIMAL CLASSIFICATION NUMBER

  Background:
    Given a marc field "=082  00$a975.5/4252/00222$bWor$222"
    And a marc field "=082  04$a004$222/ger$qDE-101b"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field082Converter

  Scenario: 082 creates a classification/ClassificationDdc property of the Work
    When I search with patterns:
      | ?x a bf:Work                                  |
      | ?x bf:classification ?y                       |
      | ?y a bf:ClassificationDdc                     |
    Then I should find 2 matches

  Scenario: $a creates the classificationPortion of the ClassificationDdc
    When I search with patterns:
      | ?x a bf:Work                                   |
      | ?x bf:classification ?y                        |
      | ?y a bf:ClassificationDdc                      |
      | ?y bf:classificationPortion "975.5/4252/00222" |
    Then I should find 1 match

  Scenario: $b creates an itemPortion of the ClassificationDdc
    When I search with patterns:
      | ?x a bf:Work                                   |
      | ?x bf:classification ?y                        |
      | ?y a bf:ClassificationDdc                      |
      | ?y bf:itemPortion "Wor"                        |
    Then I should find 1 match

  Scenario: $2 creates an edition property of the ClassificationDdc
    When I search with patterns:
      | ?x a bf:Work                                   |
      | ?x bf:classification ?y                        |
      | ?y a bf:ClassificationDdc                      |
      | ?y bf:edition "22"                             |
    Then I should find 1 match

  Scenario: $q creates a source property of the ClassificationDdc
    When I search with patterns:
      | ?x a bf:Work                                   |
      | ?x bf:classification ?y                        |
      | ?y a bf:ClassificationDdc                      |
      | ?y bf:source ?z                                |
      | ?z a bf:Source                                 |
      | ?z rdfs:label "DE-101b"                        |
    Then I should find 1 match

  Scenario: ind1 creates an edition property of the ClassificationDdc
    When I search with patterns:
      | ?x a bf:Work                                   |
      | ?x bf:classification ?y                        |
      | ?y a bf:ClassificationDdc                      |
      | ?y bf:edition "full"                           |
    Then I should find 2 match

  Scenario: ind2 can set a source property of the ClassificationDdc
    When I search with patterns:
      | ?x a bf:Work                                   |
      | ?x bf:classification ?y                        |
      | ?y a bf:ClassificationDdc                      |
      | ?y bf:source <http://id.loc.gov/vocabulary/organizations/dlc> |
      | <http://id.loc.gov/vocabulary/organizations/dlc> a bf:Source  |
    Then I should find 1 match

