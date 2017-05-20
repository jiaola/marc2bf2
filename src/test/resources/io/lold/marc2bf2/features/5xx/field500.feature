Feature: 500 - GENERAL NOTE
  Background:
    Given a marc field "=500  \\$3Some stuff$aFrom the papers of the Chase family.$5DLC"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field500Converter

  Scenario: 500 creates a note property of the Instance
    When I search with patterns:
      | ?x a bf:Instance |
      | ?x bf:note ?y    |
      | ?y a bf:Note     |
      | ?y rdfs:label "From the papers of the Chase family." |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property of the Note
    When I search with patterns:
      | ?x a bf:Instance             |
      | ?x bf:note ?y                |
      | ?y a bf:Note                 |
      | ?y bflc:appliesTo ?z         |
      | ?z a bflc:AppliesTo          |
      | ?z rdfs:label "Some stuff"   |
    Then I should find 1 match

  Scenario: $5 creates a bflc:applicableInstitution property of the Note
    When I search with patterns:
      | ?x a bf:Instance                 |
      | ?x bf:note ?y                    |
      | ?y a bf:Note                     |
      | ?y bflc:applicableInstitution ?z |
      | ?z a bf:Agent                    |
      | ?z bf:code "DLC"                 |
    Then I should find 1 match
