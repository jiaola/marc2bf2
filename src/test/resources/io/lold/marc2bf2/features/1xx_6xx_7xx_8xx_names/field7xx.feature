Feature: 7XX - NAMES - Added Entry Fields
  Background:
    Given a marc field "=700  1\$iParody of (work):$aCarroll, Lewis,$d1832-1898.$tAlice's adventures in Wonderland$0(isni)0000000121358464"
    And a marc field "=700  12$aVoltaire,$d1694-1778.$tCorrespondence.$kSelections.$f1777."
    And a marc field "=700  1\$aHecht, Ben,$d1893-1964,$ewriting, direction, and production.$econception,$ecreative consulting.$uChemistry Dept., American University."
    And a marc field "=700  1\$aGalway, James.$4prf$4cnd$0http://id.loc.gov/authorities/names/n81042545"
    When converted by a field converter io.lold.marc2bf2.converters.field1XX_6XX_7XX_8XX.Field7XXNameConverter

  Scenario: 7XX with $t, ind2 != 2 creates a bf:relatedTo
    When I search with patterns:
      | ?x a bf:Work                           |
      | ?x bf:relatedTo ?y                     |
      | ?y a bf:Work                           |
      | ?y bf:title ?z                         |
      | ?z a bf:Title                          |
      | ?z rdfs:label "Alice's adventures in Wonderland" |
    Then I should find 1 match

  Scenario: 7XX with $t, ind2 = 2, creates a bf:hasPart
    When I search with patterns:
      | ?x a bf:Work                           |
      | ?x bf:hasPart ?y                       |
      | ?y a bf:Work                           |
      | ?y bf:title ?z                         |
      | ?z a bf:Title                          |
      | ?z bf:mainTitle "Correspondence"       |
    Then I should find 1 match

  Scenario: Name field with $e, $j, or $4 becomes a bf:Contribution
    When I search with patterns:
      | ?x a bf:Work                           |
      | ?x bf:contribution ?y                  |
      | ?y a bf:Contribution                   |
      | ?y bf:agent ?z                         |
      | ?z a bf:Agent                          |
      | ?z rdfs:label "Hecht, Ben, 1893-1964," |
    Then I should find 1 match

  Scenario: Name field with $e/$j become bf:role properties with blank bf:Role node
    When I search with patterns:
      | ?x a bf:Work                           |
      | ?x bf:contribution ?y                  |
      | ?y a bf:Contribution                   |
      | ?y bf:agent ?z                         |
      | ?z a bf:Agent                          |
      | ?z rdfs:label "Hecht, Ben, 1893-1964," |
      | ?y bf:role ?r                          |
    Then I should find 5 matches

  Scenario: Name field with $t creates a bf:Title
    When I search with patterns:
      | ?x a bf:Work    |
      | ?x bf:title ?y  |
      | ?y a bf:Title   |
    Then I should find 2 matches

  Scenario: If there's a $t, $0 becomes a bf:identifiedBy property for the bf:Work
    When I search with patterns:
      | ?x a bf:Work                           |
      | ?x bf:identifiedBy ?y                  |
      | ?y a bf:Identifier                     |
      | ?y rdf:value "0000000121358464"        |
    Then I should find 1 match

  Scenario: If there's a $t, and $0 is a URI, $0 becomes the rdf:about attribute of the bf:Agent or bf:Work
    When I search with patterns:
      | ?x a bf:Work                                                |
      | ?x bf:contribution ?y                                       |
      | ?y a bf:Contribution                                        |
      | ?y bf:agent <http://id.loc.gov/authorities/names/n81042545> |
      | <http://id.loc.gov/authorities/names/n81042545> a bf:Agent  |
    Then I should find 1 match



