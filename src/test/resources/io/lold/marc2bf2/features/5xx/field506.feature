Feature: 506 - RESTRICTIONS ON ACCESS NOTE
  Background:
    Given a marc field "=506  \\$3Office files of Under Secretary$aRestricted access;$cWritten permission required;$bDonor;$dMembers of donor's family;$eTitle 50, chapter 401, U.S.C.$fPreview only$uhttps://www.gnu.org/licenses/gpl-3.0.txt$5MH"
    When converted by a field converter io.lold.marc2bf2.converters.impls.Field506Converter

  Scenario: 506 creates a usageAndAccessPolicy/UsageAndAccessPolicy property of the Instance
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:usageAndAccessPolicy ?y       |
      | ?y a bf:UsageAndAccessPolicy        |
      | ?y rdfs:label "Restricted access; Written permission required; Donor; Members of donor's family; Title 50, chapter 401, U.S.C. Preview only" |
    Then I should find 1 match

  Scenario: $u creates an rdfs:label property of the UsageAndAccessPolicy
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:usageAndAccessPolicy ?y       |
      | ?y a bf:UsageAndAccessPolicy        |
      | ?y rdfs:label "https://www.gnu.org/licenses/gpl-3.0.txt"^^xsd:anyURI |
    Then I should find 1 match

  Scenario: $3 creates a bflc:appliesTo property of the UsageAndAccessPolicy
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:usageAndAccessPolicy ?y       |
      | ?y a bf:UsageAndAccessPolicy        |
      | ?y bflc:appliesTo ?z                |
      | ?z a bflc:AppliesTo                 |
      | ?z rdfs:label "Office files of Under Secretary" |
    Then I should find 1 match

  Scenario: $5 creates a bflc:applicableInstitution property of the UsageAndAccessPolicy
    When I search with patterns:
      | ?x a bf:Instance                    |
      | ?x bf:usageAndAccessPolicy ?y       |
      | ?y a bf:UsageAndAccessPolicy        |
      | ?y bflc:applicableInstitution ?z    |
      | ?z a bf:Agent                       |
      | ?z bf:code "MH"                     |
    Then I should find 1 match
