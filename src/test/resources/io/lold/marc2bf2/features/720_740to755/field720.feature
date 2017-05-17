Feature: 720 - ADDED ENTRY--UNCONTROLLED NAME
  
  Background:
    Given a marc field "=720  1\$aTheodore K. Hepburn$einventor"
    When converted by a field converter io.lold.marc2bf2.converters.field720_740to755.Field720Converter

  Scenario: 720 creates a contribution/Contribution/agent/Agent property of the Work
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:contribution ?y                |
      | ?y a bf:Contribution                 |
      | ?y bf:agent ?z                       |
      | ?z a bf:Agent                        |
      | ?z rdfs:label "Theodore K. Hepburn"  |
    Then I should find 1 match

  Scenario: ind1=1 adds the Person Class to the Agent
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:contribution ?y                |
      | ?y a bf:Contribution                 |
      | ?y bf:agent ?z                       |
      | ?z a bf:Agent                        |
      | ?z a bf:Person                       |
    Then I should find 1 match



