Feature: 016 - NATIONAL BIBLIOGRAPHIC AGENCY CONTROL NUMBER
  Background: 
    Given a marc field "=016  \\$a 730032015  rev$z 89000298 "
    And a marc field "=016  7\$aPTBN000004618$2PoLiBN"
    When converted by a field converter io.lold.marc2bf2.converters.field010to048.Field016Converter

  Scenario: ind1=' ' creates a LAC source property of the Local
    When I search with patterns:
      | ?x a bf:AdminMetadata                       |
      | ?x bf:identifiedBy ?y                       |
      | ?y a bf:Local                               |
      | ?y bf:source ?z                             |
      | ?z a bf:Source                              |
      | ?z rdfs:label "Library and Archives Canada" |
    Then I should find 2 match

  Scenario: $a creates an identifiedBy/Local property of the AdminMetadata
    When I search with patterns:
      | ?x a bf:AdminMetadata                       |
      | ?x bf:identifiedBy ?y                       |
      | ?y a bf:Local                               |
      | ?y rdf:value " 730032015  rev"              |
    Then I should find 1 match

  Scenario: $z creates an status/Status property of the Local with rdfs:label 'invalid'
    When I search with patterns:
      | ?x a bf:AdminMetadata        |
      | ?x bf:identifiedBy ?y        |
      | ?y a bf:Local                |
      | ?y bf:status ?z              |
      | ?z a bf:Status               |
      | ?z rdfs:label "invalid"      |
    Then I should find 1 match

  Scenario: $2 creates a source property of the Local
    When I search with patterns:
      | ?x a bf:AdminMetadata        |
      | ?x bf:identifiedBy ?y        |
      | ?y a bf:Local                |
      | ?y bf:source ?z              |
      | ?z a bf:Source               |
      | ?z rdfs:label "PoLiBN"       |
    Then I should find 1 match
