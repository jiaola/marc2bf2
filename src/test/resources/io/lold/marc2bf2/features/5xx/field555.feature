Feature: 555 - CUMULATIVE INDEX/FINDING AIDS NOTE
  Background:
    Given a marc field "=555  0\$3Claims settled under Treaty of Washington, May 8, 1871$aPreliminary inventory prepared in 1962;$bAvailable in NARS central search room.$uhttp://hdl.loc.gov/loc.mss/eadmss.ms996001"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field555Converter

  Scenario: 555 creates a note/Note property of the Instance with noteType from ind1
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x bf:note ?y                  |
      | ?y a bf:Note                   |
      | ?y bf:noteType "finding aid"   |
    Then I should find 1 match

  Scenario: $abcd create an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x bf:note ?y                  |
      | ?y a bf:Note                   |
      | ?y rdfs:label "Preliminary inventory prepared in 1962; Available in NARS central search room." |
    Then I should find 1 match

  Scenario: $u creates an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x bf:note ?y                  |
      | ?y a bf:Note                   |
      | ?y rdfs:label "http://hdl.loc.gov/loc.mss/eadmss.ms996001"^^xsd:anyURI |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property of the Note
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x bf:note ?y                  |
      | ?y a bf:Note                   |
      | ?y bflc:appliesTo ?z           |
      | ?z a bflc:AppliesTo            |
      | ?z rdfs:label "Claims settled under Treaty of Washington, May 8, 1871" |
    Then I should find 1 match



