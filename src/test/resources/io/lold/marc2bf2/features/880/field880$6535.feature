Feature: 880$6535 - LOCATION OF ORIGINALS/DUPLICATES NOTE
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6534-01$pПервоначально опубликовано:$cНью-Йорк: Garland, 1987."
    And a marc field "=880  1\$6535-01$3Угольные отчеты$aАмериканский горный конгресс;$b1920 N St., NW, Washington, D.C. 20036;$d202-861-2800"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 535 creates a hasItem/Item property of the related 880/533 or 880/534 Instance
    When I search with patterns:
      | ?x a bf:Work                          |
      | ?x bf:hasInstance ?y                  |
      | ?y a bf:Instance                      |
      | ?y bf:hasItem ?z                      |
      | ?z a bf:Item                          |
      | ?z bf:heldBy ?h                       |
      | ?h a bf:Agent                         |
      | ?h rdfs:label "Американский горный конгресс" |
    Then I should find 1 match

