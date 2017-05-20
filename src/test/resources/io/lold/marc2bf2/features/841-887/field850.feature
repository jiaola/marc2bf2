Feature: 850 - HOLDING INSTITUTION
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=850  \\$aAAP$aCU$aDLC$aUniversity of Upper Toothache"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field850Converter

  Scenario: 850 $a create Items of the Instance, with itemOf created
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:hasItem ?y                     |
      | ?y a bf:Item                         |
      | ?y bf:itemOf ?x                      |
    Then I should find 4 match

  Scenario: if $a string-length < 10, $a becomes URI of the heldBy/Agent of the Item
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:hasItem ?y                     |
      | ?y a bf:Item                         |
      | ?y bf:heldBy <http://id.loc.gov/vocabulary/organizations/DLC>  |
      | <http://id.loc.gov/vocabulary/organizations/DLC> a bf:Agent    |
    Then I should find 1 match

  Scenario: if $a string-length > 10,  $a becomes the rdfs:label property
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:hasItem ?y                     |
      | ?y a bf:Item                         |
      | ?y bf:heldBy ?z                      |
      | ?z rdfs:label "University of Upper Toothache" |
    Then I should find 1 match
