<%@ include file="includes.jsp" %>

<portal:SetContent name="ActiveTab">
  <% out.print(org.metaz.gui.portal.PortalTabTag.TAB_INFO); %>
</portal:SetContent>
<portal:SetContent name="Background">images/binocs.jpg</portal:SetContent>
<portal:SetContent name="PageContent"><p>Bij het <b>Ruud de Moorcentrum</b> (RdMC) worden ten behoeve van zij-instromers, beginnende leraren en hun begeleiders allerlei producten ontwikkeld om hen te ondersteunen in hun werk en hun opleidings- en begeleidingsactiviteiten op de werkplek. Voorbeelden van producten zijn kennisbanken (voor zowel vakinhouden als didactische vraagstukken), assessmentinstrumenten en communities of practice. Deze producten (ook wel <b>"leerobjecten"</b> genoemd) worden zoveel mogelijk via het internet ter beschikking gesteld.</p><br />

<p><b>Applicatie Z</b> is een zoekapplicatie die u in staat stelt om op <b>gestructueerde</b> wijze te zoeken naar specifieke (voor u geschikte) leerobjecten van het Ruud de Moor Centrum.</p><br />

<p>Het zoekalgoritme van Applicatie Z is primair gebaseerd op het doorzoeken van <b>metadata</b>. De metadata is gespecificeerd door de eigenaar of de aanbrenger van het leerobject. Met metadata bedoelen we allerlei classificaties van de leerobjecten. U kunt denken aan gegevens als titel en omschrijving, maar ook bijvoorbeeld de didactische positionering (zoals schooltype of vakleergebied) of de beoogde doelgroep (bijvoorbeeld begeleiders, docenten of managers). Daarnaast kan elk leerobject trefwoorden bevatten waardoor gezocht kan worden.</p><br />

<p>Sommige metadatavelden zijn <b>hi�rarchies</b> van aard. Een voorbeeld is het schooltype. Een schooltype kan �voortgezet onderwijs� zijn. Dat is het eerste niveau. Het tweede niveau kan bijvoorbeeld �voorbereidend beroepsonderwijs� zijn, en het derde niveau dan weer �VMBO�. Als u zoekt naar een bepaald <i>hoger</i> gelegen niveau, dan ontsluit u automatisch ook alle <i>lager</i> gelegen niveaus.</p><br />

<p>Door het gebruik van metadata is het mogelijk om <b>zeer gericht</b> te zoeken naar geschikte leerobjecten. Voor de duidelijkheid: het zoeken op basis van metadata is dus afwijkend van het doorzoeken van de volledige tekst van een leerobject. Met Applicatie Z doorzoekt u de toegevoegde metadata van het leerobject, en dus niet de tekst. U zult merken dat u met deze techniek doorgaans zeer snel relevante leerobjecten kunt vinden! Overigens zal Applicatie Z mogelijk in een toekomstige versie aanvullend zogenaamde "full text search" functionaliteit aanbieden.</p><br />

<p>Een zoekvraag bestaat uit een combinatie van waarden voor metadata-elementen. Applicatie Z doorzoekt op basis van uw vraagstelling in de onderliggende systemen van het RdMC, en presenteert het antwoord van de systemen aan de gebruiker. Een antwoord van applicatie Z is een verzameling van <b>informatie en links</b> naar de producten waarvan de metadata-elementwaarden voldoen aan die van de zoekvraag.</p><br />

<p>Meer informatie kunt u onder het tabblad <b>help</b> vinden.</p><br /></portal:SetContent>
<%@ include file="template.jsp" %>
