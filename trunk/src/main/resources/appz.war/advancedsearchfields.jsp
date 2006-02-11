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
		<p class="last">Beperk het resultaat tot een bepaald type leerobject (video, audio, etc)</p>
	</div>
	<div class="optional">
		<label for="productType">Product type:</label>
		<select name="productType" id="productType" class="selectOne">
<% out.print(org.metaz.gui.portal.SelectOptionList.toHtml(searchBean.getProductTypeOptions())); %>
		</select>
	</div>
</fieldset>
<fieldset>
	<legend>Beroepssituatie</legend>
	<div class="notes">
		<p class="last">Zoek leerobjecten die relevant zijn voor uw specifieke situatie</p>
	</div>
	<div class="optional">
		<label for="professionalSituation">Vakleergebied:</label>
		<select name="professionalSituation" id="professionalSituation" class="selectOne">
<% out.print(org.metaz.gui.portal.SelectOptionList.toHtml(searchBean.getProfessionalSituationOptions())); %>
		</select>
	</div>
</fieldset>
<fieldset>
	<legend>Competentie</legend>
	<div class="notes">
		<p class="last">Competentie gericht zoeken</p>
	</div>
	<div class="optional">
		<label for="competence">Competentie</label>
		<select name="competence" id="competence" class="selectOne">
<% out.print(org.metaz.gui.portal.SelectOptionList.toHtml(searchBean.getCompetenceOptions())); %>
		</select>
	</div>
</fieldset>
