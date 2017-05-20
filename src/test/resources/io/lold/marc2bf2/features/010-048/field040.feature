Feature: 040 - CATALOGING SOURCE

  Background:
    Given a marc field "=040  \\$aDNA$bfre$cCtY$dCtY$eNARS Staff Bulletin No. 16$eappm"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field040Converter

  Scenario: 040 creates a source property of the Work AdminMetadata
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:adminMetadata ?y               |
      | ?y a bf:AdminMetadata                |
      | ?y bf:source ?z                      |
      | ?z a bf:Source                       |
      | ?z rdfs:label "DNA"                  |
    Then I should find 1 match

  Scenario: $b creates a descriptionLanguage property of the AdminMetadata
    When I search with patterns:
      | ?x a bf:AdminMetadata                                                  |
      | ?x bf:descriptionLanguage <http://id.loc.gov/vocabulary/languages/fre> |
      | <http://id.loc.gov/vocabulary/languages/fre> a bf:Language             |
    Then I should find 1 match

  Scenario: $c creates another source property
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:adminMetadata ?y               |
      | ?y a bf:AdminMetadata                |
      | ?y bf:source ?z                      |
      | ?z a bf:Source                       |
      | ?z rdfs:label "CtY"                  |
    Then I should find 1 match

  Scenario: $d creates a descriptionModifier property of the AdminMetadata
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:adminMetadata ?y               |
      | ?y a bf:AdminMetadata                |
      | ?y bf:descriptionModifier ?z         |
      | ?z a bf:Agent                        |
      | ?z rdfs:label "CtY"                  |
    Then I should find 1 match

  Scenario: $e creates a descriptionConventions property of the AdminMetadata
    When I search with patterns:
      | ?x a bf:Work                               |
      | ?x bf:adminMetadata ?y                     |
      | ?y a bf:AdminMetadata                      |
      | ?y bf:descriptionConventions ?z            |
      | ?z a bf:DescriptionConventions             |
      | ?z rdfs:label "NARS Staff Bulletin No. 16" |
    Then I should find 1 match
