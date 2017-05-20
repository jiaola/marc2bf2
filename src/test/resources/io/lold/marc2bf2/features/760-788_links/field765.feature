Feature: 765 - ORIGINAL LANGUAGE ENTRY
  
  Background:
    Given a marc field "=765  0\$cOriginal$tAstrofizicheskie issledovaniíà$w(DLC)   78648457 $w(OCoLC)4798581"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field765Converter

  Scenario: 765 creates a translationOf property of the Work
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:translationOf ?y           |
    Then I should find 1 match

  Scenario: $c creates a title/Title/qualifier property of the title of the linked Work
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:translationOf ?y           |
      | ?y a bf:Work                     |
      | ?y bf:title ?z                   |
      | ?z a bf:Title                    |
      | ?z bf:qualifier "Original"       |
    Then I should find 1 match

  Scenario: $w creates an identifiedBy/Identifier property of the linked Work
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:translationOf ?y           |
      | ?y a bf:Work                     |
      | ?y bf:identifiedBy ?z            |
      | ?z a bf:Identifier               |
      | ?z rdf:value "   78648457 "      |
    Then I should find 1 match