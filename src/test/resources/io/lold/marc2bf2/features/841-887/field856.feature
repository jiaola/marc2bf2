Feature: 856 - ELECTRONIC LOCATION AND ACCESS
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=856  41$uhttp://www.jstor.org/journals/0277903x.html"
    And a marc field "=856  40$uhttp://www.ref.oclc.org:2000$zAddress for accessing the journal using authorization number and password through OCLC FirstSearch Electronic Collections Online. Subscription to online journal required for access to abstracts and full text"
    And a marc field "=856  42$3Finding aid$uhttp://www.loc.gov/ammem/ead/jackson.sgm"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field856Converter

  Scenario: If Instance is Electronic, 856 creates an Item of the Instance
    When I search with patterns:
      | <http://example.org/9999999999#Instance> a bf:Instance                     |
      | <http://example.org/9999999999#Instance> bf:hasItem ?y                     |
      | ?y a bf:Item                         |
    Then I should find 1 match

  Scenario: If Instance is not Electronic and ind2!=2, 856 creates a new Instance of the Work" test="count(//bf:Instance) = 2"/>
    When I search with patterns:
      | ?x a bf:Instance                     |
    Then I should find 2 matches

  Scenario: If Instance is not Electronic and ind2=2, in which case it creates a supplementaryContent property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:supplementaryContent ?y        |
    Then I should find 1 match

  Scenario: $u with no $zy or $3 creates an electronicLocator property of the Item with rdf:resource = '$u'
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:hasInstance ?y                 |
      | ?y a bf:Instance                     |
      | ?y bf:hasItem ?z                     |
      | ?z a bf:Item                         |
      | ?z bf:electronicLocator <http://www.jstor.org/journals/0277903x.html> |
    Then I should find 1 match

  Scenario: ...otherwise it creates a blank node with a bflc:locator property from $u
    When I search with patterns:
      | ?y a bf:Instance                     |
      | ?y bf:hasItem ?z                     |
      | ?z a bf:Item                         |
      | ?z bf:electronicLocator ?e           |
      | ?e bflc:locator <http://www.ref.oclc.org:2000> |
    Then I should find 1 match

  Scenario: ...with notes generated from $z/y/3
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:supplementaryContent ?y        |
      | ?y bf:note ?z                        |
      | ?z a bf:Note                         |
      | ?z rdfs:label "Finding aid"          |
    Then I should find 1 match
