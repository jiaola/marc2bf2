Feature: 037 - SOURCE OF ACQUISITION
  Background: 
    Given a marc field "=037  3\$32014$aFSWEC-77/0420$bNational Technical Information Service, Springfield, VA 22161$fMagnetic tape$gASCII recording mode; available with no internal labels or with ANSI standard labels; logical record length is 1024 bytes; block size is a multiple of 1024 up to 31744 bytes; 1600 or 6250 characters per inch.$nAvailable only without color$cUSD175.00$5Uk"
    And a marc field "=037  \\$aFOO_FSWEC-77/0420$bNational Technical Information Service, Springfield, VA 22161$fStone tablet$5Uk"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field037Converter

  Scenario: 037 creates an acquisitionSource property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:acquisitionSource ?y           |
      | ?y a bf:AcquisitionSource            |
    Then I should find 2 match

  Scenario: ind1 creates a note on the AcquisitionSource
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:acquisitionSource ?y           |
      | ?y a bf:AcquisitionSource            |
      | ?y bf:note ?z                        |
      | ?z a bf:Note                         |
      | ?z rdfs:label "current source"       |
    Then I should find 1 match

  Scenario: $a creates an identifiedBy/StockNumber property of the AcquisitionSource
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:acquisitionSource ?y           |
      | ?y a bf:AcquisitionSource            |
      | ?y bf:identifiedBy ?z                |
      | ?z a bf:StockNumber                  |
      | ?z rdf:value "FSWEC-77/0420"         |
    Then I should find 1 match

  Scenario: $b is the rdfs:label of the AcquistionSource
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:acquisitionSource ?y           |
      | ?y a bf:AcquisitionSource            |
      | ?y rdfs:label "National Technical Information Service, Springfield, VA 22161" |
    Then I should find 2 match

  Scenario: $c creates an acquisitionTerms property of the AcquisitionSource
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:acquisitionSource ?y           |
      | ?y a bf:AcquisitionSource            |
      | ?y bf:acquisitionTerms "USD175.00"   |
    Then I should find 1 match

  Scenario: $f creates a note property of the AcquisitionSource
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:acquisitionSource ?y           |
      | ?y a bf:AcquisitionSource            |
      | ?y bf:note ?z                        |
      | ?z a bf:Note                         |
      | ?z rdfs:label "Stone tablet"         |
    Then I should find 1 match

  Scenario: $g creates a note on the AcquisitionSource
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:acquisitionSource ?y           |
      | ?y a bf:AcquisitionSource            |
      | ?y bf:note ?z                        |
      | ?z a bf:Note                         |
      | ?z rdfs:label "ASCII recording mode; available with no internal labels or with ANSI standard labels; logical record length is 1024 bytes; block size is a multiple of 1024 up to 31744 bytes; 1600 or 6250 characters per inch." |
    Then I should find 1 match

  Scenario: $n creates a note on the AcquisitionSource
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:acquisitionSource ?y           |
      | ?y a bf:AcquisitionSource            |
      | ?y bf:note ?z                        |
      | ?z a bf:Note                         |
      | ?z rdfs:label "Available only without color" |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property of the AcquisitionSource
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:acquisitionSource ?y           |
      | ?y a bf:AcquisitionSource            |
      | ?y bflc:appliesTo ?z                 |
      | ?z a bflc:AppliesTo                  |
      | ?z rdfs:label "2014"                 |
    Then I should find 1 match

  Scenario: $5 creates a bflc:applicableInstitution property of the AcquisitionSource
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:acquisitionSource ?y           |
      | ?y a bf:AcquisitionSource            |
      | ?y bflc:applicableInstitution ?z     |
      | ?z a bf:Agent                        |
      | ?z bf:code "Uk"                      |
    Then I should find 2 match

