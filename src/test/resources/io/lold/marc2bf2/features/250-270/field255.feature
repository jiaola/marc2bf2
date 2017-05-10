Feature: 255 - CARTOGRAPHIC MATHEMATICAL DATA
  
  Background:
    Given a marc field "=255  \\$aScale [ca. 1:500,000] ;$bConic proj.$c(E 72°--E 148°/N 13°--N 18°).$d(RA 0 hr. to 24 hr./Decl. +90° to -90° ;$eeq. 1980).$f-113.628304 -113.498001 47.009300 46.087225$g-113.628304 -113.498001 47.009300 46.087225"
    When converted by a field converter io.lold.marc2bf2.converters.Field255Converter

  Scenario: 255 creates a cartographicAttributes/Cartographic property of the Work
    When I search with patterns:
      | ?x a bf:Work                    |
      | ?x bf:cartographicAttributes ?y |
      | ?y a bf:Cartographic            |
    Then I should find 1 match

  Scenario: $a creates a scale/Scale property of the Work
    When I search with patterns:
      | ?x a bf:Work                          |
      | ?x bf:scale ?y                        |
      | ?y a bf:Scale                         |
      | ?y rdfs:label "Scale [ca. 1:500,000]" |
    Then I should find 1 match

  Scenario: $b creates a projection/Projection property of the Cartographic
    When I search with patterns:
      | ?x a bf:Work                    |
      | ?x bf:cartographicAttributes ?y |
      | ?y a bf:Cartographic            |
      | ?y bf:projection ?z             |
      | ?z a bf:Projection              |
      | ?z rdfs:label "Conic proj."     |
    Then I should find 1 match

  Scenario: $c creates a coordinates property of the Cartographic
    When I search with patterns:
      | ?x a bf:Work                                    |
      | ?x bf:cartographicAttributes ?y                 |
      | ?y a bf:Cartographic                            |
      | ?y bf:coordinates "E 72°--E 148°/N 13°--N 18°"  |
    Then I should find 1 match

  Scenario: $d creates an ascensionAndDeclination property of the Cartographic
    When I search with patterns:
      | ?x a bf:Work                                    |
      | ?x bf:cartographicAttributes ?y                 |
      | ?y a bf:Cartographic                            |
      | ?y bf:ascensionAndDeclination "RA 0 hr. to 24 hr./Decl. +90° to -90°" |
    Then I should find 1 match

  Scenario: $e creates an equinox property of the Cartographic
    When I search with patterns:
      | ?x a bf:Work                                    |
      | ?x bf:cartographicAttributes ?y                 |
      | ?y a bf:Cartographic                            |
      | ?y bf:equinox "eq. 1980"                        |
    Then I should find 1 match

  Scenario: $f creates an outerGRing property of the Cartographic
    When I search with patterns:
      | ?x a bf:Work                                    |
      | ?x bf:cartographicAttributes ?y                 |
      | ?y a bf:Cartographic                            |
      | ?y bf:outerGRing "-113.628304 -113.498001 47.009300 46.087225" |
    Then I should find 1 match

  Scenario: $g creates an exclusionGRing property of the Cartographic
    When I search with patterns:
      | ?x a bf:Work                                    |
      | ?x bf:cartographicAttributes ?y                 |
      | ?y a bf:Cartographic                            |
      | ?y bf:exclusionGRing "-113.628304 -113.498001 47.009300 46.087225" |
    Then I should find 1 match

