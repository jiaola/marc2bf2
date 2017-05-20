Feature: 545 - BIOGRAPHICAL OR HISTORICAL DATA
  Background:
    Given a marc field "=545  0\$aAuthor and reformer.$bBorn Harriet Elizabeth Beecher.$uhttps://www.harrietbeecherstowecenter.org/hbs/"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field545Converter
    
  Scenario: 545 creates a note/Note property of the Instance with noteType from ind1
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y bf:noteType "biographical data"     |
    Then I should find 1 match

  Scenario: $ab create an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y rdfs:label "Author and reformer. Born Harriet Elizabeth Beecher." |
    Then I should find 1 match

  Scenario: $u creates an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance                       |
      | ?x bf:note ?y                          |
      | ?y a bf:Note                           |
      | ?y rdfs:label "https://www.harrietbeecherstowecenter.org/hbs/"^^xsd:anyURI |
    Then I should find 1 match
