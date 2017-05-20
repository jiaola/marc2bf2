Feature: 026 - FINGERPRINT IDENTIFIER
  Background: 
    Given a marc field "=026  \\$adete nkck$bvess lodo 3$cAnno Domini MDCXXXVI$d3$2fei$5UkCU"
    And a marc field "=026  \\$edete nkck vess lodo 3 Anno Domini MDCXXXVI 3$2fei$5UkCU"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field026Converter

  Scenario: $a $b $c $d create an identifiedBy/Fingerprint property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:Fingerprint                  |
      | ?y rdf:value "dete nkck vess lodo 3 Anno Domini MDCXXXVI 3"          |
    Then I should find 2 match

  Scenario: $e creates an identifiedBy/Fingerprint property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:Fingerprint                  |
      | ?y rdf:value "dete nkck vess lodo 3 Anno Domini MDCXXXVI 3" |
    Then I should find 2 match

  Scenario: $2 creates a source property of the Fingerprint
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:Fingerprint                  |
      | ?y bf:source ?z                      |
      | ?z a bf:Source                       |
      | ?z rdfs:label "fei"                  |
    Then I should find 2 match

  Scenario: $5 creates a bflc:applicableInstitution property of the Fingerprint
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:Fingerprint                  |
      | ?y bflc:applicableInstitution ?z     |
      | ?z a bf:Agent                        |
      | ?z bf:code "UkCU"                    |
    Then I should find 2 match
