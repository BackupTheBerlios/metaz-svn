<fieldset>
	<legend>Rol</legend>
	<div class="notes">
		<h4>Wat doet dit?</h4>
		<p class="last">Beperkt het zoekresultaat tot leerobjecten die bestemd zijn voor een bepaalde rol</p>
	</div>
	<div class="optional">
		<label for="role">Ik ben een:</label>
		<select name="role" id="role" class="selectOne">
			<option value="*">Alles</option>
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
			<option value="*">Alles</option>
			<option value="V">VBMO</option>
			<option value="H">HAVO</option>
			<option value="V">VWO</option>
		</select>
	</div>
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
			<option value="*">Alles</option>
			<option value="R">Rekenen</option>
			<option value="L">Lezen</option>
			<option value="S">Schrijven</option>
		</select>
	</div>
</fieldset>
