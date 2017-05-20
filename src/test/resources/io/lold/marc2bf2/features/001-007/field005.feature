Feature: 005 - DATE AND TIME OF LAST TRANSACTION
  Scenario: 005 should set the AdminMetadata changeDate property for the work
    Given a marc field "=005  20110713212405.0"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field005Converter
    When I search with patterns:
      | ?x a bf:Work            |
      | ?x bf:adminMetadata ?y  |
      | ?y a bf:AdminMetadata   |
      | ?y bf:changeDate "2011-07-14T02:24:05Z"^^<http://www.w3.org/2001/XMLSchema#dateTime> |
    Then I should find 1 match