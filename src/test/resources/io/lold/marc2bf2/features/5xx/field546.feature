Feature: 546 - LANGUAGE NOTE
  Background:
    Given a marc field "=546  \\$3Marriage certificate$aGerman;$bFraktur."
    When converted by a field converter io.lold.marc2bf2.converters.field5XX.Field546Converter

  Scenario: 546 creates a language/Language/note/Note property of the Work
    When I search with patterns:
      | ?x a bf:Work           |
      | ?x bf:language ?y      |
      | ?y a bf:Language       |
      | ?y bf:note ?z          |
      | ?z a bf:Note           |
    Then I should find 1 match

  Scenario: $a creates an rdfs:label property of the Note
    When I search with patterns:
      | ?x a bf:Work           |
      | ?x bf:language ?y      |
      | ?y a bf:Language       |
      | ?y bf:note ?z          |
      | ?z a bf:Note           |
      | ?z rdfs:label "German" |
    Then I should find 1 match

  Scenario: $b creates a notation property of the Note
    When I search with patterns:
      | ?x a bf:Work           |
      | ?x bf:language ?y      |
      | ?y a bf:Language       |
      | ?y bf:note ?z          |
      | ?z a bf:Note           |
      | ?z bf:notation ?n      |
      | ?n a bf:Notation       |
      | ?n rdfs:label "Fraktur"|
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property of the Note
    When I search with patterns:
      | ?x a bf:Work           |
      | ?x bf:language ?y      |
      | ?y a bf:Language       |
      | ?y bf:note ?z          |
      | ?z a bf:Note           |
      | ?z bflc:appliesTo ?a   |
      | ?a a bflc:AppliesTo    |
      | ?a rdfs:label "Marriage certificate"|
    Then I should find 1 match
