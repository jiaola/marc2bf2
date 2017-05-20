Feature: 740 - ADDED ENTRY--UNCONTROLLED RELATED/ANALYTICAL TITLE
  
  Background:
    Given a marc field "=740  02$aJoint Legislative Committee on Matrimonial and Family Laws, proposed statute."
    And a marc field "=740  0\$aManual del adivino."
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field740Converter
    
  Scenario: 740 I2=2 becomes a hasPart of the main Work
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:hasPart ?y                     |
      | ?y a bf:Work                         |
      | ?y bf:title ?z                       |
      | ?z a bf:Title                        |
      | ?z bf:mainTitle "Joint Legislative Committee on Matrimonial and Family Laws, proposed statute" |
    Then I should find 1 match

  Scenario: 740 otherwise becomes a relatedTo of the main Work
    When I search with patterns:
      | ?x a bf:Work                         |
      | ?x bf:relatedTo ?y                   |
      | ?y a bf:Work                         |
      | ?y bf:title ?z                       |
      | ?z a bf:Title                        |
      | ?z bf:mainTitle "Manual del adivino" |
    Then I should find 1 match


