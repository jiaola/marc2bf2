Feature: 263 - PROJECTED PUBLICATION DATE

  Scenario: 250 creates an note property of the Instance
    Given a marc field "=263  \\$a1999--"
    When converted by a field converter io.lold.marc2bf2.converters.Field263Converter
    When I search with patterns:
      | ?x bflc:projectedProvisionDate "1999-XX"^^<http://id.loc.gov/datatypes/edtf> |
      | ?x a bf:Instance                                                             |
    Then I should find 1 match
