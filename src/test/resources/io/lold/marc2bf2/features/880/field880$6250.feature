Feature: 880$6250 - ALTERNATE GRAPHIC REPRESENTATION -  EDITION STATEMENT
  
  Background:
    Given a marc leader "=LDR  00000nam a2200000 a 4500"
    And a marc field "=880  \\$6250-01$aТретий проект /$bпод редакцией Пола Уотсона.$32000-2006"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field880Converter

  Scenario: $6 250 creates an editionStatement property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                     |
      | ?x bf:editionStatement "Третий проект / под редакцией Пола Уотсона" |
    Then I should find 1 match










