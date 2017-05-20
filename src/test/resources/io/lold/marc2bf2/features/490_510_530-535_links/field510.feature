Feature: 510 - CITATION/REFERENCES NOTE

  Background:
    Given a marc field "=510  4\$aIndex Medicus,$x0019-3879,$cp. 10, 50, and iii,$bv1n1, 1984-"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field510Converter

  Scenario: 510 creates a bflc:indexOf/Instance property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                        |
      | ?x bf:indexOf ?y                        |
      | ?y a bf:Instance                        |
    Then I should find 1 match

  Scenario: $a creates a title property of the indexOf/Instance
    When I search with patterns:
      | ?x a bf:Instance                        |
      | ?x bf:indexOf ?y                        |
      | ?y a bf:Instance                        |
      | ?y bf:title ?z                          |
      | ?z a bf:Title                           |
      | ?z rdfs:label "Index Medicus"           |
    Then I should find 1 match

  Scenario: $b creates a note property of the indexOf/Instance with noteType 'Coverage'
    When I search with patterns:
      | ?x a bf:Instance                        |
      | ?x bf:indexOf ?y                        |
      | ?y a bf:Instance                        |
      | ?y bf:note ?z                           |
      | ?z a bf:Note                            |
      | ?z bf:noteType "Coverage"               |
      | ?z rdfs:label "v1n1, 1984-"             |
    Then I should find 1 match

  Scenario: $c creates a note property of the indexOf/Instance with noteType 'Location'
    When I search with patterns:
      | ?x a bf:Instance                        |
      | ?x bf:indexOf ?y                        |
      | ?y a bf:Instance                        |
      | ?y bf:note ?z                           |
      | ?z a bf:Note                            |
      | ?z bf:noteType "Location"               |
      | ?z rdfs:label "p. 10, 50, and iii"      |
    Then I should find 1 match

  Scenario: $x creates an identifiedBy/Issn property of the indexOf/Instance
    When I search with patterns:
      | ?x a bf:Instance                        |
      | ?x bf:indexOf ?y                        |
      | ?y a bf:Instance                        |
      | ?y bf:identifiedBy ?z                   |
      | ?z a bf:Issn                            |
      | ?z rdf:value "0019-3879"                |

    Then I should find 1 match

