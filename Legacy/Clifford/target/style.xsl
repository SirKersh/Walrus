<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text" encoding="iso-8859-1"/>

<xsl:template match="/*">
<xsl:for-each select="*[1]/*">
<xsl:text>"</xsl:text>
<xsl:value-of select="name()"/>
<xsl:text>"</xsl:text>
<xsl:if test="position() != last()">, </xsl:if>
<xsl:if test="position() = last()">
<xsl:text>&#xD;</xsl:text>
</xsl:if>
</xsl:for-each>
<xsl:apply-templates/>
</xsl:template>


<xsl:strip-space elements="*" />
<xsl:template match="/*/child::*">
<xsl:for-each select="child::*">
<xsl:if test="position() != last()">"<xsl:value-of select="normalize-space(.)"/>",    </xsl:if>
<xsl:if test="position()  = last()">"<xsl:value-of select="normalize-space(.)"/>"<xsl:text>&#xD;</xsl:text>
</xsl:if>
</xsl:for-each>
</xsl:template>
</xsl:stylesheet>