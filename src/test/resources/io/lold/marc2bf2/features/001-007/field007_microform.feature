Feature: 007 - MICROFORM - PHYSICAL DESCRIPTION FIXED FIELD
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=007  he bmb024baca"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field007Converter

  Scenario: pos 0 = 'h' creates a media property of the Instance
            and sets the rdfs:label property of the Media
    When I search with patterns:
      | ?x a bf:Instance                                                   |
      | ?x bf:media <http://id.loc.gov/vocabulary/mediaTypes/h>            |
      | <http://id.loc.gov/vocabulary/mediaTypes/h> a bf:Media             |
      | <http://id.loc.gov/vocabulary/mediaTypes/h> rdfs:label "microform" |
    Then I should find 1 match

  Scenario: pos 1 sets the carrier property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                                         |
      | ?x bf:carrier <http://id.loc.gov/vocabulary/carriers/he> |
      | <http://id.loc.gov/vocabulary/carriers/he> a bf:Carrier  |
    Then I should find 1 match

  Scenario: pos 3 creates a polarity property of the Instance
    When I search with patterns:
      | ?x a bf:Instance            |
      | ?x bf:polarity ?y           |
      | ?y a bf:Polarity            |
      | ?y rdfs:label "negative"    |
    Then I should find 1 match

  Scenario: pos 4 sets the dimensions property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                         |
      | ?x bf:dimensions "4x6 in. or 11x15 cm."  |
    Then I should find 1 match

  Scenario: pos 5-8 create a reductionRatio property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                         |
      | ?x bf:reductionRatio ?y                  |
      | ?y a bf:ReductionRatio                   |
      | ?y rdfs:label "024"                      |
      | ?y bf:note ?z                            |
      | ?z a bf:Note                             |
      | ?z rdfs:label "normal reduction range"   |
    Then I should find 1 match

  Scenario: pos 9 creates a colorContent property of the Work
    When I search with patterns:
      | ?x a bf:Work                    |
      | ?x bf:colorContent ?y           |
      | ?y a bf:ColorContent            |
      | ?y rdfs:label "black and white" |
    Then I should find 1 match

  Scenario: pos 10 creates an emulsion property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                 |
      | ?x bf:emulsion ?y                |
      | ?y a bf:Emulsion                 |
      | ?y rdfs:label "silver halide"    |
    Then I should find 1 match

  Scenario: pos 11 creates a generation property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                 |
      | ?x bf:generation ?y              |
      | ?y a bf:Generation               |
      | ?y rdfs:label "service copy"     |
    Then I should find 1 match

  Scenario: pos 12 creates a baseMaterial property of the Instance
    When I search with patterns:
      | ?x a bf:Instance             |
      | ?x bf:baseMaterial ?y        |
      | ?y a bf:BaseMaterial         |
      | ?y rdfs:label "safety base"  |
    Then I should find 1 match


