Feature: 256 - COMPUTER FILE CHARACTERISTICS

  Scenario: 250 creates an note property of the Instance
    Given a marc field "=256  \\$aComputer data (2 files : 876,000, 775,000 records)."
    When converted by a field converter io.lold.marc2bf2.converters.Field256Converter
    When I search with patterns:
      | ?x a bf:Instance           |
      | ?x bf:note ?y              |
      | ?y a bf:Note               |
      | ?y rdfs:label "Computer data (2 files : 876,000, 775,000 records)" |
    Then I should find 1 match
