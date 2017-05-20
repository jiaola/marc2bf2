Feature: 242 - TRANSLATION OF TITLE BY CATALOGING AGENCY

  Background:
    Given a marc field "=242  03$aLe Bureau$yfre"
    And a marc field "=242  14$aThe Office$yeng"
    And a marc field "=242  00$aAnnual report of the Minister of Supply and Service Canada under the Corporations and Labour Unions Returns Act.$nPart II,$pLabour unions =$bRapport annuel du ministre des Approvisionnements et services Canada présenté sous l'empire et des syndicates ouvriers.$nPartie II,$pSyndicats ouvriers."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field242Converter
  
  Scenario: multiple 242 should create title properties in Instance
    When I search with patterns:
      | ?x a bf:Instance            |
      | ?x bf:title ?y              |
    Then I should find 3 matches

  Scenario: @ind2 should create the appropriate titleSortKey
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bflc:titleSortKey "Bureau" |
    Then I should find 1 match

  Scenario: $abchnp in record order creates a rdfs:label for Title
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x bf:title ?y                 |
      | ?y a bf:Title                  |
      | ?y rdfs:label "Annual report of the Minister of Supply and Service Canada under the Corporations and Labour Unions Returns Act. Part II, Labour unions = Rapport annuel du ministre des Approvisionnements et services Canada présenté sous l'empire et des syndicates ouvriers. Partie II, Syndicats ouvriers." |
    Then I should find 1 match

  Scenario: $a becomes the mainTitle
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bf:mainTitle "Le Bureau"   |
    Then I should find 1 match

  Scenario: $b becomes the subtitle
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bf:subtitle "Rapport annuel du ministre des Approvisionnements et services Canada présenté sous l'empire et des syndicates ouvriers"      |
    Then I should find 1 match

  Scenario: $n becomes the partNumber
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bf:partNumber "Partie II"  |
    Then I should find 1 match

  Scenario: $n becomes the partName
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:title ?y                      |
      | ?y a bf:Title                       |
      | ?y bf:partName "Syndicats ouvriers" |
    Then I should find 1 match

  Scenario: $p becomes the partName
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:title ?y                      |
      | ?y a bf:Title                       |
      | ?y bf:partName "Syndicats ouvriers" |
    Then I should find 1 match

  Scenario: $y becomes the language
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:title ?y                      |
      | ?y a bf:Title                       |
      | ?y bf:language <http://id.loc.gov/vocabulary/languages/fre> |
    Then I should find 1 match