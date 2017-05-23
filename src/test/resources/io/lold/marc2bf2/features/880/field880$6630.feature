Feature: 880$6630 - NAMES - Subject Access Fields
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  00$6630-01$aукраинский журнал"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 630 creates a subject/Work property of the Work
    When I search with patterns:
      | ?x a bf:Work                          |
      | ?x bf:subject ?y                      |
      | ?y a bf:Work                          |
      | ?y madsrdf:authoritativeLabel "украинский журнал" |
    Then I should find 1 match

