Feature: MARC field 350 - PRICE
  Background:
    Given a marc field "=350  \\$a20.00"
    When converted by a field converter io.lold.marc2bf2.converters.Field350Converter

  Scenario: 350 creates an acquisitionSource/AcquisitionSource property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:acquisitionSource ?y      |
      | ?y a bf:AcquisitionSource       |
      | ?y bf:acquisitionTerms "20.00"  |
    Then I should find 1 match
