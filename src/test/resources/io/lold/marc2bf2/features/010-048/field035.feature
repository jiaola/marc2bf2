Feature: 035 - SYSTEM CONTROL NUMBER
  Background: 
    Given a marc field "=035  \\$a(OCoLC)814782$z(OCoLC)7374506"
    When converted by a field converter io.lold.marc2bf2.converters.field010to048.Field035Converter

  Scenario: $a creates an identifiedBy/Local property of the Instance
            and value in parens creates a source property of the Local
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:Local                        |
      | ?y rdf:value "814782"                |
      | ?y bf:source ?z                      |
      | ?z a bf:Source                       |
      | ?z rdfs:label "OCoLC"                |
    Then I should find 1 match

  Scenario: $z creates a status/Status property of the Local with rdfs:label 'invalid'
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:Local                        |
      | ?y bf:status ?z                      |
      | ?z a bf:Status                       |
      | ?zz rdfs:label "invalid"             |
    Then I should find 1 match

