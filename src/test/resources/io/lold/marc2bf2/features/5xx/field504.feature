Feature: 504 - BIBLIOGRAPHY, ETC. NOTE
  Background:
    Given a marc field "=504  \\$a"Literature cited": p. 67-68.$b19"
    When converted by a field converter io.lold.marc2bf2.converters.Field504Converter

  Scenario: 504 creates a note/Note property of the Instance with noteType 'bibliography'
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x bf:note ?y                  |
      | ?y a bf:Note                   |
      | ?y bf:noteType "bibliography"  |
    Then I should find 1 match

  Scenario: $a creates an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x bf:note ?y                  |
      | ?y a bf:Note                   |
      | ?y rdfs:label "\"Literature cited\": p. 67-68."  |
    Then I should find 1 match

  Scenario: $b creates a count property of the Note
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x bf:note ?y                  |
      | ?y a bf:Note                   |
      | ?y bf:count "19"               |
    Then I should find 1 match
