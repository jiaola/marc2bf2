Feature: 086 - GOVERNMENT DOCUMENT CLASSIFICATION NUMBER

  Background:
    Given a marc field "=086  0\$aA 1.1:$zA 1.1/3:984"
    And a marc field "=086  \\$aHEU/G74.3C49$2ordocs"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field086Converter

  Scenario: 086 creates a classification/Classification property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                              |
      | ?x bf:classification ?y                       |
      | ?y a bf:Classification                        |
    Then I should find 3 matches

  Scenario: $a is the rdfs:label of the Classification
    When I search with patterns:
      | ?x a bf:Instance                              |
      | ?x bf:classification ?y                       |
      | ?y a bf:Classification                        |
      | ?y rdfs:label "A 1.1:"                        |
    Then I should find 1 match

  Scenario: $z creates an 'invalid' Classification
    When I search with patterns:
      | ?x a bf:Instance                              |
      | ?x bf:classification ?y                       |
      | ?y a bf:Classification                        |
      | ?y bf:status ?z                               |
      | ?z a bf:Status                                |
      | ?z rdfs:label "invalid"                       |
    Then I should find 1 match

  Scenario: $2 creates a source property of the classification
    When I search with patterns:
      | ?x a bf:Instance                              |
      | ?x bf:classification ?y                       |
      | ?y a bf:Classification                        |
      | ?y bf:source ?z                               |
      | ?z a bf:Source                                |
      | ?z rdfs:label "ordocs"                        |
    Then I should find 1 match

  Scenario: ind1 can create a source property of the Classification
    When I search with patterns:
      | ?x a bf:Instance                              |
      | ?x bf:classification ?y                       |
      | ?y a bf:Classification                        |
      | ?y bf:source ?z                               |
      | ?z a bf:Source                                |
      | ?z rdfs:label "sudocs"                        |
    Then I should find 2 matches
