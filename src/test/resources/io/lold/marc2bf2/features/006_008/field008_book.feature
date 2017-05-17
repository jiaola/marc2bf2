Feature: 008 - BOOKS - FIXED-LENGTH DATA ELEMENTS
  Scenario: pos 18-21 create illustrativeContent properties of the Instance
    Given a marc leader "=LDR  01094cam a2200229 a 4500"
    And a marc field "=008  040520s200u    dk ab  js6   a111 1adan  "
    When converted by a field converter io.lold.marc2bf2.converters.field006_008.Field008Converter
    When I search with patterns:
      | ?x a bf:Instance                   |
      | ?x bf:illustrativeContent ?y       |
      | ?y a bf:Illustration               |
      | ?y rdfs:label "Illustrations"      |
    Then I should find 1 match

  Scenario: pos 22 creates an intendedAudience property of the Work
    Given a marc leader "=LDR  01094cam a2200229 a 4500"
    And a marc field "=008  040520s200u    dk ab  js6   a111 1adan  "
    When converted by a field converter io.lold.marc2bf2.converters.field006_008.Field008Converter
    When I search with patterns:
      | ?x a bf:Work                        |
      | ?x bf:intendedAudience ?y           |
      | ?y a bf:IntendedAudience            |
      | ?y rdfs:label "Juvenile"            |
    Then I should find 1 match

  Scenario: Scenario: pos 23 may create a carrier property of the Instance
    Given a marc leader "=LDR  01094cam a2200229 a 4500"
    And a marc field "=008  040520s200u    dk ab  js6   a111 1adan  "
    When converted by a field converter io.lold.marc2bf2.converters.field006_008.Field008Converter
    When I search with patterns:
      | ?x a bf:Instance           |
      | ?x bf:carrier ?y           |
      | ?y a bf:Carrier            |
      | ?y rdfs:label "electronic" |
    Then I should find 1 match

  Scenario: Scenario: pos 23 may creates a fontSize property of the Instance
    Given a marc leader "=LDR  01094cam a2200229 a 4500"
    And a marc field "=008  040520s200u    dk ab  jd     000 1 dan  "
    When converted by a field converter io.lold.marc2bf2.converters.field006_008.Field008Converter
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:fontSize ?y                   |
      | ?y a bf:FontSize                    |
      | ?y rdfs:label "large print"         |
    Then I should find 1 match

  Scenario: Scenario: pos 23 may creates a notation property of the Instance
    Given a marc leader "=LDR  01094cam a2200229 a 4500"
    And a marc field "=008  040520s200u    dk ab  jf     000 1 dan  "
    When converted by a field converter io.lold.marc2bf2.converters.field006_008.Field008Converter
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:notation ?y                   |
      | ?y a bf:TactileNotation             |
      | ?y rdfs:label "braille"             |
    Then I should find 1 match

  Scenario: pos 24-27 create genreForm properties of the Work
    Given a marc leader "=LDR  01094cam a2200229 a 4500"
    And a marc field "=008  040520s200u    dk ab  js6   a111 1adan  "
    When converted by a field converter io.lold.marc2bf2.converters.field006_008.Field008Converter
    When I search with patterns:
      | ?x a bf:Work                              |
      | ?x bf:genreForm ?y                        |
      | ?y a bf:GenreForm                         |
      | ?y rdfs:label "comic or graphic novel"    |
    Then I should find 1 match

  Scenario: pos 28 creates a genreForm for a gov doc
    Given a marc leader "=LDR  01094cam a2200229 a 4500"
    And a marc field "=008  040520s200u    dk ab  js6   a111 1adan  "
    When converted by a field converter io.lold.marc2bf2.converters.field006_008.Field008Converter
    When I search with patterns:
      | ?x a bf:Work                              |
      | ?x bf:genreForm ?y                        |
      | ?y a bf:GenreForm                         |
      | ?y rdfs:label "autonomous or semi-autonomous government publication"    |
    Then I should find 1 match

  Scenario: pos 29 creates a genreForm for a conference pub
    Given a marc leader "=LDR  01094cam a2200229 a 4500"
    And a marc field "=008  040520s200u    dk ab  js6   a111 1adan  "
    When converted by a field converter io.lold.marc2bf2.converters.field006_008.Field008Converter
    When I search with patterns:
      | ?x a bf:Work                              |
      | ?x bf:genreForm ?y                        |
      | ?y a bf:GenreForm                         |
      | ?y rdfs:label "conference publication"    |
    Then I should find 1 match

  Scenario: pos 30 creates a genreForm for a festschrift
    Given a marc leader "=LDR  01094cam a2200229 a 4500"
    And a marc field "=008  040520s200u    dk ab  js6   a111 1adan  "
    When converted by a field converter io.lold.marc2bf2.converters.field006_008.Field008Converter
    When I search with patterns:
      | ?x a bf:Work                              |
      | ?x bf:genreForm ?y                        |
      | ?y a bf:GenreForm                         |
      | ?y rdfs:label "festschrift"               |
    Then I should find 1 match

  Scenario: pos 31 creates a supplementaryContent property of the Instance
    Given a marc leader "=LDR  01094cam a2200229 a 4500"
    And a marc field "=008  040520s200u    dk ab  js6   a111 1adan  "
    When converted by a field converter io.lold.marc2bf2.converters.field006_008.Field008Converter
    When I search with patterns:
      | ?x a bf:Instance                          |
      | ?x bf:supplementaryContent ?y             |
      | ?y a bf:SupplementaryContent              |
      | ?y rdfs:label "Index present"             |
    Then I should find 1 match

  Scenario: pos 33 creates a genreForm for the literary form
    Given a marc leader "=LDR  01094cam a2200229 a 4500"
    And a marc field "=008  040520s200u    dk ab  js6   a111 1adan  "
    When converted by a field converter io.lold.marc2bf2.converters.field006_008.Field008Converter
    When I search with patterns:
      | ?x a bf:Work                              |
      | ?x bf:genreForm ?y                        |
      | ?y a bf:GenreForm                         |
      | ?y rdfs:label "fiction"                   |
    Then I should find 1 match

  Scenario: pos 33 creates a genreForm for biography
    Given a marc leader "=LDR  01094cam a2200229 a 4500"
    And a marc field "=008  040520s200u    dk ab  js6   a111 1adan  "
    When converted by a field converter io.lold.marc2bf2.converters.field006_008.Field008Converter
    When I search with patterns:
      | ?x a bf:Work                              |
      | ?x bf:genreForm ?y                        |
      | ?y a bf:GenreForm                         |
      | ?y rdfs:label "autobiography"             |
    Then I should find 1 match
