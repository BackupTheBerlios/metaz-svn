<fieldset>
<legend>Didactische functie</legend>
<div class="notes">
  <p class="last">Zoekt leerobjecten bestemd voor een bepaalde didactische functie of toepassing</p>
</div>
<div class="optional">
  <label for="<%= org.metaz.domain.MetaData.DIDACTICFUNCTION %>">Didactische functie:</label>
  <select name="<%= org.metaz.domain.MetaData.DIDACTICFUNCTION %>" id="<%= org.metaz.domain.MetaData.DIDACTICFUNCTION %>" class="selectOne">
    <% out.print(org.metaz.gui.portal.SelectOptionList.toHtml(searchBean.getDidacticFunctionOptions())); %>
  </select>
</div>
</fieldset>
<fieldset>
<legend>Producttype</legend>
<div class="notes">
  <p class="last">Zoek naar bepaalde type media, zoals afbeeldingen, video of audio (meerdere keuzen mogelijk)</p>
</div>
<div class="optional">
  <label for="<%= org.metaz.domain.MetaData.PRODUCTTYPE %>">Product type:</label>
  <select multiple size="3" name="<%= org.metaz.domain.MetaData.PRODUCTTYPE %>" id="<%= org.metaz.domain.MetaData.PRODUCTTYPE %>" class="selectMultiple">
    <% out.print(org.metaz.gui.portal.SelectOptionList.toHtml(searchBean.getProductTypeOptions())); %>
  </select>
  <small>Gebruik de <kbd>CTRL</kbd> toets om meerdere opties te kiezen </small>
</div>
</fieldset>
<fieldset>
<legend>Beroepssituatie</legend>
<div class="notes">
  <p class="last">Zoek leerobjecten die relevant zijn voor uw specifieke situatie (meerdere keuzen mogelijk)</p>
</div>
<div class="optional">
  <label for="<%= org.metaz.domain.MetaData.PROFESSIONALSITUATION %>">Beroepssituatie:</label>
  <select multiple size="10" name="<%= org.metaz.domain.MetaData.PROFESSIONALSITUATION %>" id="<%= org.metaz.domain.MetaData.PROFESSIONALSITUATION %>" class="selectMultiple">
    <% out.print(org.metaz.gui.portal.SelectOptionList.toHtml(searchBean.getProfessionalSituationOptions())); %>
  </select>
  <small>Gebruik de <kbd>CTRL</kbd> toets om meerdere opties te kiezen </small>
</div>
</fieldset>
<fieldset>
<legend>Competentie</legend>
<div class="notes">
  <p class="last">Competentie gericht zoeken (meerdere keuzen mogelijk)</p>
</div>
<div class="optional">
  <label for="<%= org.metaz.domain.MetaData.COMPETENCE %>">Competentie</label>
  <select multiple size="3" name="<%= org.metaz.domain.MetaData.COMPETENCE %>" id="<%= org.metaz.domain.MetaData.COMPETENCE %>" class="selectMultiple">
    <% out.print(org.metaz.gui.portal.SelectOptionList.toHtml(searchBean.getCompetenceOptions())); %>
  </select>
  <small>Gebruik de <kbd>CTRL</kbd> toets om meerdere opties te kiezen </small>
</div>
</fieldset>
