Feature: 020 - INTERNATIONAL STANDARD BOOK NUMBER
  Background: 
    Given a marc field "=020  \\$a0877790086 :$c10.00"
    And a marc field "=020  \\$a0877790019$qblack leather$z0877780116 :$c14.00"
    When converted by a field converter io.lold.marc2bf2.converters.field010to048.Field020Converter

  Scenario: 020 creates identifiedBy/Isbn properties of the Instance
    When I search with patterns:
      | ?x a bf:Instance                            |
      | ?x bf:identifiedBy ?y                       |
      | ?y a bf:Isbn                                |
    Then I should find 3 match

  Scenario: $a is the rdf:value of the Isbn
    When I search with patterns:
      | ?x a bf:Instance                            |
      | ?x bf:identifiedBy ?y                       |
      | ?y a bf:Isbn                                |
      | ?y rdf:value "0877790086"                   |
    Then I should find 1 match

  Scenario: $c creates an acqusitionTerms property of the Isbn
    When I search with patterns:
      | ?x a bf:Instance                            |
      | ?x bf:identifiedBy ?y                       |
      | ?y a bf:Isbn                                |
      | ?y bf:acquisitionTerms "10.00"              |
    Then I should find 1 match

  Scenario: $q creates a qualifier property of the Isbn
    When I search with patterns:
      | ?x a bf:Instance                            |
      | ?x bf:identifiedBy ?y                       |
      | ?y a bf:Isbn                                |
      | ?y bf:qualifier "black leather"             |
    Then I should find 2 match

  Scenario: $z creates a status/Status property of the Isbn with rdfs:label 'invalid'
    When I search with patterns:
      | ?x a bf:Instance             |
      | ?x bf:identifiedBy ?y        |
      | ?y a bf:Isbn                 |
      | ?y bf:status ?z              |
      | ?z a bf:Status               |
      | ?z rdfs:label "invalid"      |
    Then I should find 1 match
