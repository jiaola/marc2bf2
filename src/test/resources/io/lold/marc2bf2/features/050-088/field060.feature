Feature: 060 - NATIONAL LIBRARY OF MEDICINE CALL NUMBER

  Background:
    Given a marc field "=060  00$aW 22 DC2.1$bB8M"
    When converted by a field converter io.lold.marc2bf2.converters.field050to088.Field060Converter

  Scenario: 060 creates a classification/ClassificationNlm property of the Work
    When I search with patterns:
      | ?x a bf:Work                |
      | ?x bf:classification ?y     |
      | ?y a bf:ClassificationNlm   |
    Then I should find 1 match
    
  Scenario: $a creates the classificationPortion of the ClassificationNlm
    When I search with patterns:
      | ?x a bf:Work                             |
      | ?x bf:classification ?y                  |
      | ?y a bf:ClassificationNlm                |
      | ?y bf:classificationPortion "W 22 DC2.1" |
    Then I should find 1 match

  Scenario: $b creates an itemPortion of the ClassificationNlm
    When I search with patterns:
      | ?x a bf:Work                  |
      | ?x bf:classification ?y       |
      | ?y a bf:ClassificationNlm     |
      | ?y bf:itemPortion "B8M"       |
    Then I should find 1 match

  Scenario: ind1 = '0' creates an Item with a heldBy property
    When I search with patterns:
      | ?x a bf:Item                  |
      | ?x bf:heldBy ?y               |
      | ?y a bf:Agent                 |
      | ?y rdfs:label "National Library of Medicine" |
    Then I should find 1 match

  Scenario: ...and a hasItem property of the Instance
    When I search with patterns:
      | ?x a bf:Instance    |
      | ?x bf:hasItem ?y    |
    Then I should find 1 match

  Scenario: ind2 = '0' creates a source property of the ClassificationNlm
    When I search with patterns:
      | ?x a bf:Work                                 |
      | ?x bf:classification ?y                      |
      | ?y a bf:ClassificationNlm                    |
      | ?y bf:source ?z                              |
      | ?z a bf:Source                               |
      | ?z rdfs:label "National Library of Medicine" |
    Then I should find 1 match
