Feature: 246 - VARYING FORM OF TITLE

  Scenario: multi 246s should create title properties in Instance
    Given a marc field "=246  31$aModerne Probleme der Pharmakopsychiatrie"
    And a marc field "=246  31$aProblèmes actuels de pharmacopsychiatrie"
    When converted by a field converter io.lold.marc2bf2.converters.field200to247not240.Field246Converter
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
    Then I should find 2 match

  Scenario: @ind2='1' should create a ParallelTitle
    Given a marc field "=246  31$aModerne Probleme der Pharmakopsychiatrie"
    And a marc field "=246  31$aProblèmes actuels de pharmacopsychiatrie"
    When converted by a field converter io.lold.marc2bf2.converters.field200to247not240.Field246Converter
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y a bf:ParallelTitle         |
    Then I should find 2 match

  Scenario: @ind2 != '1' should become the variantType
    Given a marc field "=246  30$aZeitschrift für analytische Chemie$fJuli 1976-"
    When converted by a field converter io.lold.marc2bf2.converters.field200to247not240.Field246Converter
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bf:variantType "portion"   |
    Then I should find 1 match

  Scenario: $abgnp in record order creates a rdfs:label for Title
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=246  31$aArchives for meteorology, geophysics, and bioclimatology :$ban engrossing read.$nSerie A,$pMeteorology and geophysics$5DLC"
    When converted by a field converter io.lold.marc2bf2.converters.field200to247not240.Field246Converter
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x bf:title ?y                 |
      | ?y a bf:Title                  |
      | ?y rdfs:label "Archives for meteorology, geophysics, and bioclimatology : an engrossing read. Serie A, Meteorology and geophysics" |
    Then I should find 1 match

  Scenario: $a becomes the mainTitle
    Given a marc field "=246  31$aModerne Probleme der Pharmakopsychiatrie"
    And a marc field "=246  31$aProblèmes actuels de pharmacopsychiatrie"
    When converted by a field converter io.lold.marc2bf2.converters.field200to247not240.Field246Converter
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bf:mainTitle "Moderne Probleme der Pharmakopsychiatrie" |
    Then I should find 1 match

  Scenario: $b becomes the subtitle
    Given a marc field "=246  2\$aAmerican Library Association bulletin$ban engrossing read"
    When converted by a field converter io.lold.marc2bf2.converters.field200to247not240.Field246Converter
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bf:subtitle "an engrossing read" |
    Then I should find 1 match

  Scenario: $f becomes the date
    Given a marc field "=246  30$aZeitschrift für analytische Chemie$fJuli 1976-"
    When converted by a field converter io.lold.marc2bf2.converters.field200to247not240.Field246Converter
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bf:date "Juli 1976-"       |
    Then I should find 1 match

  Scenario: $n becomes a partNumber
    Given a marc field "=246  31$aArchives for meteorology, geophysics, and bioclimatology :$ban engrossing read.$nSerie A,$pMeteorology and geophysics$5DLC"
    When converted by a field converter io.lold.marc2bf2.converters.field200to247not240.Field246Converter
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bf:partNumber "Serie A"    |
    Then I should find 1 match

  Scenario: $p becomes a partName
    Given a marc field "=246  31$aArchives for meteorology, geophysics, and bioclimatology :$ban engrossing read.$nSerie A,$pMeteorology and geophysics$5DLC"
    When converted by a field converter io.lold.marc2bf2.converters.field200to247not240.Field246Converter
    When I search with patterns:
      | ?x a bf:Instance                             |
      | ?x bf:title ?y                               |
      | ?y a bf:Title                                |
      | ?y bf:partName "Meteorology and geophysics"  |
    Then I should find 1 match

  Scenario: $5 becomes the applicableInstitution
    Given a marc field "=246  31$aArchives for meteorology, geophysics, and bioclimatology :$ban engrossing read.$nSerie A,$pMeteorology and geophysics$5DLC"
    When converted by a field converter io.lold.marc2bf2.converters.field200to247not240.Field246Converter
    When I search with patterns:
      | ?x a bf:Instance                             |
      | ?x bf:title ?y                               |
      | ?y a bf:Title                                |
      | ?y bflc:applicableInstitution ?z             |
      | ?z a bf:Agent                                |
      | ?z bf:code "DLC"                             |
    Then I should find 1 match

