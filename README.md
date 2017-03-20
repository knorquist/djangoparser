# djangoparser
Utility for testing if a website has used Django

## Premise

If you're familiar with web development, you know that it's actually impossible to know for sure what framework
a website was built with. However, there are some tell-tale signs and little things developers in a rush can
sometimes forget, which this tool takes advantage of.

## How It Works

This tool grabs viewable HTML from the URL provided and looks for keywords, nothing fancy. It does also check
if there is a viewable admin page at the default URL (www.whateverURLBeingChecked.com/admin) and also checks
that page.

## How To Use

Django Parser is currently a command line tool. Ensure you have Java and Maven. Download the src directory and 
POM file, and then build using Maven:
"mvn package"

Finally, execute with:
"java -jar yourDjangoParserJarFile.jar http://www.whateverURLBeingChecked.com"

Be sure to include the http:// or https:// prefix, or the tool will not work.
