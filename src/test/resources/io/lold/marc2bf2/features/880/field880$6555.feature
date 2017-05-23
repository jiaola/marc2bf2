Feature: 880$6555 - CUMULATIVE INDEX/FINDING AIDS NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6555-01$aСовокупный индекс с учетом включенных в каждом томе, -V. 29."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 555 creates a note property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                      |
      | ?x bf:note ?y                         |
      | ?y a bf:Note                          |
      | ?y rdfs:label "Совокупный индекс с учетом включенных в каждом томе, -V. 29." |
    Then I should find 1 match

