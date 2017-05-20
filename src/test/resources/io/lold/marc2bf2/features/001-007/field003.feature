Feature: 003 - CONTROL NUMBER IDENTIFIER
  Scenario: 003 should set the AdminMetadata source property for the work
    Given a marc field "=003  DE-101"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field003Converter
    When I search with patterns:
      | ?x a bf:Work            |
      | ?x bf:adminMetadata ?y  |
      | ?y a bf:AdminMetadata   |
      | ?y bf:source ?z         |
      | ?z a bf:Source          |
      | ?z bf:code "DE-101"     |
    Then I should find 1 match

