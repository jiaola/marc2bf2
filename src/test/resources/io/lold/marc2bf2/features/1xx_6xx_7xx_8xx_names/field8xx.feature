Feature: 8XX - NAMES - Series Added Entry Fields
  Background:
    Given a marc field "=800  1\$aFernando, A. Denis N.$tResource maps of Sri Lanka ;$vpt. 2."
    When converted by a field converter io.lold.marc2bf2.converters.field1XX_6XX_7XX_8XX.Field8XXNameConverter

  Scenario: 8XX creates a bf:hasSeries
    When I search with patterns:
      | ?x a bf:Work                                 |
      | ?x bf:hasSeries ?y                           |
      | ?y a bf:Work                                 |
      | ?y bf:title ?z                               |
      | ?z a bf:Title                                |
      | ?z bf:mainTitle "Resource maps of Sri Lanka" |
    Then I should find 1 match


