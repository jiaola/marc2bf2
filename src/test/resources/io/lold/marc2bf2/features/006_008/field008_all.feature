Feature: 008 - ALL MATERIALS - FIXED-LENGTH DATA ELEMENTS--GENERAL INFORMATION
  Scenario: pos 0-5 should set the AdminMetadata creationDate property of the Work
    Given a marc leader "=LDR  01094cam a2200229 a 4500"
    And a marc field "=008  040520s200u    dk ab  js6   a111 1adan  "
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field008Converter
    When I search with patterns:
      | ?x a bf:Work                        |
      | ?x bf:adminMetadata ?y              |
      | ?y a bf:AdminMetadata               |
      | ?y bf:creationDate "2004-05-20"^^<http://www.w3.org/2001/XMLSchema#date>    |
    Then I should find 1 match

  Scenario: pos 6-14 date handling
    Given a marc leader "=LDR  01094cam a2200229 a 4500"
    And a marc field "=008  040520s200u    dk ab  js6   a111 1adan  "
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field008Converter
    When I search with patterns:
      | ?y bf:date "200X"^^<http://id.loc.gov/datatypes/edtf>    |
      | ?x a bf:Instance                     |
      | ?x bf:provisionActivity ?y           |
      | ?y a bf:ProvisionActivity            |
    Then I should find 1 match

  Scenario: | in pos 6 handled like s or m
    Given a marc leader "=LDR  00584nam a22002175a 4500"
    And a marc field "=008  810811|1981    |||           000 0 spa  "
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field008Converter
    When I search with patterns:
      | ?y bf:date "1981"^^<http://id.loc.gov/datatypes/edtf> |
      | ?x a bf:Instance                     |
      | ?x bf:provisionActivity ?y           |
      | ?y a bf:ProvisionActivity            |
      | ?y bf:date ?z                        |
    Then I should find 1 match

  Scenario: pos 15-17 publication place
    Given a marc leader "=LDR  01094cam a2200229 a 4500"
    And a marc field "=008  040520s200u    dk ab  js6   a111 1adan  "
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field008Converter
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:provisionActivity ?y           |
      | ?y a bf:ProvisionActivity            |
      | ?y bf:place <http://id.loc.gov/vocabulary/countries/dk> |
      | <http://id.loc.gov/vocabulary/countries/dk> a bf:Place  |
    Then I should find 1 match

  Scenario: pos 35-37 should set the language property of the Work
    Given a marc leader "=LDR  01094cam a2200229 a 4500"
    And a marc field "=008  040520s200u    dk ab  js6   a111 1adan  "
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field008Converter
    When I search with patterns:
      | ?x a bf:Work                                                |
      | ?x bf:language <http://id.loc.gov/vocabulary/languages/dan> |
      | <http://id.loc.gov/vocabulary/languages/dan> a bf:Language  |
    Then I should find 1 match