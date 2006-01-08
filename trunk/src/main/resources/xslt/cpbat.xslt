<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="text"/>
  
  <xsl:template match="/">
    <xsl:text>SET CLASSPATH=%CLASSPATH%;</xsl:text>
    <xsl:apply-templates select="project/dependencies/dependency"/>
  </xsl:template>

  <xsl:template match="dependency">
    <xsl:text>%MAVEN_HOME_LOCAL%\repository\</xsl:text>
	<xsl:value-of select="artifactId"/>
	<xsl:text>\jars\</xsl:text>
    <xsl:value-of select="artifactId"/>
    <xsl:value-of select="version"/>
    <xsl:text>.jar;</xsl:text>
  </xsl:template>
  
</xsl:stylesheet>