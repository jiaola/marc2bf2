Feature: 530 - ADDITIONAL PHYSICAL FORM AVAILABLE NOTE

  Background:
    Given a marc field "=530  \\$3Dispatches from U.S. consuls in Batavia, Java, Netherlands East Indies, 1818-1906$aAvailable in microfilm;$bNational Archives;$dM449;$cStanding order account required.$uhttp://www.bartleby.com/99/index.html"
    When converted by a field converter io.lold.marc2bf2.converters.Field530Converter

  Scenario: 530 creates a hasInstance/Instance property of the Work with a URI
    When I search with patterns:
      | ?x a bf:Work                                                       |
      | ?x bf:hasInstance <http://example.org/9999999999#Instance530-0>    |
      | <http://example.org/9999999999#Instance530-0> a bf:Instance        |
      | <http://example.org/9999999999#Instance530-0> bf:instanceOf ?x     |
    Then I should find 1 match

  Scenario: 530 creates a otherPhysicalFormat property of the Instance
            and an otherPhysicalFormat property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Instance                                                        |
      | ?x bf:otherPhysicalFormat <http://example.org/9999999999#Instance530-0> |
      | <http://example.org/9999999999#Instance530-0> a bf:Instance             |
      | <http://example.org/9999999999#Instance530-0> bf:otherPhysicalFormat ?x |
    Then I should find 1 match

  Scenario: $a creates a note property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                           |
      | ?x bf:hasInstance ?y                   |
      | ?y a bf:Instance                       |
      | ?y bf:note ?z                          |
      | ?z a bf:Note                           |
      | ?z rdfs:label "Available in microfilm" |
    Then I should find 1 match

  Scenario: $b creates an acquisitionSource property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:acquisitionSource "National Archives" |
    Then I should find 1 match

  Scenario: $c creates an acquisitionTerms property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                             |
      | ?x bf:hasInstance ?y                                     |
      | ?y a bf:Instance                                         |
      | ?y bf:acquisitionTerms "Standing order account required" |
    Then I should find 1 match

  Scenario: $d creates an identifiedBy/StockNumber property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:identifiedBy ?z                       |
      | ?z a bf:StockNumber                         |
      | ?z rdf:value "M449"                         |
    Then I should find 1 match

  Scenario: $u creates a hasItem property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bf:hasItem ?z                            |
      | ?z a bf:Item                                |
      | ?z bf:electronicLocator <http://www.bartleby.com/99/index.html> |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property of the hasInstance/Instance
    When I search with patterns:
      | ?x a bf:Work                                |
      | ?x bf:hasInstance ?y                        |
      | ?y a bf:Instance                            |
      | ?y bflc:appliesTo ?z                        |
      | ?z a bflc:AppliesTo                         |
      | ?z rdfs:label "Dispatches from U.S. consuls in Batavia, Java, Netherlands East Indies, 1818-1906" |
    Then I should find 1 match

