Feature: 505 - FORMATTED CONTENTS NOTE
  Background:
    Given a marc field "=505  00$aQuatrain II (16:35) --$tWater ways$g(1:57) /$rby Stephen Paulus."
    When converted by a field converter io.lold.marc2bf2.converters.field5XX.Field505Converter

  Scenario: 505 creates a tableOfContents/TableOfContents property of the Instance
    When I search with patterns:
      | ?x a bf:Instance               |
      | ?x bf:tableOfContents ?y       |
      | ?y a bf:TableOfContents        |
      | ?y rdfs:label "Quatrain II (16:35) -- Water ways (1:57) / by Stephen Paulus." |
    Then I should find 1 match
