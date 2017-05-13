Feature: 501 - WITH NOTE
  Background:
    Given a marc field "=501  \\$aWith: The reformed school / John Dury. London : Printed for R. Wasnothe, [1850]$5DLC"
    When converted by a field converter io.lold.marc2bf2.converters.field5XX.Field501Converter
  Scenario: 501 creates a note/Note property of the Instance with noteType 'with'
    When I search with patterns:
      | ?x a bf:Instance       |
      | ?x bf:note ?y          |
      | ?y a bf:Note           |
      | ?y bf:noteType "with"  |
    Then I should find 1 match

  Scenario: $a creates an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Instance       |
      | ?x bf:note ?y          |
      | ?y a bf:Note           |
      | ?y rdfs:label "With: The reformed school / John Dury. London : Printed for R. Wasnothe, [1850]" |
    Then I should find 1 match

  Scenario: $5 creates a bflc:applicableInstitution property of the Note
    When I search with patterns:
      | ?x a bf:Instance                 |
      | ?x bf:note ?y                    |
      | ?y a bf:Note                     |
      | ?y bflc:applicableInstitution ?z |
      | ?z a bf:Agent                    |
      | ?z bf:code "DLC"                 |
    Then I should find 1 match
