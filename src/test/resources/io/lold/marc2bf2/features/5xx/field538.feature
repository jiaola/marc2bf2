Feature: 538 - SYSTEM DETAILS NOTE
  Background:
    Given a marc field "=538  \\$3v.1-49(1927-1975)$aMaster and use copy. Digital Master created according to Benchmark for Faithful Digital Reproductions of Monographs and Serials, Version 1. Digital Library Federation, December 2002.$uhttp://www.diglib.org/standards/bmarkfin.htm$5NIC"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field538Converter

  Scenario: 538 creates a systemRequirement/SystemRequirement property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:systemRequirement ?y             |
      | ?y a bf:SystemRequirement              |
    Then I should find 1 match

  Scenario: $a creates an rdfs:label property of the SystemRequirement
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:systemRequirement ?y             |
      | ?y a bf:SystemRequirement              |
      | ?y rdfs:label "Master and use copy. Digital Master created according to Benchmark for Faithful Digital Reproductions of Monographs and Serials, Version 1. Digital Library Federation, December 2002." |
    Then I should find 1 match

  Scenario: $u creates an rdfs:label property of the SystemRequirement
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:systemRequirement ?y             |
      | ?y a bf:SystemRequirement              |
      | ?y rdfs:label "http://www.diglib.org/standards/bmarkfin.htm"^^xsd:anyURI |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property of the SystemRequirement
    When I search with patterns:
      | ?x a bf:Instance                 |
      | ?x bf:systemRequirement ?y       |
      | ?y a bf:SystemRequirement        |
      | ?y bflc:appliesTo ?z             |
      | ?z a bflc:AppliesTo              |
      | ?z rdfs:label "v.1-49(1927-1975)"|
    Then I should find 1 match
