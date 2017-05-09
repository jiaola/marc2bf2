Feature: 245 - TITLE STATEMENT

  Scenario: @ind2 should create the appropriate titleSortKey
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=245  03$aLe Bureau$h[filmstrip] =$bLa Oficina = Das Büro."
    When converted by a field converter io.lold.marc2bf2.converters.Field245Converter
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bflc:titleSortKey "Bureau La Oficina = Das Büro." |
    Then I should find 1 match

  Scenario: $abfgknps in record order creates a rdfs:label for Title and Instance
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=245  00$aAnnual report of the Minister of Supply and Service Canada under the Corporations and Labour Unions Returns Act.$nPart II,$pLabour unions =$bRapport annuel du ministre des Approvisionnements et services Canada présenté sous l'empire et des syndicates ouvriers.$nPartie II,$pSyndicats ouvriers."
    When converted by a field converter io.lold.marc2bf2.converters.Field245Converter
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x rdfs:label "Annual report of the Minister of Supply and Service Canada under the Corporations and Labour Unions Returns Act. Part II, Labour unions = Rapport annuel du ministre des Approvisionnements et services Canada présenté sous l'empire et des syndicates ouvriers. Partie II, Syndicats ouvriers." |
      | ?x bf:title ?y                 |
      | ?y a bf:Title                  |
      | ?y rdfs:label "Annual report of the Minister of Supply and Service Canada under the Corporations and Labour Unions Returns Act. Part II, Labour unions = Rapport annuel du ministre des Approvisionnements et services Canada présenté sous l'empire et des syndicates ouvriers. Partie II, Syndicats ouvriers." |
    Then I should find 1 match

  Scenario: $a becomes the mainTitle
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=245  03$aLe Bureau$h[filmstrip] =$bLa Oficina = Das Büro."
    When converted by a field converter io.lold.marc2bf2.converters.Field245Converter
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bf:mainTitle "Le Bureau"   |
    Then I should find 1 match

  Scenario: $b becomes the subtitle
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=245  00$aAnnual report of the Minister of Supply and Service Canada under the Corporations and Labour Unions Returns Act.$nPart II,$pLabour unions =$bRapport annuel du ministre des Approvisionnements et services Canada présenté sous l'empire et des syndicates ouvriers.$nPartie II,$pSyndicats ouvriers."
    When converted by a field converter io.lold.marc2bf2.converters.Field245Converter
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bf:subtitle "Rapport annuel du ministre des Approvisionnements et services Canada présenté sous l'empire et des syndicates ouvriers"      |
    Then I should find 1 match

  Scenario: $c becomes the Instance responsibility statement
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=245  04$aThe plays of Oscar Wilde /$cAlan Bird."
    When converted by a field converter io.lold.marc2bf2.converters.Field245Converter
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:responsibilityStatement "Alan Bird" |
    Then I should find 1 match

  Scenario: $f becomes the Instance Work originDate
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=245  10$aShort-Harrison-Symmes family papers,$f1760-1878.$sMember release."
    When converted by a field converter io.lold.marc2bf2.converters.Field245Converter
    When I search with patterns:
      | ?x a bf:Work                 |
      | ?x bf:originDate "1760-1878" |
    Then I should find 1 match

  Scenario: $g becomes the Instance Work originDate
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=245  10$aEmployment applications$gJan.-Dec. 1985."
    When converted by a field converter io.lold.marc2bf2.converters.Field245Converter
    When I search with patterns:
      | ?x a bf:Work                      |
      | ?x bf:originDate "Jan.-Dec. 1985" |
    Then I should find 1 match

  Scenario: $h creates a GenreForm entity in a genreForm property
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=245  10$aThe New Lost City Ramblers with Cousin Emmy$h[sound recording]."
    When converted by a field converter io.lold.marc2bf2.converters.Field245Converter
    When I search with patterns:
      | ?x a bf:Work                      |
      | ?x bf:genreForm ?y                |
      | ?y a bf:GenreForm                 |
      | ?y rdfs:label "sound recording"   |
    Then I should find 1 match

  Scenario: $n becomes a partNumber
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=245  00$aAnnual report of the Minister of Supply and Service Canada under the Corporations and Labour Unions Returns Act.$nPart II,$pLabour unions =$bRapport annuel du ministre des Approvisionnements et services Canada présenté sous l'empire et des syndicates ouvriers.$nPartie II,$pSyndicats ouvriers."
    When converted by a field converter io.lold.marc2bf2.converters.Field245Converter
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bf:partNumber "Partie II"  |
    Then I should find 1 match

  Scenario: $p becomes a partName
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=245  00$aAnnual report of the Minister of Supply and Service Canada under the Corporations and Labour Unions Returns Act.$nPart II,$pLabour unions =$bRapport annuel du ministre des Approvisionnements et services Canada présenté sous l'empire et des syndicates ouvriers.$nPartie II,$pSyndicats ouvriers."
    When converted by a field converter io.lold.marc2bf2.converters.Field245Converter
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:title ?y                       |
      | ?y a bf:Title                        |
      | ?y bf:partName "Syndicats ouvriers"  |
    Then I should find 1 match

  Scenario: $s becomes a Work version
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=245  10$aShort-Harrison-Symmes family papers,$f1760-1878.$sMember release."
    When converted by a field converter io.lold.marc2bf2.converters.Field245Converter
    When I search with patterns:
      | ?x a bf:Work                   |
      | ?x bf:version "Member release" |
    Then I should find 1 match

