<fieldset>
<legend>Rol</legend>
<div class="notes">
  <h4>Wat doet dit?</h4>
  <p class="last">Beperkt het zoekresultaat tot leerobjecten die bestemd zijn voor een bepaalde rol</p>
</div>
<div class="optional">
  <label for="targetEndUser">Ik ben een:</label>
  <select name="targetEndUser" id="targetEndUser" class="selectOne">
    <% out.print(org.metaz.gui.portal.SelectOptionList.toHtml(searchBean.getTargetEndUserOptions())); %>
  </select>
</div>
</fieldset>
<fieldset>
<legend>Schooltype</legend>
<div class="notes">
  <p class="last">Selecteer leerobjecten voor een bepaald schooltype</p>
</div>
<div class="optional">
  <label for="schoolType">Mijn schooltype:</label>
  <select name="schoolType" id="schoolType" class="selectOne">
    <% out.print(org.metaz.gui.portal.SelectOptionList.toHtml(searchBean.getSchoolTypeOptions())); %>
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
  <label for="schoolDiscipline">Vakleergebied:</label>
  <select name="schoolDiscipline" id="schoolDiscipline" class="selectOne">
    <% out.print(org.metaz.gui.portal.SelectOptionList.toHtml(searchBean.getSchoolDisciplineOptions())); %>
  </select>
</div>
</fieldset>
