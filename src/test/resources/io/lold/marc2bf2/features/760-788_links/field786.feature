Feature: 786 - DATA SOURCE ENTRY
  
  Background:
    Given a marc field "=786  0\$aUnited States. Defense Mapping Agency.$tReno, NV-CA west digital terrain elevation data$vData for reformatting to DEM format"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field786Converter

  Scenario: 786 creates a dataSource property of the WorkK
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:dataSource ?y              |
    Then I should find 1 match

  Scenario: $v creates a note property of the linked Work
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:dataSource ?y              |
      | ?y a bf:Work                     |
      | ?y bf:note ?z                    |
      | ?z a bf:Note                     |
      | ?z rdfs:label "Data for reformatting to DEM format" |
    Then I should find 1 match
