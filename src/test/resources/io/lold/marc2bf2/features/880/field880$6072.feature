Feature: 880$6072 - ALTERNATE GRAPHIC REPRESENTATION - SUBJECT CATEGORY CODE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \7$6072-01$aז 1$x.630$2ייגל"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 072 creates a subject property of the Work
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:subject ?y                     |
      | ?y a rdfs:Resource                   |
      | ?y rdf:value "ז 1 .630"              |
    Then I should find 1 match
