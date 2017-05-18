Feature: 774 - CONSTITUENT UNIT ENTRY
  
  Background:
    Given a marc field "=774  0\$oNYDA.1993.010.00132.$n[DIAPimage]$hfile on disc$m240 x 760px$tView SE from Mill Brook Houses on rooftop on Cypress Ave. Between 136th St. and 137th St.,$d93/05"
    When converted by a field converter io.lold.marc2bf2.converters.field760to788.Field774Converter

  Scenario: 774 creates a hasPart property of the Work
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:hasPart ?y                 |
    Then I should find 1 match

  Scenario: $h creates an extent property of the linked Instance
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:hasPart ?y                 |
      | ?y a bf:Work                     |
      | ?y bf:hasInstance ?z             |
      | ?z a bf:Instance                 |
      | ?z bf:extent ?e                  |
      | ?e a bf:Extent                   |
      | ?e rdfs:label "file on disc"     |
    Then I should find 1 match

  Scenario: $m creates a note property of the linked Instance
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:hasPart ?y                 |
      | ?y a bf:Work                     |
      | ?y bf:hasInstance ?z             |
      | ?z a bf:Instance                 |
      | ?z bf:note ?e                    |
      | ?e a bf:Note                     |
      | ?e rdfs:label "240 x 760px"      |
    Then I should find 1 match

  Scenario: $n creates a note property of the linked Instance
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:hasPart ?y                 |
      | ?y a bf:Work                     |
      | ?y bf:hasInstance ?z             |
      | ?z a bf:Instance                 |
      | ?z bf:note ?e                    |
      | ?e a bf:Note                     |
      | ?e rdfs:label "[DIAPimage]"      |
    Then I should find 1 match