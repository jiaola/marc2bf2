Feature: 775 - OTHER EDITION ENTRY
  
  Background:
    Given a marc field "=775  0\$tCuba economic news$bHavana ed.$fcu$x0590-2932$eeng$w(OCoLC)2259984"
    When converted by a field converter io.lold.marc2bf2.converters.field760to788.Field775Converter

  Scenario: 775 creates an otherEdition property of the Work
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:otherEdition ?y            |
    Then I should find 1 match

  Scenario: $b creates an editionStatement property of the linked Instance
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:otherEdition ?y            |
      | ?y a bf:Work                     |
      | ?y bf:hasInstance ?z             |
      | ?z a bf:Instance                 |
      | ?z bf:editionStatement "Havana ed." |
    Then I should find 1 match

  Scenario: $e creates a language property of the linked Work
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:otherEdition ?y            |
      | ?y a bf:Work                     |
      | ?y bf:language <http://id.loc.gov/vocabulary/languages/eng> |
      | <http://id.loc.gov/vocabulary/languages/eng> a bf:Language  |
    Then I should find 1 match

  Scenario: $f creates a provisionActivity property of the linked Instance
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:otherEdition ?y            |
      | ?y a bf:Work                     |
      | ?y bf:hasInstance ?z             |
      | ?z a bf:Instance                 |
      | ?z bf:provisionActivity ?p       |
      | ?p a bf:ProvisionActivity        |
      | ?p bf:place <http://id.loc.gov/vocabulary/countries/cu> |
      | <http://id.loc.gov/vocabulary/countries/cu> a bf:Place  |
    Then I should find 1 match