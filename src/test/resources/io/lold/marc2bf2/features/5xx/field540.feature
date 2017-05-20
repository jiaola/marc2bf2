Feature: 540 - TERMS GOVERNING USE AND REPRODUCTION NOTE
  Background:
    Given a marc field "=540  \\$3Diaries$aPhotocopying prohibited;$c50 Stat.88;$dExecutor of estate.$uhttp://lcweb.loc.gov/rr/print/195_copr.html$5DLC"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field540Converter

  Scenario: 540 creates a usageAndAccessPolicy/UsePolicy property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:usageAndAccessPolicy ?y          |
      | ?y a bf:UsePolicy                      |
    Then I should find 1 match

  Scenario: $a creates an rdfs:label property of the UsePolicy
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:usageAndAccessPolicy ?y          |
      | ?y a bf:UsePolicy                      |
      | ?y rdfs:label "Photocopying prohibited"|
    Then I should find 1 match

  Scenario: $c creates a source property of the UsePolicy
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:usageAndAccessPolicy ?y          |
      | ?y a bf:UsePolicy                      |
      | ?y bf:source ?z                        |
      | ?z a bf:Source                         |
      | ?z rdfs:label "50 Stat.88"             |
    Then I should find 1 match

  Scenario: $d creates a note property of the UsePolicy
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:usageAndAccessPolicy ?y          |
      | ?y a bf:UsePolicy                      |
      | ?y bf:note ?z                          |
      | ?z a bf:Note                           |
      | ?z rdfs:label "Authorized users: Executor of estate" |
    Then I should find 1 match

  Scenario: $u creates an rdfs:label property of the UsePolicy
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:usageAndAccessPolicy ?y          |
      | ?y a bf:UsePolicy                      |
      | ?y rdfs:label "http://lcweb.loc.gov/rr/print/195_copr.html"^^xsd:anyURI |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property of the UsePolicy
    When I search with patterns:
      | ?x a bf:Instance                 |
      | ?x bf:usageAndAccessPolicy ?y    |
      | ?y a bf:UsePolicy                |
      | ?y bflc:appliesTo ?z             |
      | ?z a bflc:AppliesTo              |
      | ?z rdfs:label "Diaries"          |
    Then I should find 1 match

  Scenario: $5 creates a bflc:applicableInstitution property of the UsePolicy
    When I search with patterns:
      | ?x a bf:Instance                 |
      | ?x bf:usageAndAccessPolicy ?y    |
      | ?y a bf:UsePolicy                |
      | ?y bflc:applicableInstitution ?z |
      | ?z a bf:Agent                    |
      | ?z bf:code "DLC"                 |
    Then I should find 1 match
