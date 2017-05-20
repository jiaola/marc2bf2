Feature: 250 - EDITION STATEMENT

  Scenario: 250 creates an editionStatement property of the Instance
    Given a marc field "=250  \\$a3rd draft /$bedited by Paul Watson.$32000-2006"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field250Converter
    When I search with patterns:
      | ?x a bf:Instance                                           |
      | ?x bf:editionStatement "3rd draft / edited by Paul Watson" |
    Then I should find 1 match
