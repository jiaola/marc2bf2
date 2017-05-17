Feature: 130 - MAIN ENTRY - UNIFORM TITLE
  
  Background:
    Given a marc field "=130  4\$aThe Encyclopedia of Latin American history and culture.$d(1952).$f[between 1775 and 1800].$gManuscripts, Latin.$hSound recording.$lFrench$mviolin, viola,$rD major.$oarr.$pO.T.$nBook 4.$kSelections.$0(ID)0001$w(ID)0002"
    When converted by a field converter io.lold.marc2bf2.converters.field240_X30.Field130Converter

  Scenario: Work rdfs:label generation
    When I search with patterns:
      | ?x a bf:Work      |
      | ?x rdfs:label "The Encyclopedia of Latin American history and culture. (1952). [between 1775 and 1800]. Manuscripts, Latin. French violin, viola, D major. arr. O.T. Book 4. Selections." |
    Then I should find 1 match

  Scenario: title match key and marc key generation
    When I search with patterns:
      | ?x a bf:Work          |
      | ?x bf:title ?y        |
      | ?y a bf:Title         |
      | ?y bflc:title30MatchKey "The Encyclopedia of Latin American history and culture. (1952). [between 1775 and 1800]. Manuscripts, Latin. French violin, viola, D major. arr. O.T. Book 4. Selections." |
      | ?y bflc:title30MarcKey "1304 $aThe Encyclopedia of Latin American history and culture.$d(1952).$f[between 1775 and 1800].$gManuscripts, Latin.$hSound recording.$lFrench$mviolin, viola,$rD major.$oarr.$pO.T.$nBook 4.$kSelections.$0(ID)0001$w(ID)0002" |
    Then I should find 1 match

  Scenario: title rdfs:label generation
    When I search with patterns:
      | ?x a bf:Work          |
      | ?x bf:title ?y        |
      | ?y a bf:Title         |
      | ?y rdfs:label "The Encyclopedia of Latin American history and culture. (1952). [between 1775 and 1800]. Manuscripts, Latin. French violin, viola, D major. arr. O.T. Book 4. Selections." |
    Then I should find 1 match

  Scenario: title sort string generation
    When I search with patterns:
      | ?x a bf:Work          |
      | ?x bf:title ?y        |
      | ?y a bf:Title         |
      | ?y bflc:titleSortKey "Encyclopedia of Latin American history and culture. (1952). [between 1775 and 1800]. Manuscripts, Latin. French violin, viola, D major. arr. O.T. Book 4. Selections." |
    Then I should find 1 match

  Scenario: $a/$t becomes mainTitle
    When I search with patterns:
      | ?x a bf:Work          |
      | ?x bf:title ?y        |
      | ?y a bf:Title         |
      | ?y bf:mainTitle "The Encyclopedia of Latin American history and culture" |
    Then I should find 1 match

  Scenario: $d becomes a legalDate
    When I search with patterns:
      | ?x a bf:Work           |
      | ?x bf:legalDate "1952" |
    Then I should find 1 match

  Scenario: $f becomes the originDate
    When I search with patterns:
      | ?x a bf:Work                               |
      | ?x bf:originDate "[between 1775 and 1800]" |
    Then I should find 1 match

  Scenario: $g becomes a genreForm
    When I search with patterns:
      | ?x a bf:Work                       |
      | ?x bf:genreForm ?y                 |
      | ?y a bf:GenreForm                  |
      | ?y rdfs:label "Manuscripts, Latin" |
    Then I should find 1 match

  Scenario: $h becomes a genreForm
    When I search with patterns:
      | ?x a bf:Work                       |
      | ?x bf:genreForm ?y                 |
      | ?y a bf:GenreForm                  |
      | ?y rdfs:label "Sound recording"    |
    Then I should find 1 match

  Scenario: $k becomes a natureOfContent
    When I search with patterns:
      | ?x a bf:Work                       |
      | ?x bf:natureOfContent "Selections" |
    Then I should find 1 match

  Scenario: $k also becomes a genreForm
    When I search with patterns:
      | ?x a bf:Work                       |
      | ?x bf:genreForm ?y                 |
      | ?y a bf:GenreForm                  |
      | ?y rdfs:label "Selections"         |
    Then I should find 1 match

  Scenario: $m becomes a musicMedium
    When I search with patterns:
      | ?x a bf:Work                       |
      | ?x bf:musicMedium ?y               |
      | ?y a bf:MusicMedium                |
      | ?y rdfs:label "violin, viola"      |
    Then I should find 1 match

  Scenario: $n becomes a partNumber
    When I search with patterns:
      | ?x a bf:Work              |
      | ?x bf:title ?y            |
      | ?y a bf:Title             |
      | ?y bf:partNumber "Book 4" |
    Then I should find 1 match

  Scenario: $o becomes a version
    When I search with patterns:
      | ?x a bf:Work                       |
      | ?x bf:version "arr"                |
    Then I should find 1 match

  Scenario: $p becomes a partName
    When I search with patterns:
      | ?x a bf:Work              |
      | ?x bf:title ?y            |
      | ?y a bf:Title             |
      | ?y bf:partName "O.T"      |
    Then I should find 1 match

  Scenario: $r becomes a musicKey
    When I search with patterns:
      | ?x a bf:Work                       |
      | ?x bf:musicKey "D major"           |
    Then I should find 1 match

  Scenario: $0 becomes an identifiedBy
    When I search with patterns:
      | ?x a bf:Work              |
      | ?x bf:identifiedBy ?y     |
      | ?y a bf:Identifier        |
      | ?y rdf:value "0001"       |
    Then I should find 1 match

  Scenario: $w becomes an identifiedBy
    When I search with patterns:
      | ?x a bf:Work              |
      | ?x bf:identifiedBy ?y     |
      | ?y a bf:Identifier        |
      | ?y rdf:value "0002"       |
    Then I should find 1 match