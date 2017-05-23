Feature: 880$6350 - ALTERNATE GRAPHIC REPRESENTATION - PRICE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6350-01$a20.00"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 350 creates an acquisitionSource property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:acquisitionSource ?y          |
      | ?y a bf:AcquisitionSource           |
      | ?y bf:acquisitionTerms "20.00"      |
    Then I should find 1 match
