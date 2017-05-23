Feature: 880$6520 - SUMMARY, ETC.
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6520-01$aИллюстрированный сборник потешек на музыку."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 520 creates a summary property of the Work
    When I search with patterns:
      | ?x a bf:Work                        |
      | ?x bf:summary ?y                    |
      | ?y a bf:Summary                     |
      | ?y rdfs:label "Иллюстрированный сборник потешек на музыку." |
    Then I should find 1 match

