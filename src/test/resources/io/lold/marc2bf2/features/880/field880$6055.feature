Feature: 880$6055 - ALTERNATE GRAPHIC REPRESENTATION - CLASSIFICATION NUMBERS ASSIGNED IN CANADA
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  00$6055-01$aפ5050 .2$bט 5"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 055 creates a classification property of the Work
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:classification ?y              |
      | ?y a bf:ClassificationLcc            |
      | ?y bf:classificationPortion "פ5050 .2" |
    Then I should find 1 match
