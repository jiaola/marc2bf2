Feature: 776 - ADDITIONAL PHYSICAL FORM ENTRY
  
  Background:
    Given a marc field "=776  0\$cOriginal$w(DLC)   24020326 "
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field776Converter

  Scenario: 776 creates an otherPhysicalFormat property of the Work
    When I search with patterns:
      | ?x a bf:Instance                 |
      | ?x bf:otherPhysicalFormat ?y     |
    Then I should find 1 match

  Scenario: Processing for 776 places properties on an Instance, not a work
    When I search with patterns:
      | ?x a bf:Instance                 |
      | ?x bf:otherPhysicalFormat ?y     |
      | ?y a bf:Instance                 |
      | ?y bf:identifiedBy ?z            |
      | ?z a bf:Identifier               |
      | ?z rdf:value "   24020326 "      |
    Then I should find 1 match
