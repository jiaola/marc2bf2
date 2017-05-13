Feature: 047 - FORM OF MUSICAL COMPOSITION CODE

  Background:
    Given a marc field "=047  \\$aor$act"
    And a marc field "=047  \7$argg$2iamlmf"
    When converted by a field converter io.lold.marc2bf2.converters.field010to048.Field047Converter

  Scenario: 047 creates a genreForm property of the Work
    When I search with patterns:
      | ?x a bf:Work           |
      | ?x bf:genreForm ?y     |
      | ?y a bf:GenreForm      |
      | ?y bf:code "rgg"       |
    Then I should find 1 match

  Scenario: ind2 can set the source of the GenreForm
            and set the URI for the GenreForm
    When I search with patterns:
      | ?x a bf:Work                |
      | ?x bf:genreForm <http://id.loc.gov/vocabulary/marcmuscomp/ct> |
      | <http://id.loc.gov/vocabulary/marcmuscomp/ct> a bf:GenreForm  |
      | ?y bf:source ?z             |
      | ?z a bf:Source              |
      | ?z rdfs:label "marcmuscomp" |
    Then I should find 2 match

  Scenario: $2 sets the source of the GenreForm
    When I search with patterns:
      | ?x a bf:Work                |
      | ?x bf:genreForm ?y          |
      | ?y a bf:GenreForm           |
      | ?y bf:source ?z             |
      | ?z a bf:Source              |
      | ?z rdfs:label "iamlmf"      |
    Then I should find 1 match
  