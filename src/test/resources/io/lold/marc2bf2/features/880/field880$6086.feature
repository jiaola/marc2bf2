Feature: 880$6086 - ALTERNATE GRAPHIC REPRESENTATION - GOVERNMENT DOCUMENT CLASSIFICATION NUMBER
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  0\$6086-01$aA 1.1:$zA 1.1/3:984"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 086 creates a classification property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:classification ?y              |
      | ?y a bf:Classification               |
      | ?y rdfs:label "A 1.1:"               |
    Then I should find 1 match