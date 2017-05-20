Feature: 502 - DISSERTATION NOTE
  Background:
    Given a marc field "=502  \\$aKarl Schmidt's thesis (doctoral)--Ludwig-Maximilians-Universität, Munich, 1965."
    And a marc field "=502  \\$gKarl Schmidt's thesis$bDoctoral$cLudwig-Maximilians-Universität, Munich$d1965$oU 58.4033."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field502Converter

  Scenario: 502 creates a dissertation/Dissertation property of the Work
    When I search with patterns:
      | ?x a bf:Work           |
      | ?x bf:dissertation ?y  |
      | ?y a bf:Dissertation   |
    Then I should find 2 matches

  Scenario: $a creates an rdfs:label property of the Dissertation
    When I search with patterns:
      | ?x a bf:Work           |
      | ?x bf:dissertation ?y  |
      | ?y a bf:Dissertation   |
      | ?y rdfs:label "Karl Schmidt's thesis (doctoral)--Ludwig-Maximilians-Universität, Munich, 1965." |
    Then I should find 1 match

  Scenario: $b creates a degree property of the Dissertation
    When I search with patterns:
      | ?x a bf:Work           |
      | ?x bf:dissertation ?y  |
      | ?y a bf:Dissertation   |
      | ?y bf:degree "Doctoral"|
    Then I should find 1 match

  Scenario: $c creates a grantingInstitution property of the Dissertation
    When I search with patterns:
      | ?x a bf:Work                                           |
      | ?x bf:dissertation ?y                                  |
      | ?y a bf:Dissertation                                   |
      | ?y bf:grantingInstitution ?z                           |
      | ?z a bf:Agent                                          |
      | ?z rdfs:label "Ludwig-Maximilians-Universität, Munich" |
    Then I should find 1 match

  Scenario: $d creates a date property of the Dissertation
    When I search with patterns:
      | ?x a bf:Work           |
      | ?x bf:dissertation ?y  |
      | ?y a bf:Dissertation   |
      | ?y bf:date "1965"      |
    Then I should find 1 match

  Scenario: $g creates a note property of the Dissertation
    When I search with patterns:
      | ?x a bf:Work                                           |
      | ?x bf:dissertation ?y                                  |
      | ?y a bf:Dissertation                                   |
      | ?y bf:note ?z                                          |
      | ?z a bf:Note                                           |
      | ?z rdfs:label "Karl Schmidt's thesis"                  |
    Then I should find 1 match

  Scenario: $o creates an identifiedBy property of the Dissertation
    When I search with patterns:
      | ?x a bf:Work                                           |
      | ?x bf:dissertation ?y                                  |
      | ?y a bf:Dissertation                                   |
      | ?y bf:identifiedBy ?z                                  |
      | ?z a bf:DissertationIdentifier                         |
      | ?z rdf:value "U 58.4033."                              |
    Then I should find 1 match


