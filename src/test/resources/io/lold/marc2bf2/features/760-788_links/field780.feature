Feature: 780 - PRECEEDING ENTRY
  
  Background:
    Given a marc field "=780  05$aAmerican Society of International Law.$sPublications.$tProceedings$g1971$uMPC-387$z0491001304"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field780Converter

  Scenario: 780 creates a property of the Work determined by ind2
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:absorbed ?y                |
    Then I should find 1 match

  Scenario: $g creates a part property of the linked Instance
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:absorbed ?y                |
      | ?y a bf:Work                     |
      | ?y bf:hasInstance ?z             |
      | ?z a bf:Instance                 |
      | ?z bf:part "1971"                |
    Then I should find 1 match

  Scenario: $s creates a title property of the linked Work
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:absorbed ?y                |
      | ?y a bf:Work                     |
      | ?y bf:title ?z                   |
      | ?z a bf:Title                    |
      | ?z rdfs:label "Publications."    |
    Then I should find 1 match

  Scenario: $u creates an identifiedBy/Strn property of the linked Instance
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:absorbed ?y                |
      | ?y a bf:Work                     |
      | ?y bf:hasInstance ?z             |
      | ?z a bf:Instance                 |
      | ?z bf:identifiedBy ?i            |
      | ?i a bf:Strn                     |
      | ?i rdf:value "MPC-387"           |
    Then I should find 1 match

  Scenario: $z creates an identifiedBy/Isbn property of the linked Instance
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:absorbed ?y                |
      | ?y a bf:Work                     |
      | ?y bf:hasInstance ?z             |
      | ?z a bf:Instance                 |
      | ?z bf:identifiedBy ?i            |
      | ?i a bf:Isbn                     |
      | ?i rdf:value "0491001304"        |
    Then I should find 1 match