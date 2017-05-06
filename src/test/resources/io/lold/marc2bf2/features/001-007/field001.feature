Feature: 001 - CONTROL NUMBER
  Scenario: 001 should set the AdminMetadata identifiedBy property for the work
    Given a marc field "=001  13600108"
    When converted by a field converter io.lold.marc2bf2.converters.Field001Converter
    When I search with patterns:
      | ?x a bf:Work            |
      | ?x bf:adminMetadata ?y  |
      | ?y a bf:AdminMetadata   |
      | ?y bf:identifiedBy ?z   |
      | ?z a bf:Local           |
      | ?z rdf:value "13600108" |
    Then I should find 1 match