Feature: 880$6505 - ALTERNATE GRAPHIC REPRESENTATION - FORMATTED CONTENTS NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  0\$6505-01$aКак были обнаружены эти записи - Краткий очерк Талмудов - письмо Константина."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 505 creates a tableOfContents property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:tableOfContents ?y            |
      | ?y a bf:TableOfContents             |
      | ?y rdfs:label "Как были обнаружены эти записи - Краткий очерк Талмудов - письмо Константина." |
    Then I should find 1 match

