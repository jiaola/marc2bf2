Feature: 045 - TIME PERIOD OF CONTENT
  Background: 
    Given a marc field "=045  2\$ad7n6$bc0221$bd0960"
    When converted by a field converter io.lold.marc2bf2.converters.field010to048.Field045Converter

  Scenario: $a creates a temporalCoverage property of the Work
    When I search with patterns:
      | ?x bf:temporalCoverage "-02XX/096X"^^<http://id.loc.gov/datatypes/edtf>  |
      | ?x a bf:Work                                                             |
    Then I should find 1 match

  Scenario: $b creates a temporalCoverage property of the Work
    When I search with patterns:
      | ?x bf:temporalCoverage "-0221/0960"^^<http://id.loc.gov/datatypes/edtf>  |
      | ?x a bf:Work                                                             |
    Then I should find matches

