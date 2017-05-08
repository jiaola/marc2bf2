Feature: 032 - POSTAL REGISTRATION NUMBER
  Background: 
    Given a marc field "=032  \\$a686310$bUSPS"
    When converted by a field converter io.lold.marc2bf2.converters.Field032Converter

  Scenario: $a creates an identifiedBy/PostalRegistration property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:PostalRegistration           |
      | ?y rdf:value "686310"                |
    Then I should find 1 match

  Scenario: $b creates a source property of the PostalRegistration
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:PostalRegistration           |
      | ?y bf:source ?z                      |
      | ?z a bf:Source                       |
      | ?z rdfs:label "USPS"                 |
    Then I should find 1 match
