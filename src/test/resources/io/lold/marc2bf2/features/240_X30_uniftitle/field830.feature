Feature: 830 - SERIES ADDED ENTRY - UNIFORM TITLE
  
  Background:
    Given a marc field "=830  \0$7ab$aNihon zenkoku panorama chizu ;$v1."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field830Converter

  Scenario: 830 becomes a hasSeries of the main Work
    When I search with patterns:
      | ?x a bf:Work                                   |
      | ?x bf:hasSeries ?y                             |
      | ?y a bf:Work                                   |
      | ?y bf:title ?z                                 |
      | ?z a bf:Title                                  |
      | ?z bf:mainTitle "Nihon zenkoku panorama chizu" |
    Then I should find 1 match

  Scenario: $7 sets the rdf:type of the Work
    When I search with patterns:
      | ?x a bf:Work |
      | ?x a bf:Text |
    Then I should find 1 match

  Scenario: $7 sets the issuance of the Work
    When I search with patterns:
      | ?x a bf:Work             |
      | ?x bf:issuance ?y        |
      | ?y a bf:Issuance         |
      | ?y bf:code "s"           |
    Then I should find 1 match

  Scenario: $v becomes a seriesEnumeration
    When I search with patterns:
      | ?x a bf:Work                       |
      | ?x bf:seriesEnumeration "1"        |
    Then I should find 1 match
