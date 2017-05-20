Feature: 770 - SUPPLEMENT / SPECIAL ISSUE ENTRY
  
  Background:
    Given a marc field "=770  0\$tJournal of cellular biochemistry. Supplement$rAPA 3011$x0733-1959"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field770Converter

  Scenario: 770 creates a supplement property of the Work
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:supplement ?y              |
    Then I should find 1 match

  Scenario: $r creates an identifiedBy/ReportNumber property of the linked Instance
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:supplement ?y              |
      | ?y a bf:Work                     |
      | ?y bf:hasInstance ?z             |
      | ?z a bf:Instance                 |
      | ?z bf:identifiedBy ?i            |
      | ?i a bf:ReportNumber             |
      | ?i rdf:value "APA 3011"          |
    Then I should find 1 match
