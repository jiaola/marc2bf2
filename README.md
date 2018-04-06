marc2bf2 - MARC to BibFrame Converter
 
marc2bf2 is a Java based tool for converting MARC records to [BibFrame2](https://www.loc.gov/bibframe/docs/bibframe2-model.html). 
It's a java port of the Library of Congress XSLT based conversion tool [marc2bibframe2](https://github.com/lcnetdev/marc2bibframe2). 

[![Build Status](https://travis-ci.com/jiaola/marc2bf2.svg?token=PecqmC3tkGmvJLxUwkq6&branch=master)](https://travis-ci.com/jiaola/marc2bf2)

## Differences

These are the differences from the LOC marc2bibframe2 tool 

* In 008 converter, the duration is converted to the xsd:duration format: PXXXM instead of XXX.
* in 006/008 converter, the labels are capitalized for some nodes. This is because the values are taken directly from 
the vocabulary. And in the vocabularies, the preferred labels are capitalized. For example, see io.lold.marc2bf2.features.006_008.field006.feature

