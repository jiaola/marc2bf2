Feature: 880$6362 - ALTERNATE GRAPHIC REPRESENTATION - DATES OF PUBLICATION AND/OR SEQUENTIAL DESIGNATION
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  1\$6362-01$a3 выпуска в том. 1, прекратилось с 3 (1983)"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 362 creates a firstIssue/lastIssue or note property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:note ?y                       |
      | ?y a bf:Note                        |
      | ?y rdfs:label "3 выпуска в том. 1, прекратилось с 3 (1983)" |
      | ?y bf:noteType "Numbering"          |
    Then I should find 1 match

