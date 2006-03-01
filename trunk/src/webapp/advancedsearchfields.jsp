<fieldset>
<legend>Didactische functie</legend>
<div class="notes">
  <p class="last">Zoekt leerobjecten bestemd voor een bepaalde didactische functie of toepassing</p>
</div>
<div class="optional">
  <label for="didacticFunction">Didactische functie:</label>
  <select name="didacticFunction" id="didacticFunction" class="selectOne">
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
  <label for="productType">Product type:</label>
  <select multiple size="3" name="productType" id="productType" class="selectMultiple">
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
  <label for="professionalSituation">Vakleergebied:</label>
  <select multiple size="10" name="professionalSituation" id="professionalSituation" class="selectMultiple">
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
  <label for="competence">Competentie</label>
  <select multiple size="3" name="competence" id="competence" class="selectMultiple">
    <% out.print(org.metaz.gui.portal.SelectOptionList.toHtml(searchBean.getCompetenceOptions())); %>
  </select>
  <small>Gebruik de <kbd>CTRL</kbd> toets om meerdere opties te kiezen </small>
</div>
</fieldset>
