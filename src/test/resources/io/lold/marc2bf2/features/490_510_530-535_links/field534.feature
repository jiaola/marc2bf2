Feature: 534 - ORIGINAL VERSION NOTE
         535 - LOCATION OF ORIGINALS/DUPLICATES NOTE

  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=534  \\$3vol. 2$pReprint. Originally published:$cBerlin :Eulenspiegel, c1978,$b1st ed.$aFrederick, John.$nunder title:$tLuck.$e1 art original : oil, col. ; 79 x 64 cm.$f(International series of monographs on electromagnetic waves ; v. 4).$kAmerican journal of theology & philosophy,$x0228-913X.$z0385061781 (v. 1)$mat scale 1:50,000."
    And a marc field "=535  1\$3Coal reports$aAmerican Mining Congress;$b1920 N St., NW, Washington, D.C. 20036;$d202-861-2800"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field534Converter

  Scenario: 534 creates a hasInstance/Instance property of the Work
            with a URI
            and an instanceOf property
    When I search with patterns:
      | ?x a bf:Work                                                       |
      | ?x bf:hasInstance <http://example.org/9999999999#Instance534-0>    |
      | <http://example.org/9999999999#Instance534-0> a bf:Instance        |
      | <http://example.org/9999999999#Instance534-0> bf:instanceOf ?x     |
    Then I should find 1 match

  Scenario: 534 creates an originalVersion property of the Instance
            and an originalVersionOf property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Instance                                                        |
      | ?x bf:originalVersion <http://example.org/9999999999#Instance534-0>     |
      | <http://example.org/9999999999#Instance534-0> a bf:Instance             |
      | <http://example.org/9999999999#Instance534-0> bf:originalVersionOf ?x   |
    Then I should find 1 match

  Scenario: $a creates a contribution property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:contribution  ?z                      |
      | ?z a bf:Contribution                        |
      | ?z bf:agent ?p                              |
      | ?p a bf:Agent                               |
      | ?p rdfs:label "Frederick, John"             |
    Then I should find 1 match

  Scenario: $b creates an editionStatement property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:editionStatement "1st ed"             |
    Then I should find 1 match

  Scenario: $c creates a provisionActivityStatement of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:provisionActivityStatement "Berlin :Eulenspiegel, c1978" |
    Then I should find 1 match

  Scenario: $e creates an extent property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:extent ?z                             |
      | ?z a bf:Extent                              |
      | ?z rdfs:label "1 art original : oil, col. ; 79 x 64 cm" |
    Then I should find 1 match

  Scenario: $f creates a seriesStatement property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:seriesStatement "International series of monographs on electromagnetic waves ; v. 4" |
    Then I should find 1 match

  Scenario: $k creates a title/KeyTitle property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:title ?z                              |
      | ?z a bf:KeyTitle                            |
      | ?z rdfs:label "American journal of theology & philosophy" |
    Then I should find 1 match

  Scenario: $m creates a note property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:note ?z                               |
      | ?z a bf:Note                                |
      | ?z rdfs:label "at scale 1:50,000"           |
    Then I should find 1 match

  Scenario: $n creates a note property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:note ?z                               |
      | ?z a bf:Note                                |
      | ?z rdfs:label "under title"                 |
    Then I should find 1 match

  Scenario: $p creates a bflc:appliesTo property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y bflc:appliesTo ?z                        |
      | ?z a bflc:AppliesTo                         |
      | ?z rdfs:label "Reprint. Originally published" |
    Then I should find 1 match

  Scenario: $t creates a title/InstanceTitle property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:title "Luck."                         |
    Then I should find 1 match

  Scenario: $x creates an identifiedBy/Issn property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:identifiedBy ?z                       |
      | ?z a bf:Issn                                |
      | ?z rdf:value "0228-913X"                    |
    Then I should find 1 match

  Scenario: $z creates an identifiedBy/Isbn property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:identifiedBy ?z                       |
      | ?z a bf:Isbn                                |
      | ?z rdf:value "0385061781 (v. 1)"            |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y bflc:appliesTo ?z                        |
      | ?z a bflc:AppliesTo                         |
      | ?z rdfs:label "vol. 2"                      |
    Then I should find 1 match

  Scenario: A 535 field with ind1=1 creates a hasItem/Item property of the related 534 Instance
            with a heldBy/Agent property
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:hasItem ?z                            |
      | ?z a bf:Item                                |
      | ?z bf:heldBy ?a                             |
      | ?a a bf:Agent                               |
    Then I should find 1 match

  Scenario: 535 $a becomes the rdfs:label of the Agent
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:hasItem ?z                            |
      | ?z a bf:Item                                |
      | ?z bf:heldBy ?a                             |
      | ?a a bf:Agent                               |
      | ?a rdfs:label "American Mining Congress"    |
    Then I should find 1 match

  Scenario: 535 $b/$c/$d create a place property of the Agent
    When I search with patterns:
      | ?x a bf:Item                                |
      | ?x bf:heldBy ?y                             |
      | ?y a bf:Agent                               |
      | ?y bf:place ?z                              |
      | ?z a bf:Place                               |
      | ?z rdfs:label "1920 N St., NW, Washington, D.C. 20036; 202-861-2800" |
    Then I should find 1 match

  Scenario: 535 $3 creates a bflc:appliesTo property of the Item
    When I search with patterns:
      | ?x a bf:Item                                |
      | ?x bflc:appliesTo ?z                        |
      | ?z a bflc:AppliesTo                         |
      | ?z rdfs:label "Coal reports"                |
    Then I should find 1 match



