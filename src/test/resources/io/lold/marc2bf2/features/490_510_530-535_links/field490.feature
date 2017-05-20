Feature: 490 - SERIES STATEMENT
  
  Background:
    Given a marc field "=490  1\$aMémoire du BRGM,$x0071-8246 ;$vno 123"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field490Converter

  Scenario: $a creates a seriesStatement property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                        |
      | ?x bf:seriesStatement "Mémoire du BRGM" |
    Then I should find 1 match

  Scenario: $v creates a seriesEnumeration property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:seriesEnumeration "no 123"    |
    Then I should find 1 match

  Scenario: $x creates a hasSeries property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:hasSeries ?y              |
      | ?y a bf:Instance                |
      | ?y bf:identifiedBy ?z           |
      | ?z a bf:Issn                    |
      | ?z rdf:value "0071-8246"        |
    Then I should find 1 match
