Feature: 385 - AUDIENCE CHARACTERISTICS
  Background:
    Given a marc field "=385  \\$3Nonsense$mAge group$nage$aChildren$bkid$2ws"
    And a marc field "=385  \\$aYoungsters$baaa$0(DLC)dg2015060010"
    When converted by a field converter io.lold.marc2bf2.converters.field3XX.Field385_386Converter

  Scenario: $a creates an intendedAudience property of the Work
    When I search with patterns:
      | ?x a bf:Work                |
      | ?x bf:intendedAudience ?y   |
      | ?y a bf:IntendedAudience    |
      | ?y rdfs:label "Children"    |
    Then I should find 1 match

  Scenario: $b creates a code property of the IntendedAudience...
    When I search with patterns:
      | ?x a bf:Work                |
      | ?x bf:intendedAudience ?y   |
      | ?y a bf:IntendedAudience    |
      | ?y bf:code "kid"            |
    Then I should find 1 match

  Scenario: If there is a $0 with a standard number value starting with 'dg',
            $b also creates a bflc:demographicGroup with a URI in demographicTerms
    When I search with patterns:
      | ?x a bf:Work                |
      | ?x bf:intendedAudience ?y   |
      | ?y a bf:IntendedAudience    |
      | ?y bflc:demographicGroup <http://id.loc.gov/authorities/demographicTerms/dg2015060010> |
    Then I should find 1 match

  Scenario: $m creates a bflc:demographicGroup property of the IntendedAudience
    When I search with patterns:
      | ?x a bf:IntendedAudience         |
      | ?x bflc:demographicGroup $y      |
      | ?y a bflc:DemographicGroup       |
      | ?y rdfs:label "Age group"        |
    Then I should find 1 match

  Scenario: $n creates a bflc:demographicGroup property of the IntendedAudience
    When I search with patterns:
      | ?x a bf:IntendedAudience         |
      | ?x bflc:demographicGroup $y      |
      | ?y a bflc:DemographicGroup       |
      | ?y rdfs:label "age"              |
    Then I should find 1 match

  Scenario: $2 creates a source property of the MusicMedium
    When I search with patterns:
      | ?x a bf:Work                  |
      | ?x bf:intendedAudience ?y     |
      | ?y a bf:IntendedAudience      |
      | ?y bf:source ?z               |
      | ?z a bf:Source                |
      | ?z rdfs:label "ws"            |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property of the MusicMedium
    When I search with patterns:
      | ?x a bf:Work                  |
      | ?x bf:intendedAudience ?y     |
      | ?y a bf:IntendedAudience      |
      | ?y bflc:appliesTo ?z          |
      | ?z a bflc:AppliesTo           |
      | ?z rdfs:label "Nonsense"      |
    Then I should find 1 match
