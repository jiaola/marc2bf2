Feature: 044 - COUNTRY OF PUBLISHING/PRODUCING ENTITY CODE

  Background:
    Given a marc field "=044  \\$aat$bxna$2ausmarc$cau"
    When converted by a field converter io.lold.marc2bf2.converters.Field044Converter

  Scenario: $a creates a place property of the Instance
    When I search with patterns:
      | ?x a bf:Instance    |
      | ?x bf:place <http://id.loc.gov/vocabulary/countries/at> |
      | <http://id.loc.gov/vocabulary/countries/at> a bf:Place  |
    Then I should find 1 match

  Scenario: $b creates a place property of the Instance
            with the source of the Place from $2
    When I search with patterns:
      | ?x a bf:Instance             |
      | ?x bf:place ?y               |
      | ?y a bf:Place                |
      | ?y bf:code "xna"             |
      | ?y bf:source ?z              |
      | ?z a bf:Source               |
      | ?z rdfs:label "ausmarc"      |
    Then I should find 1 match

  Scenario: $c creates a place property of the Instance
            with source labelled 'ISO 3166'
    When I search with patterns:
      | ?x a bf:Instance             |
      | ?x bf:place ?y               |
      | ?y a bf:Place                |
      | ?y bf:code "au"              |
      | ?y bf:source ?z              |
      | ?z a bf:Source               |
      | ?z rdfs:label "ISO 3166"     |
    Then I should find 1 match
