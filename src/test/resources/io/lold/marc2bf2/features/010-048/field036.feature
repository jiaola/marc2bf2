Feature: 036 - ORIGINAL STUDY NUMBER FOR COMPUTER DATA FILES
  Background: 
    Given a marc field "=036  \\$aCNRS 84115$bCentre national de la recherche scientifique."
    When converted by a field converter io.lold.marc2bf2.converters.field010to048.Field036Converter

  Scenario: $a creates an identifiedBy/StudyNumber property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:StudyNumber                  |
      | ?y rdf:value "CNRS 84115"            |
    Then I should find 1 match

  Scenario: $b creates a source property of the StudyNumber
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:identifiedBy ?y                |
      | ?y a bf:StudyNumber                  |
      | ?y bf:source ?z                      |
      | ?z a bf:Source                       |
      | ?z rdfs:label "Centre national de la recherche scientifique" |
    Then I should find 1 match

