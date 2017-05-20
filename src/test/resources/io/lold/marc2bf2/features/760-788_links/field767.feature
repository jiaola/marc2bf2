Feature: 767 - TRANSLATION ENTRY
  
  Background:
    Given a marc field "=767  0\$tAstrofizicheskie issledovaniíà. English. Bulletin of the Special Astrophysical Observatory (North Caucasus)$x0190-2709$w(DLC)   86649325 $w(OCoLC)4698159"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field767Converter

  Scenario: 767 creates a translation property of the Work
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:translation ?y             |
    Then I should find 1 match

  Scenario: $x creates an identifiedBy/Issn property of the linked Instance
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:translation ?y             |
      | ?y a bf:Work                     |
      | ?y bf:hasInstance ?z             |
      | ?z a bf:Instance                 |
      | ?z bf:identifiedBy ?i            |
      | ?i a bf:Issn                     |
      | ?i rdf:value "0190-2709"         |
    Then I should find 1 match
