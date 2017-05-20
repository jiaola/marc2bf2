Feature: 550 - ISSUING BODY NOTE
  Background:
    Given a marc field "=550  \\$aVols. for 1972- issued with: Bureau de recherches géologiques et minières."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field550Converter

  Scenario: 550 creates a note/Note property of the Instance with noteType 'issuing body'
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x bf:note ?y                  |
      | ?y a bf:Note                   |
      | ?y bf:noteType "issuing body"  |
    Then I should find 1 match

  Scenario: $a creates an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x bf:note ?y                  |
      | ?y a bf:Note                   |
      | ?y rdfs:label "Vols. for 1972- issued with: Bureau de recherches géologiques et minières." |
    Then I should find 1 match

