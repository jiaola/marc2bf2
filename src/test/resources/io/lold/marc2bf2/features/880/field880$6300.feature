Feature: 880$6300 - ALTERNATE GRAPHIC REPRESENTATION - PHYSICAL DESCRIPTION
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6300-01$3грампластинки$a1$fкоробка$g2 x 4 x 3 1/2 ft."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 300 creates an extent property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:extent ?y                     |
      | ?y a bf:Extent                      |
      | ?y rdfs:label "1 коробка 2 x 4 x 3 1/2 ft." |
    Then I should find 1 match
