Feature: 247 - FORMER TITLE
  Background: 
    Given a marc field "=247  10$aEverywoman's magazine$fv. 1-24, Jan. 1948-57."
    And a marc field "=247  00$aDistribution of the principal kinds of soil :$borders, suborders, and great groups : National Soil Survey Classification of 1967."
    And a marc field "=247  00$aArchives for meteorology, geophysics, and bioclimatology.$nSerie A,$pMeteorology and geophysics$g(varies slightly)$x1234-5678"
    When converted by a field converter io.lold.marc2bf2.converters.field200to247not240.Field247Converter

  Scenario: multi 247s should create title properties in Instance
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
    Then I should find 3 match

  Scenario: $abgnp in record order creates a rdfs:label for Title
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x bf:title ?y                 |
      | ?y a bf:Title                  |
      | ?y rdfs:label "Archives for meteorology, geophysics, and bioclimatology. Serie A, Meteorology and geophysics (varies slightly)" |
    Then I should find 1 match

  Scenario: $a becomes the mainTitle
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bf:mainTitle "Everywoman's magazine" |
    Then I should find 1 match

  Scenario: $b becomes the subtitle
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bf:subtitle "orders, suborders, and great groups : National Soil Survey Classification of 1967" |
    Then I should find 1 match

  Scenario: $f becomes the date
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:title ?y                     |
      | ?y a bf:Title                      |
      | ?y bf:date "v. 1-24, Jan. 1948-57" |
    Then I should find 1 match

  Scenario: $g becomes a qualifier
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:title ?y                     |
      | ?y a bf:Title                      |
      | ?y bf:qualifier "varies slightly"  |
    Then I should find 1 match

  Scenario: $n becomes a partNumber
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bf:partNumber "Serie A"    |
    Then I should find 1 match

  Scenario: $p becomes a partName
    When I search with patterns:
      | ?x a bf:Instance                             |
      | ?x bf:title ?y                               |
      | ?y a bf:Title                                |
      | ?y bf:partName "Meteorology and geophysics"  |
    Then I should find 1 match

  Scenario: $x becomes the identifiedBy Issn
    When I search with patterns:
      | ?x a bf:Instance                             |
      | ?x bf:title ?y                               |
      | ?y a bf:Title                                |
      | ?y bf:identifiedBy ?z                        |
      | ?z a bf:Issn                                 |
      | ?z rdf:value "1234-5678"                     |
    Then I should find 1 match
    
