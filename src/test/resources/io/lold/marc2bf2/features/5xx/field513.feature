Feature: 513 - TYPE OF REPORT AND PERIOD COVERED NOTE
  Background:
    Given a marc field "=513  \\$aQuarterly technical progress report;$bJan.-Apr. 1, 1977."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field513Converter

  Scenario: 513 creates a note/Note property of the Instance with noteType 'report type'
    When I search with patterns:
      | ?x a bf:Instance             |
      | ?x bf:note ?y                |
      | ?y a bf:Note                 |
      | ?y bf:noteType "report type" |
    Then I should find 1 match

  Scenario: $ab create an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance             |
      | ?x bf:note ?y                |
      | ?y a bf:Note                 |
      | ?y rdfs:label "Quarterly technical progress report; Jan.-Apr. 1, 1977." |
    Then I should find 1 match
