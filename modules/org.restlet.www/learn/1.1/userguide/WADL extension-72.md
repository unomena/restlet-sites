WADL extension
==============

Introduction
============

Description
===========

Indicate schema relative to an XML representation
-------------------------------------------------

#### A bit of theory

Quoting the WADL specification:

    The "grammars" element acts as a container for definitions of the format of data
    exchanged during execution of the protocol described by the WADL document. Such
    definitions may be included inline or by reference using the include element.

For example:

    <grammars>
      <include href="NewsSearchResponse.xsd"/>
      <include href="Error.xsd"/>
    </grammars>

NB: at this time, the WADL extension of the Restlet framework supports
only "included" and not "inline" schemas via the GrammarsInfo\#includes
attribute.

Then, for XML-based representations, the "element" attribute specifies
the qualified name of the root element as described within the grammars
section.

For example:

    <representation mediaType="application/xml" element="yn:ResultSet"/>

Assuming that the "yn" namespace is declared in the document:

    <application [...] xmlns:yn="urn:yahoo:yn" >

#### Implementation with Restlet

At the level of the subclass of WadlApplication, override the
getApplicationInfo method:

    @Override
    public ApplicationInfo getApplicationInfo(Request request, Response response) {
        ApplicationInfo appInfo = super.getApplicationInfo(request, response);
        appInfo.getNamespaces().put("urn:yahoo:yn", "yn");
        GrammarsInfo grammar = new GrammarsInfo();
        IncludeInfo include = new IncludeInfo();
        include.setTargetRef(new Reference("NewsSearchResponse.xsd"));
        grammar.getIncludes().add(include);
        appInfo.setGrammars(grammar);
        return appInfo;
    }

Then, at the level of the subclass of WadlResource, update the
RepresentationInfo\#element attribute:

    RepresentationInfo formRepresentation = new RepresentationInfo();
    formRepresentation.setXmlElement("yn:ResultSet");

[Comments
(0)](http://web.archive.org/web/20091124075912/http://wiki.restlet.org/docs_1.1/13-restlet/28-restlet/72-restlet.html#)

Comments
[Hide](http://web.archive.org/web/20091124075912/http://wiki.restlet.org/docs_1.1/13-restlet/28-restlet/72-restlet.html#)
\
There are no comments.

Add a comment

Please log in to be able to add comments.
