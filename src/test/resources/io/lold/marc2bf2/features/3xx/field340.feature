Feature: 340 - PHYSICAL MEDIUM
  Background:
    Given a marc field "=340  \\$3self-portrait$arice paper$b7" x 9"$ccolored inks$enone$hbetween entry for April 7 and April 19, 1843."
    And a marc field "=340  \\$3case files$aaperture cards$b9 x 19 cm.$dmicrofilm$f48x$iIbord Model 74 tape reader."
    And a marc field "=340  \\$joriginal$kdouble-sided$mfolio$ngiant print (36 point)$opositive$2rda"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field340Converter

  Scenario: $a creates a baseMaterial property of the Instance
    When I search with patterns:
      | ?x a bf:Instance           |
      | ?x bf:baseMaterial ?y      |
      | ?y a bf:BaseMaterial       |
      | ?y rdfs:label "rice paper" |
    Then I should find 1 match

  Scenario: $b creates a dimensions property of the Instance
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x bf:dimensions "7\" x 9\""   |
    Then I should find 1 match

  Scenario: $c creates an appliedMaterial property of the Instance
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x bf:appliedMaterial ?y       |
      | ?y a bf:AppliedMaterial        |
      | ?y rdfs:label "colored inks"   |
    Then I should find 1 match

  Scenario: $d creates a productionMethod property of the Instance
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x bf:productionMethod ?y      |
      | ?y a bf:ProductionMethod       |
      | ?y rdfs:label "microfilm"      |
    Then I should find 1 match

  Scenario: $e creates a mount property of the Instance
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x bf:mount ?y                 |
      | ?y a bf:Mount                  |
      | ?y rdfs:label "none"           |
    Then I should find matches

  Scenario: $f creates a reductionRatio property of the Instance
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x bf:reductionRatio ?y        |
      | ?y a bf:ReductionRatio         |
      | ?y rdfs:label "48x"            |
    Then I should find 1 match


  Scenario: $i creates a systemRequirement/SystemRequirement property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                                       |
      | ?x bf:systemRequirement ?y                             |
      | ?y a bf:SystemRequirement                              |
      | ?y rdfs:label "Ibord Model 74 tape reader."            |
    Then I should find 1 match

  Scenario: $j creates a generation property of the Instance
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x bf:generation ?y            |
      | ?y a bf:Generation             |
      | ?y rdfs:label "original"       |
    Then I should find 1 match

  Scenario: $k creates a layout property of the Instance
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x bf:layout ?y                |
      | ?y a bf:Layout                 |
      | ?y rdfs:label "double-sided"   |
    Then I should find 1 match

  Scenario: $m creates a bookFormat property of the Instance
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x bf:layout ?y                |
      | ?y a bf:Layout                 |
      | ?y rdfs:label "double-sided"   |
    Then I should find 1 match

  Scenario: $n creates a fontSize property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                        |
      | ?x bf:fontSize ?y                       |
      | ?y a bf:FontSize                        |
      | ?y rdfs:label "giant print (36 point)"  |
    Then I should find 1 match

  Scenario: $o creates a polarity property of the Instance
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x bf:polarity ?y              |
      | ?y a bf:Polarity               |
      | ?y rdfs:label "positive"       |
    Then I should find 1 match

  Scenario: $2 creates a source property on generated Resources
    When I search with patterns:
      | ?x a bf:Generation             |
      | ?x bf:source ?y                |
      | ?y a bf:Source                 |
      | ?y rdfs:label "rda"            |
    Then I should find 1 match
