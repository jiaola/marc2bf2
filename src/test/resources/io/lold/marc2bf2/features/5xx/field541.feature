Feature: 541 - IMMEDIATE SOURCE OF ACQUISITION NOTE
  Background:
    Given a marc field "=541  \\$3Materials scheduled for permanent retention$n25$oreels of microfilm$aU.S. Department of Transportation;$cTransfer under schedule;$d1980/01/10.$5DLC"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field541Converter

  Scenario: 541 creates a new Item with an immediateAcquisition/ImmediateAcquisition property
    When I search with patterns:
      | ?x a bf:Item                       |
      | ?x bf:immediateAcquisition ?y      |
      | ?y a bf:ImmediateAcquisition       |
    Then I should find 1 match

  Scenario: $abcdefhno creates an rdfs:label property of the ImmediateAcquistion
    When I search with patterns:
      | ?x a bf:Item                       |
      | ?x bf:immediateAcquisition ?y      |
      | ?y a bf:ImmediateAcquisition       |
      | ?y rdfs:label "25 reels of microfilm U.S. Department of Transportation; Transfer under schedule; 1980/01/10." |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property of the Item
    When I search with patterns:
      | ?x a bf:Item                       |
      | ?x bflc:appliesTo ?y               |
      | ?y a bflc:AppliesTo                |
      | ?y rdfs:label "Materials scheduled for permanent retention" |
    Then I should find 1 match

  Scenario: $5 creates a bflc:applicableInstitution property of the Item
    When I search with patterns:
      | ?x a bf:Item                       |
      | ?x bflc:applicableInstitution ?y   |
      | ?y a bf:Agent                      |
      | ?y bf:code "DLC"                   |
    Then I should find 1 match
