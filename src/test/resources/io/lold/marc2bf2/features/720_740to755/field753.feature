Feature: 753 - SYSTEM DETAILS ACCESS TO COMPUTER FILES
  
  Background:
    Given a marc field "=753  \\$aIBM PC$bPascal$cDOS 1.1"
    When converted by a field converter io.lold.marc2bf2.converters.field720_740to755.Field753Converter

  Scenario: $a creates a systemRequirement/bflc:MachineModel property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:systemRequirement ?y           |
      | ?y a bflc:MachineModel               |
      | ?y rdfs:label "IBM PC"               |
    Then I should find 1 match

  Scenario: $b creates a systemRequirement/bflc:ProgrammingLanguage property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:systemRequirement ?y           |
      | ?y a bflc:ProgrammingLanguage        |
      | ?y rdfs:label "Pascal"               |
    Then I should find 1 match

  Scenario: $c creates a systemRequirement/bflc:OperatingSystem property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:systemRequirement ?y           |
      | ?y a bflc:OperatingSystem            |
      | ?y rdfs:label "DOS 1.1"              |
    Then I should find 1 match



