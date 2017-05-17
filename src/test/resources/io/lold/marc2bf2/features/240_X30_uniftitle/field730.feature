Feature: 730 - ADDED ENTRY - UNIFORM TITLE
  
  Background:
    Given a marc field "=730  02$31980:$aBible.$pO.T.$pPsalms.$sCodex Sinaiticus."
    And a marc field "=730  0\$iParody of (work):$a[Motets].$hSound recording$x1234-5678$5DLC"
    When converted by a field converter io.lold.marc2bf2.converters.field240_X30.Field730Converter

  Scenario: ind2='2' becomes a hasPart of the main Work
    When I search with patterns:
      | ?x a bf:Work            |
      | ?x bf:hasPart ?y        |
      | ?y a bf:Work            |
      | ?y bf:title ?z          |
      | ?z a bf:Title           |
      | ?z bf:mainTitle "Bible" |
    Then I should find 1 match

  Scenario: ind2 != '2' becomes a relatedTo of the main Work
    When I search with patterns:
      | ?x a bf:Work               |
      | ?x bf:relatedTo ?y         |
      | ?y a bf:Work               |
      | ?y bf:title ?z             |
      | ?z a bf:Title              |
      | ?z bf:mainTitle "[Motets]" |
    Then I should find 1 match

  Scenario: $i becomes a bflc:relationship of the main Work
    When I search with patterns:
      | ?x a bf:Work                                   |
      | ?x bflc:relationship ?y                        |
      | ?y a bflc:Relationship                         |
      | ?y bflc:relation ?z                            |
      | ?z a bflc:Relation                             |
      | ?z rdfs:label "Parody of (work)"               |
      | ?y bf:relatedTo <http://example.org/9999999999#Work730-1> |
      | <http://example.org/9999999999#Work730-1> a bf:Work       |

    Then I should find 1 match

  Scenario: $s becomes a version
    When I search with patterns:
      | ?x a bf:Work                       |
      | ?x bf:version "Codex Sinaiticus"   |
    Then I should find 1 match

  Scenario: $x becomes a Issn
    When I search with patterns:
      | ?x a bf:Work              |
      | ?x bf:identifiedBy ?y     |
      | ?y a bf:Issn              |
      | ?y rdf:value "1234-5678"  |
    Then I should find 1 match

  Scenario: $3 becomes a bflc:appliesTo
    When I search with patterns:
      | ?x a bf:Work              |
      | ?x bflc:appliesTo ?y      |
      | ?y a bflc:AppliesTo       |
      | ?y rdfs:label "1980"      |
    Then I should find 1 match

  Scenario: $5 becomes a bflc:applicableInstitution
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bflc:applicableInstitution ?y |
      | ?y a bf:Agent                    |
      | ?y bf:code "DLC"                 |
    Then I should find 1 match