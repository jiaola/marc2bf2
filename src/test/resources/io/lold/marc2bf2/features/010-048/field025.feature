Feature: 025 - OVERSEAS ACQUISITION NUMBER

  Scenario: $a creates an identifiedBy/LcOverseasAcq property of the Instance
    Given a marc field "=025  \\$aLACAP72-1719"
    When converted by a field converter io.lold.marc2bf2.converters.field010to048.Field025Converter
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:LcOverseasAcq                |
      | ?y rdf:value "LACAP72-1719"          |
    Then I should find 1 match
