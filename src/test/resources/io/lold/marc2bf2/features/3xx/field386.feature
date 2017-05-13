Feature: 386 - CREATOR/CONTRIBUTOR CHARACTERISTICS
  Background:
    Given a marc field "=386  \\$3Other stuff$mGender group$ngen$aFemales$bf$2ericd"
    And a marc field "=386  \\$aYoungsters$baaa$0(DLC)dg2015060010"
    When converted by a field converter io.lold.marc2bf2.converters.field3XX.Field385_386Converter

  Scenario: $a creates an bflc:creatorCharacteristic property of the Work
    When I search with patterns:
      | ?x a bf:Work                       |
      | ?x bflc:creatorCharacteristic ?y   |
      | ?y a bflc:CreatorCharacteristic    |
      | ?y rdfs:label "Females"            |
    Then I should find 1 match

  Scenario: $b creates a code property of the CreatorCharacteristic
    When I search with patterns:
      | ?x a bf:Work                       |
      | ?x bflc:creatorCharacteristic ?y   |
      | ?y a bflc:CreatorCharacteristic    |
      | ?y bf:code "f"                     |
    Then I should find 1 match

  Scenario: If there is a $0 with a standard number value starting with 'dg',
            $b also creates a bflc:demographicGroup with a URI in demographicTerms
    When I search with patterns:
      | ?x a bf:Work                       |
      | ?x bflc:creatorCharacteristic ?y   |
      | ?y a bflc:CreatorCharacteristic    |
      | ?y bflc:demographicGroup <http://id.loc.gov/authorities/demographicTerms/dg2015060010> |
    Then I should find 1 match

  Scenario: $m creates a bflc:demographicGroup property of the IntendedAudience
    When I search with patterns:
      | ?x a bflc:CreatorCharacteristic  |
      | ?x bflc:demographicGroup $y      |
      | ?y a bflc:DemographicGroup       |
      | ?y rdfs:label "Gender group"     |
    Then I should find 1 match

  Scenario: $n creates a bflc:demographicGroup property of the CreatorCharacteristic
    When I search with patterns:
      | ?x a bflc:CreatorCharacteristic  |
      | ?x bflc:demographicGroup $y      |
      | ?y a bflc:DemographicGroup       |
      | ?y rdfs:label "gen"              |
    Then I should find 1 match

  Scenario: $2 creates a source property of the MusicMedium
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bflc:creatorCharacteristic ?y |
      | ?y a bflc:CreatorCharacteristic  |
      | ?y bf:source ?z                  |
      | ?z a bf:Source                   |
      | ?z rdfs:label "ericd"            |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property of the CreatorCharacteristic
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bflc:creatorCharacteristic ?y |
      | ?y a bflc:CreatorCharacteristic  |
      | ?y bflc:appliesTo ?z             |
      | ?z a bflc:AppliesTo              |
      | ?z rdfs:label "Other stuff"      |
    Then I should find 1 match
