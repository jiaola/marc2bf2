Feature: 880$6540 - TERMS GOVERNING USE AND REPRODUCTION NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6540-01$aЛитературные права Carrie Chapman Кэтт были посвящены общественности."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 540 creates a usageAndAccessPolicy property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                      |
      | ?x bf:usageAndAccessPolicy ?y         |
      | ?y a bf:UsePolicy                     |
      | ?y rdfs:label "Литературные права Carrie Chapman Кэтт были посвящены общественности" |
    Then I should find 1 match

