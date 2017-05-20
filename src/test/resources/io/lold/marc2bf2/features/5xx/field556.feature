Feature: 556 - INFORMATION ABOUT DOCUMENTATION NOTE
  Background:
    Given a marc field "=556  \\$aBASIC reference. 3rd ed. Boca Raton, Fl. : IBM, c1984. (Personal computer hardware reference library); 6361132."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field556Converter

  Scenario: 556 creates a note property of the Instance
    When I search with patterns:
      | ?x a bf:Instance           |
      | ?x bf:note ?y              |
      | ?y a bf:Note               |
      | ?y rdfs:label "BASIC reference. 3rd ed. Boca Raton, Fl. : IBM, c1984. (Personal computer hardware reference library); 6361132." |
    Then I should find 1 match
