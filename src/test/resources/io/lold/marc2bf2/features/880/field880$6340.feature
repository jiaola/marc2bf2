Feature: 880$6340 - ALTERNATE GRAPHIC REPRESENTATION - PHYSICAL MEDIUM
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6340-01$3Автопортрет$aрисовая бумага$b7" x 9"$cрисовая бумага"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 340 creates a bunch of Instance properties
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:baseMaterial ?y               |
      | ?y a bf:BaseMaterial                |
      | ?y rdfs:label "рисовая бумага"      |
    Then I should find 1 match

