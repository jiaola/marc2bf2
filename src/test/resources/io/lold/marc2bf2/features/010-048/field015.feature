Feature: 015 - NATIONAL BIBLIOGRAPHY NUMBER
  Background: 
    Given a marc field "=015  \\$a06,A29,1122$qpbk$z05,N51,1204$2dnb"
    When converted by a field converter io.lold.marc2bf2.converters.field010to048.Field015Converter

  Scenario: $a creates an identifiedBy/Nbn property of the Instance
    When I search with patterns:
      | ?x a bf:Instance             |
      | ?x bf:identifiedBy ?y        |
      | ?y a bf:Nbn                  |
      | ?y rdf:value "06,A29,1122"   |
    Then I should find 1 match

  Scenario: $b creates a qualifier property of the Nbn
    When I search with patterns:
      | ?x a bf:Instance             |
      | ?x bf:identifiedBy ?y        |
      | ?y a bf:Nbn                  |
      | ?y bf:qualifier "pbk"        |
    Then I should find 2 match

  Scenario: $z creates an status/Status property of the Nbn with rdfs:label 'invalid'
    When I search with patterns:
      | ?x a bf:Instance             |
      | ?x bf:identifiedBy ?y        |
      | ?y a bf:Nbn                  |
      | ?y bf:status ?z              |
      | ?z a bf:Status               |
      | ?z rdfs:label "invalid"      |
    Then I should find 1 match

  Scenario: $2 creates a source property of the Nbn" test="//bf:Instance[1]/bf:identifiedBy[4]/bf:Nbn/bf:source/bf:Source/rdfs:label = 'dnb'"/>
    When I search with patterns:
      | ?x a bf:Instance             |
      | ?x bf:identifiedBy ?y        |
      | ?y a bf:Nbn                  |
      | ?y bf:source ?z              |
      | ?z a bf:Source               |
      | ?z rdfs:label "dnb"          |
    Then I should find 2 match
