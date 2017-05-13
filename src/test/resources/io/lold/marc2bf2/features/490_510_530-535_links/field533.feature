Feature: 533 - REPRODUCTION NOTE
         535 - LOCATION OF ORIGINALS/DUPLICATES NOTE

  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=245  10$aOle Lukøie /$cH.C. Andersen ; illustrationer af Otto Dickmeiss."
    And a marc field "=533  \\$3Correspondence files$aMicrofilm.$m1962-1965.$bMiddleton, Conn.,$cWesleyan University Archives,$d1973$e35 mm. negative.$f(Current periodical series : publication no. 2313).$nIssues for 1853-1856 on reel with: Journal of the American Temperance Union and the New York prohibitionist, v. 21, no. 7 (July 1857)-v. 24 (1860).$5ICU"
    And a marc field "=535  2\$3German notebook$aYale University Library, Department of Manuscripts and Archives;$bBox 1603A Yale Station, New Haven, CT 06520;$cUSA;$d203-436-4564$gctu"
    When converted by a field converter io.lold.marc2bf2.converters.field490_510_530to535.Field533Converter

  Scenario: 533 creates a hasInstance/Instance property of the Work with a URI
    When I search with patterns:
      | ?x a bf:Work                                                       |
      | ?x bf:hasInstance <http://example.org/9999999999#Instance533-1>    |
      | <http://example.org/9999999999#Instance533-1> a bf:Instance        |
      | <http://example.org/9999999999#Instance533-1> bf:instanceOf ?x     |
      | ?y a bf:Title                                                      |
      | ?y rdfs:label "Ole Lukøie /"                                       |
    Then I should find 1 match

  Scenario: 533 creates a hasReproduction property of the Instance
            and an reproductionOf property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Instance                                                        |
      | ?x bf:hasReproduction <http://example.org/9999999999#Instance533-1>     |
      | <http://example.org/9999999999#Instance533-1> a bf:Instance             |
      | <http://example.org/9999999999#Instance533-1> bf:reproductionOf ?x      |
    Then I should find 1 match

  Scenario: 533 creates an reproductionOf property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                                       |
      | ?x bf:hasInstance <http://example.org/9999999999#Instance533-1>    |
      | <http://example.org/9999999999#Instance533-1> a bf:Instance        |
      | <http://example.org/9999999999#Instance533-1> bf:reproductionOf <http://example.org/9999999999#Instance> |
    Then I should find 1 match

  Scenario: $a creates a carrier property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                           |
      | ?x bf:hasInstance ?y                   |
      | ?y a bf:Instance                       |
      | ?y bf:carrier ?z                       |
      | ?z a bf:Carrier                        |
      | ?z rdfs:label "Microfilm"              |
    Then I should find 1 match

  Scenario: $b, $c, or $d create a provisionActivity property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:provisionActivity  ?z                 |
    Then I should find 1 match

  Scenario: $b creates a place property of the ProvisionActivity
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:provisionActivity  ?z                 |
      | ?z a bf:ProvisionActivity                   |
      | ?z bf:place ?p                              |
      | ?p a bf:Place                               |
      | ?p rdfs:label "Middleton, Conn"             |
    Then I should find 1 match

  Scenario: $c creates an agent property of the ProvisionActivity
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:provisionActivity  ?z                 |
      | ?z a bf:ProvisionActivity                   |
      | ?z bf:agent ?p                              |
      | ?p a bf:Agent                               |
      | ?p rdfs:label "Wesleyan University Archives"|
    Then I should find 1 match

  Scenario: $d creates a date property of the ProvisionActivity
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:provisionActivity  ?z                 |
      | ?z a bf:ProvisionActivity                   |
      | ?z bf:date "1973"                           |
    Then I should find 1 match

  Scenario: $e creates an extent property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:extent ?z                             |
      | ?z a bf:Extent                              |
      | ?z rdfs:label "35 mm. negative"             |
    Then I should find 1 match

  Scenario: $f creates a seriesStatement property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:seriesStatement "Current periodical series : publication no. 2313" |
    Then I should find 1 match

  Scenario: $m creates a bflc:appliesTo property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y bflc:appliesTo ?z                        |
      | ?z a bflc:AppliesTo                         |
      | ?z rdfs:label "1962-1965"                   |
    Then I should find 1 match

  Scenario: $n creates a note property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:note ?z                               |
      | ?z a bf:Note                                |
      | ?z rdfs:label "Issues for 1853-1856 on reel with: Journal of the American Temperance Union and the New York prohibitionist, v. 21, no. 7 (July 1857)-v. 24 (1860)." |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bflc:appliesTo ?z                        |
      | ?z a bflc:AppliesTo                         |
      | ?z rdfs:label "Correspondence files"        |
    Then I should find 1 match

  Scenario: $5 creates a bflc:applicableInstitution property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bflc:applicableInstitution ?z            |
      | ?z a bf:Agent                               |
      | ?z bf:code "ICU"                            |
    Then I should find 1 match

  Scenario: A 535 field with ind1=2 creates a hasItem/Item property of the related 533 Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:hasItem ?z                            |
      | ?z a bf:Item                                |
    Then I should find 1 match

  Scenario: A 535 field with ind1=2 creates a hasItem/Item property of the related 533 Instance
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
      | ?a rdfs:label "Yale University Library, Department of Manuscripts and Archives" |
    Then I should find 1 match

  Scenario: 535 $b/$c/$d create a place property of the Agent
    When I search with patterns:
      | ?x a bf:Item                                |
      | ?x bf:heldBy ?y                             |
      | ?y a bf:Agent                               |
      | ?y bf:place ?z                              |
      | ?z a bf:Place                               |
      | ?z rdfs:label "Box 1603A Yale Station, New Haven, CT 06520; USA; 203-436-4564" |
    Then I should find 1 match

  Scenario: 535 $3 creates a bflc:appliesTo property of the Item
    When I search with patterns:
      | ?x a bf:Item                                |
      | ?x bflc:appliesTo ?z                        |
      | ?z a bflc:AppliesTo                         |
      | ?z rdfs:label "German notebook"             |
    Then I should find 1 match
