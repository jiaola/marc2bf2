Feature: 880$6653 - INDEX TERM--UNCONTROLLED

  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6653-01$aтопливные элементы"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 653 creates a subject/Topic property of the Work
    When I search with patterns:
      | ?x a bf:Work                          |
      | ?x bf:subject ?y                      |
      | ?y a bf:Topic                         |
      | ?y rdfs:label "топливные элементы"    |
    Then I should find 1 match

