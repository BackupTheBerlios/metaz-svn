<fieldset>
<legend>Rol</legend>
<div class="notes">
  <h4>Wat doet dit?</h4>
  <p class="last">Beperkt het zoekresultaat tot leerobjecten die bestemd zijn voor een bepaalde rol</p>
</div>
<div class="optional">
  <label for="<%= org.metaz.domain.MetaData.TARGETENDUSER %>">Ik ben een:</label>
  <select name="<%= org.metaz.domain.MetaData.TARGETENDUSER %>" id="<%= org.metaz.domain.MetaData.TARGETENDUSER %>" class="selectOne">
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
  <label for="<%= org.metaz.domain.MetaData.SCHOOLTYPE %>">Mijn schooltype:</label>
  <select name="<%= org.metaz.domain.MetaData.SCHOOLTYPE %>" id="<%= org.metaz.domain.MetaData.SCHOOLTYPE %>" class="selectOne" onchange="javascript:update()">
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
  <label for="<%= org.metaz.domain.MetaData.SCHOOLDISCIPLINE %>">Vakleergebied:</label>
  <select name="<%= org.metaz.domain.MetaData.SCHOOLDISCIPLINE %>" id="<%= org.metaz.domain.MetaData.SCHOOLDISCIPLINE %>" class="selectOne">
    <% out.print(org.metaz.gui.portal.SelectOptionList.toHtml(searchBean.getSchoolDisciplineOptions())); %>
  </select>
</div>
</fieldset>
