Feature: MARC leader

  Scenario: pos 5 should set the status in Work adminMetadata
    Given a marc leader "=LDR  01094cam a2200229 a 4500"
    When converted by a leader converter io.lold.marc2bf2.converters.LeaderConverter
    When I search with patterns:
      | ?x a bf:Work              |
      | ?x bf:adminMetadata ?y    |
      | ?y a bf:AdminMetadata     |
      | ?y bf:status ?z           |
      | ?z a bf:Status            |
      | ?z bf:code "c"            |
    Then I should find 1 match

  Scenario: pos 6 should set the rdf:type of the Work
    Given a marc leader "=LDR  01094cdm a2200229 a 4500"
    When converted by a leader converter io.lold.marc2bf2.converters.LeaderConverter
    When I search with patterns:
      | ?x a bf:Work         |
      | ?x a bf:NotatedMusic |
    Then I should find 1 match

  Scenario: pos 6 can also set the rdf:type of the Instance
    Given a marc leader "=LDR  01094cdm a2200229 a 4500"
    When converted by a leader converter io.lold.marc2bf2.converters.LeaderConverter
    When I search with patterns:
      | ?x a bf:Instance    |
      | ?x a bf:Manuscript  |
    Then I should find 1 match

  Scenario: pos 7 should set the issuance of the Instance
    Given a marc leader "=LDR  01094cam a2200229 a 4500"
    When converted by a leader converter io.lold.marc2bf2.converters.LeaderConverter
    When I search with patterns:
      | ?x a bf:Instance                                            |
      | ?x bf:issuance <http://id.loc.gov/vocabulary/issuance/mono> |
      | <http://id.loc.gov/vocabulary/issuance/mono> a bf:Issuance  |
    Then I should find 1 match

  Scenario: pos 7 can also set the rdf:type of the Instance
    Given a marc leader "=LDR  01094cac a2200229 aa4500"
    When converted by a leader converter io.lold.marc2bf2.converters.LeaderConverter
    When I search with patterns:
      | ?x a bf:Instance      |
      | ?x a bf:Collection    |
    Then I should find 1 match

  Scenario: pos 8 can set the rdf:type of the Instance
    Given a marc leader "=LDR  01094camaa2200229 a 4500"
    When converted by a leader converter io.lold.marc2bf2.converters.LeaderConverter
    When I search with patterns:
      | ?x a bf:Instance    |
      | ?x a bf:Archival    |
    Then I should find 1 match

  Scenario: pos 17 should set the encodingLevel in Work adminMetadata
    Given a marc leader "=LDR  01094cam a2200229 a 4500"
    When converted by a leader converter io.lold.marc2bf2.converters.LeaderConverter
    When I search with patterns:
      | ?x a bf:AdminMetadata    |
      | ?x bflc:encodingLevel ?y |
      | ?y a bflc:EncodingLevel  |
      | ?y bf:code "f"           |
    Then I should find 1 match

  Scenario: pos 18 should set the descriptionConventions in Work adminMetadata
    Given a marc leader "=LDR  01094cam a2200229 a 4500"
    When converted by a leader converter io.lold.marc2bf2.converters.LeaderConverter
    When I search with patterns:
      | ?x a bf:AdminMetadata           |
      | ?x bf:descriptionConventions ?y |
      | ?y a bf:DescriptionConventions  |
      | ?y bf:code "aacr"               |
    Then I should find 1 match


