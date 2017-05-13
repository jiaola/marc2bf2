Feature: 210 - ABBREVIATED TITLE

  Background:
    Given a marc field "=210  10$aActa Cytol$2DNLM"
    And a marc field "=210  10$aActa Cytol."
    And a marc field "=210  0\$aIdler$b(Lond.)"
    When converted by a field converter io.lold.marc2bf2.converters.field200to247not240.Field210Converter
  
  Scenario: multiple 210s should create title properties in Instance
    When I search with patterns:
      | ?x a bf:Instance            |
      | ?x bf:title ?y              |
    Then I should find 3 matches

  Scenario: @ind2 = ' ' creates an issnkey source
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bf:source ?z               |
      | ?z a bf:Source                |
      | ?z rdf:value "issnkey"        |
    Then I should find 1 match

  Scenario: $a $b in record order creates a rdfs:label for Title
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y rdfs:label "Idler (Lond.)" |
    Then I should find 1 match

  Scenario: $a becomes the mainTitle
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bf:mainTitle "Idler"       |
    Then I should find 1 match

  Scenario: $b becomes the qualifier
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bf:qualifier "Lond."       |
    Then I should find 1 match

  Scenario: $2 becomes the source
    When I search with patterns:
      | ?x a bf:Instance              |
      | ?x bf:title ?y                |
      | ?y a bf:Title                 |
      | ?y bf:source ?z               |
      | ?z a bf:Source                |
      | ?z rdfs:label "DNLM"          |
    Then I should find 1 match
