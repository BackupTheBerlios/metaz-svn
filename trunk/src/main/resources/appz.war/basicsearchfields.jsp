<fieldset>
<legend>Rol</legend>
<div class="notes">
	<h4>Wat doet dit?</h4>
	<p class="last">Beperkt het zoekresultaat tot leerobjecten die bestemd zijn voor een bepaalde rol</p>
</div>
<div class="optional">
	<label for="role">Ik ben een:</label>
	<select name="role" id="role" class="selectOne">
		<option value="A">Alles</option>
		<option value="D">Docent</option>
		<option value="B">Begeleider</option>
		<option value="A">Manager</option>
	</select>
</div>
</fieldset>
<fieldset>
<legend>Schooltype</legend>
<div class="notes">
	<p class="last">Selecteer leerobjecten voor een bepaald schooltype</p>
</div>
<div class="optional">
<label for="schooltype">Mijn schooltype:</label>
<select name="schooltype" id="schooltype" class="selectOne">
	<option value="AL">Alles</option>
	<option value="VM">VBMO</option>
	<option value="HA">HAVO</option>
	<option value="VW">VWO</option>
</select>
</fieldset>
<fieldset>
<legend>Vakleergebied</legend>
<div class="notes">
	<p class="last">Het resultaat kan worden beperkt tot een bepaald vakleergebied.<br>
Vakleergebieden zijn afhankelijk van schooltypes</p>
</div>
<div class="optional">
<label for="schooltype">Vakleergebied:</label>
<select name="schooltype" id="schooltype" class="selectOne">
	<option value="AL">Alles</option>
	<option value="RE">Rekenen</option>
	<option value="LE">Lezen</option>
	<option value="SC">Schrijven</option>
</select>
</fieldset>
<fieldset>
<legend>Trefwoorden</legend>
<div class="notes">
	<p class="last">Zoekt naar specifieke trefwoorden</p>
</div>
<div class="optional">
	<label for="free">Trefwoorden:</label>
	<input type="text" name="free" id="free" class="inputText" size="10" maxlength="100" value="" />
	<small>Spaties scheiden trefwoorden</small> </div>
</fieldset>
<fieldset>
<div class="submit">
	<input type="submit" class="inputSubmit" value="Nu zoeken &raquo;" />
</div>
</fieldset>
