Feature: 880$6082 - ALTERNATE GRAPHIC REPRESENTATION - DEWEY DECIMAL CLASSIFICATION NUMBER
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  00$6082-01$a975,5 / 4252/00222$bוואָר$222"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 082 creates a classification property of the Work
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:classification ?y              |
      | ?y a bf:ClassificationDdc            |
      | ?y bf:classificationPortion "975,5 / 4252/00222" |
    Then I should find 1 match
