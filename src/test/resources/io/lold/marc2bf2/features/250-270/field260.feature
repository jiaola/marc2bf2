Feature: 260 - PUBLICATION, DISTRIBUTION, ETC. (IMPRINT)
  
  Background:
    Given a marc field "=260  3\$31998-$aLondon ;$aUpton Snodsbury :$bArts Council of Great Britain,$c<1981- >$d12345$e(Twickenham :$fCTD Printers,$g1974)"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field260Converter

  Scenario: 260 creates a provisionActivity/Publication property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:ProvisionActivity       |
      | ?y a bf:Publication             |
    Then I should find 1 match

  Scenario: 260 creates a provisionActivityStatement of the Instance
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivityStatement "London ; Upton Snodsbury : Arts Council of Great Britain, <1981- >" |
    Then I should find 1 match

  Scenario: if $e, $f, $g, 260 also creates a provisionActivity/Manufacture property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:ProvisionActivity       |
      | ?y a bf:Manufacture             |
    Then I should find 1 match

  Scenario: ind1 = 3 creates a status property of the Publication
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:ProvisionActivity       |
      | ?y bf:status ?z                 |
      | ?z a bf:Status                  |
      | ?z rdfs:label "current"         |
    Then I should find 1 match

  Scenario: $a creates a place property of the Publication
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:ProvisionActivity       |
      | ?y bf:place ?z                  |
      | ?z a bf:Place                   |
      | ?z rdfs:label "Upton Snodsbury" |
    Then I should find 1 match

  Scenario: $b creates an agent property of the Publication
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:ProvisionActivity       |
      | ?y bf:agent ?z                  |
      | ?z a bf:Agent                   |
      | ?z rdfs:label "Arts Council of Great Britain" |
    Then I should find 1 match

  Scenario: $c creates a date property of the publication
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:ProvisionActivity       |
      | ?y bf:date "<1981- >"           |
    Then I should find 1 match

  Scenario: $d creates an identifiedBy/PublisherNumber property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:identifiedBy ?y           |
      | ?y a bf:PublisherNumber         |
      | ?y rdf:value "12345"            |
    Then I should find 1 match

  Scenario: $e creates a place property of the Manufacture
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:ProvisionActivity       |
      | ?y a bf:Manufacture             |
      | ?y bf:place ?z                  |
      | ?z a bf:Place                   |
      | ?z rdfs:label "Twickenham"      |
    Then I should find 1 match

  Scenario: $f creates an agent property of the Manufacture
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:ProvisionActivity       |
      | ?y a bf:Manufacture             |
      | ?y bf:agent ?z                  |
      | ?z a bf:Agent                   |
      | ?z rdfs:label "CTD Printers"    |
    Then I should find 1 match

  Scenario: $g creates a date property of the Manufacture
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:Manufacture             |
      | ?y a bf:ProvisionActivity       |
      | ?y bf:date "1974"               |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property of the ProvisionActivity
    When I search with patterns:
      | ?x a bf:Instance                |
      | ?x bf:provisionActivity ?y      |
      | ?y a bf:ProvisionActivity       |
      | ?y bflc:appliesTo ?z            |
      | ?z a bflc:AppliesTo             |
      | ?z rdfs:label "1998-"           |
    Then I should find 2 matches
