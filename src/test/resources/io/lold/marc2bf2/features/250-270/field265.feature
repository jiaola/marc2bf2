Feature: 265 - SOURCE FOR ACQUISITION/SUBSCRIPTION ADDRESS [OBSOLETE]

  Scenario: 265 creates an acquisitionSource of the Instance
    Given a marc field "=265  \\$aU.S. Geological Survey, Reston, Va. 22091"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field265Converter
    When I search with patterns:
      | ?x a bf:Instance                                                    |
      | ?x bf:acquisitionSource "U.S. Geological Survey, Reston, Va. 22091" |
    Then I should find 1 match
