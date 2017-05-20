Feature: 653 - INDEX TERM--UNCONTROLLED

  Background:
    Given a marc field "=653  \\$aMan$aEye$aDiseases"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field653Converter

  Scenario: 653 creates a subject/Topic property of the Work
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:subject  ?y                    |
      | ?y a bf:Topic                        |
    Then I should find 1 match

  Scenario: $a (repeated) creates an rdfs:label property of the Topic
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:subject  ?y                    |
      | ?y a bf:Topic                        |
      | ?y rdfs:label "Man--Eye--Diseases"   |
    Then I should find 1 match

