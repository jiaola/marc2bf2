Feature: 038 - RECORD CONTENT LICENSOR

  Scenario: 038 creates a bflc:metadataLicensor property of the Work AdminMetadata
    Given a marc field "=038  \\$aUk"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field038Converter
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:adminMetadata ?y               |
      | ?y a bf:AdminMetadata                |
      | ?y bflc:metadataLicensor ?z          |
      | ?z a bflc:MetadataLicensor           |
      | ?z rdfs:label "Uk"                   |
    Then I should find 1 match
