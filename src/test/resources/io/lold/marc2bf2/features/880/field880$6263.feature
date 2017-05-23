Feature: 880$6263 - ALTERNATE GRAPHIC REPRESENTATION - PROJECTED PUBLICATION DATE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6263-01$a1999--"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 263 creates a bflc:projectedPubDate property of the Instance
    When I search with patterns:
      | ?x bflc:projectedProvisionDate "1999-XX"^^<http://id.loc.gov/datatypes/edtf>    |
      | ?x a bf:Instance                    |
    Then I should find 1 match

