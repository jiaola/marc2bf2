Feature: 1XX - NAMES - Main Entries
  Background:
    Given a marc field "=100  1\$aBeethoven, Ludwig van,$d1770-1827$c(Spirit)$0(DE-101c)310008891"
    When converted by a field converter io.lold.marc2bf2.converters.Field1XXNameConverter

  Scenario: 1XX creates a bflc:PrimaryContribution
    When I search with patterns:
      | ?x a bf:Work                  |
      | ?x bf:contribution ?y         |
      | ?y a bf:Contribution          |
      | ?y a bflc:PrimaryContribution |
    Then I should find 1 match

  Scenario: Name field with $4 becomes bf:role/bf:Role property with URI
    When I search with patterns:
      | ?x a bf:Work                           |
      | ?x bf:contribution ?y                  |
      | ?y a bf:Contribution                   |
      | ?y bf:role <http://id.loc.gov/vocabulary/relators/ctb> |
    Then I should find 1 matches

  Scenario: bf:Agent/bflc:nameMatchKey generation
    When I search with patterns:
      | ?x a bf:Work                           |
      | ?x bf:contribution ?y                  |
      | ?y a bf:Contribution                   |
      | ?y bf:agent ?z                         |
      | ?z a bf:Agent                          |
      | ?z bflc:name00MatchKey "Beethoven, Ludwig van, 1770-1827 (Spirit)" |
    Then I should find 1 matches

  Scenario: bf:Agent/bflc:primaryContributorNameMatchKey generation
    When I search with patterns:
      | ?x a bf:Work                           |
      | ?x bf:contribution ?y                  |
      | ?y a bf:Contribution                   |
      | ?y bf:agent ?z                         |
      | ?z a bf:Agent                          |
      | ?z bflc:primaryContributorName00MatchKey "Beethoven, Ludwig van, 1770-1827 (Spirit)" |
    Then I should find 1 matches

  Scenario: bf:Agent/bflc:nameMarcKey generation
    When I search with patterns:
      | ?x a bf:Work                           |
      | ?x bf:contribution ?y                  |
      | ?y a bf:Contribution                   |
      | ?y bf:agent ?z                         |
      | ?z a bf:Agent                          |
      | ?z bflc:name00MarcKey "1001 $aBeethoven, Ludwig van,$d1770-1827$c(Spirit)$0(DE-101c)310008891" |
    Then I should find 1 matches

  Scenario: bf:Agent/rdfs:label generation
    When I search with patterns:
      | ?x a bf:Work                           |
      | ?x bf:contribution ?y                  |
      | ?y a bf:Contribution                   |
      | ?y bf:agent ?z                         |
      | ?z a bf:Agent                          |
      | ?z rdfs:label "Beethoven, Ludwig van, 1770-1827 (Spirit)" |
    Then I should find 1 matches

  Scenario: $0 becomes a bf:identifiedBy property for the bf:Agent
    When I search with patterns:
      | ?x a bf:Agent                          |
      | ?x bf:identifiedBy ?y                  |
      | ?y a bf:Identifier                     |
      | ?y rdf:value "310008891"               |
    Then I should find 1 matches