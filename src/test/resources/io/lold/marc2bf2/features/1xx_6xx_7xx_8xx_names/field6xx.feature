Feature: 6XX - NAMES - Subject Access Fields
  Background:
    Given a marc field "=600  10$aNixon, Richard M.$q(Richard Milhouse),$d1913-$xPsychology."
    And a marc field "=600  10$aBellamy, Edward,$b1850-1898.$tLooking backward, 2000-1887."
    When converted by a field converter io.lold.marc2bf2.converters.Field6XXNameConverter

  Scenario: 600,610,611 without $t creates a subject/Agent property of the Work
            also has a Class from MADSRDF
            with rdfs:label composed according to Name processing rules
    When I search with patterns:
      | ?x a bf:Work                                                |
      | ?x bf:subject ?y                                            |
      | ?y a bf:Agent                                               |
      | ?y a <http://www.loc.gov/mads/rdf/v1#ComplexSubject>        |
      | ?y rdfs:label "Nixon, Richard M. (Richard Milhouse), 1913-" |
    Then I should find 1 match

  Scenario: It creates a madsrdf:authoritativeLabel property for the Agent
            and a madsrdf:isMemberofMADSScheme property
    When I search with patterns:
      | ?x a bf:Agent                                                                            |
      | ?x madsrdf:authoritativeLabel "Nixon, Richard M. (Richard Milhouse), 1913---Psychology." |
      | ?x madsrdf:isMemberofMADSScheme <http://id.loc.gov/authorities/subjects>                 |
    Then I should find 1 match

  Scenario: ind2 creates a source property of the Agent
    When I search with patterns:
      | ?x a bf:Agent          |
      | ?x bf:source ?y        |
      | ?y a bf:Source         |
      | ?y bf:code "lcsh"      |
    Then I should find 2 matches

  Scenario: 600,610,611 with $t creates a subject/Work property of the Work
            with contribution from Name portion of field
            and title from the Title portion
    When I search with patterns:
      | ?x a bf:Work                 |
      | ?x bf:subject ?y             |
      | ?y a bf:Work                 |
      | ?y bf:contribution ?z        |
      | ?z a bf:Contribution         |
      | ?z bf:agent ?a               |
      | ?a a bf:Agent                |
      | ?a rdfs:label "Bellamy, Edward, 1850-1898." |
    Then I should find 1 match

  Scenario: 600,610,611 with $t creates a subject/Work property of the Work
            with a Class from MADSRDF
            and a madsrdf:authoritativeLabel property
    When I search with patterns:
      | ?x a bf:Work                 |
      | ?x bf:subject ?y             |
      | ?y a bf:Work                 |
      | ?y a madsrdf:NameTitle       |
      | ?y madsrdf:authoritativeLabel "Bellamy, Edward, 1850-1898. Looking backward, 2000-1887." |
    Then I should find 1 match





