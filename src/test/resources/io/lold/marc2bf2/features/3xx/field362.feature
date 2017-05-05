Feature: MARC field 362 - DATES OF PUBLICATION AND/OR SEQUENTIAL DESIGNATION
  Background:
    Given a marc field "=362  0\$aVol. 1, no. 1 (Apr. 1983)-v. 1, no. 3 (June 1983)"
    And a marc field "=362  1\$a3 issues in vol. 1, ceased with 3 (1983)"
    When converted by a field converter io.lold.marc2bf2.converters.Field362Converter

  Scenario: ind1=0 creates a firstIssue/lastIssue property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                             |
      | ?x bf:firstIssue "Vol. 1, no. 1 (Apr. 1983)" |
      | ?x bf:lastIssue "v. 1, no. 3 (June 1983)"    |
    Then I should find 1 match

  Scenario: ind1=1 creates a note property of the Instance with noteType 'Numbering'
    When I search with patterns:
      | ?x a bf:Instance                                         |
      | ?x bf:note ?y                                            |
      | ?y a bf:Note                                             |
      | ?y bf:noteType "Numbering"                               |
      | ?y rdfs:label "3 issues in vol. 1, ceased with 3 (1983)" |
    Then I should find 1 match

