marc2bf2 - MARC to BibFrame Converter
 
marc2bf2 is a Java based tool for converting MARC records to [BibFrame2](https://www.loc.gov/bibframe/docs/bibframe2-model.html). 

[![Build Status](https://travis-ci.com/jiaola/marc2bf2.svg?token=PecqmC3tkGmvJLxUwkq6&branch=master)](https://travis-ci.com/jiaola/marc2bf2)

Progress:

(*)    1963 ConvSpec-001-007.xsl  
(*)    1600 ConvSpec-006,008.xsl
(*)    1203 ConvSpec-010-048.xsl
(*)     546 ConvSpec-050-088.xsl
(*)     885 ConvSpec-1XX,6XX,7XX,8XX-names.xsl
(*)     889 ConvSpec-200-247not240-Titles.xsl
(*)     704 ConvSpec-240andX30-UnifTitle.xsl
(*)     572 ConvSpec-250-270.xsl
(*)     917 ConvSpec-3XX.xsl
     745 ConvSpec-490-510-530to535-Links.xsl
     898 ConvSpec-5XX.xsl
     458 ConvSpec-648-662.xsl
     135 ConvSpec-720+740to755.xsl
     413 ConvSpec-760-788-Links.xsl
     222 ConvSpec-841-887.xsl
     509 ConvSpec-880.xsl
(*)     172 ConvSpec-ControlSubfields.xsl
(*)     164 ConvSpec-LDR.xsl
wc: conf: read: Is a directory
     186 marc2bibframe2.xsl
     205 naco-normalize.xsl
(*)     305 utils.xsl


XSPEC files: 
(*)     157 test/ConvSpec-001-007.xspec
(*)      85 test/ConvSpec-006,008.xspec
(*)     225 test/ConvSpec-010-048.xspec
(*)     120 test/ConvSpec-050-088.xspec
(*)      43 test/ConvSpec-1XX,6XX,7XX,8XX-names.xspec
(*)     242 test/ConvSpec-200-247not240-Titles.xspec
(*)      56 test/ConvSpec-240andX30-UnifTitle.xspec
     104 test/ConvSpec-250-270.xspec
(*)     219 test/ConvSpec-3XX.xspec
      97 test/ConvSpec-490-510-530to535-Links.xspec
     276 test/ConvSpec-5XX.xspec
      68 test/ConvSpec-648-662.xspec
      39 test/ConvSpec-720+740to755.xspec
      63 test/ConvSpec-760-788-Links.xspec
      42 test/ConvSpec-841-887.xspec
     122 test/ConvSpec-880.xspec
(*)      24 test/ConvSpec-LDR.xspec
     798 test/marc2bibframe2.xspec
    2780 total
## Differences

These are the differences from the LOC marc2bibframe2 tool 

* In 008 converter, the duration is converted to the xsd:duration format: PXXXM instead of XXX.
* in 006/008 converter, the labels are capitalized for some nodes. This is because the values are taken directly from 
the vocabulary. And in the vocabularies, the preferred labels are capitalized. For example, see io.lold.marc2bf2.features.006_008.field006.feature

