# Chapter 11: Exceptions and Localization

**Handling Exceptions**: Handle exception using try/cath/finally, 
try-with-resources, and multi-catch blocks, including exceptions

**Implementing Localization**: implement localization 
using locales, resource bundles, parse and format messages, dates, times, and numbers 
including currency and percentage values.


This chapter is about creating applications to adapt to change. What happens if a user 
enter invalid data on a web page? What if out connection to a database goes down in the 
middle of a sale? Finally, how do we build applications that can support multiple 
language or geographic regions?


In this chapter, we discuss these problems and solutions to them using exceptions, 
formatting, and localization. One way to make sure our applications respond to change 
is to build in support early on. For example, supporting localization doesn't mean we 
actually need to support specific languages right away. It just means our application 
can be more easily adapted in the future. This chapter provides structure for designing 
application that better adapt to change.

[Understanding Exceptions](#understanding-exceptions)

[Recognizing Exception Classe](#recognizing-exception-classes)

[Handling Exceptions](#handling-exceptions)

[Automating Resource Management](#automating-resource-management)

[Formatting Values](#formatting-values)

[Supporting Internalization and Localization](#support-internalization-localization)

[Loading Properties with Resource Bundles](#loading-properties-resource-bundles)

[Summary](#summary)


## Understanding Exceptions