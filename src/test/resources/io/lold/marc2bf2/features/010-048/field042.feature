Feature: 042 - AUTHENTICATION CODE

  Scenario: 042 create descriptionAuthentication properties of the Work AdminMetadata
    Given a marc field "=042  \\$alc$ansdp"
    When converted by a field converter io.lold.marc2bf2.converters.Field042Converter
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:adminMetadata ?y               |
      | ?y a bf:AdminMetadata                |
      | ?y bf:descriptionAuthentication <http://id.loc.gov/vocabulary/marcauthen/nsdp>   |
      | <http://id.loc.gov/vocabulary/marcauthen/nsdp> a bf:DescriptionAuthentication    |
    Then I should find 1 match
