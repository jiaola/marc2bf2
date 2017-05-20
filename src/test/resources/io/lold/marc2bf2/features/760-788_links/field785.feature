Feature: 785 - SUCCEEDING ENTRY
  
  Background:
    Given a marc field "=785  04$tBusiness week$gOct. 1940$x0007-7135$w(DLC)   31006225 "
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field785Converter

  Scenario: 785 creates a property of the Work determined by ind2
    When I search with patterns:
      | ?x a bf:Work                     |
      | ?x bf:absorbedBy ?y              |
    Then I should find 1 match
