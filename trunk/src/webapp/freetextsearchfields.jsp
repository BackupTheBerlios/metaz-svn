<fieldset>
<legend>Trefwoorden</legend>
<div class="notes">
  <p class="last">Zoekt naar specifieke trefwoorden</p>
</div>
<div class="optional">
  <label for="<%= org.metaz.domain.MetaData.KEYWORDS %>">Trefwoorden:</label>
  <input type="inputText" name="<%= org.metaz.domain.MetaData.KEYWORDS %>" id="<%= org.metaz.domain.MetaData.KEYWORDS %>" class="inputText" size="10" maxlength="100" value="<%=searchBean.getHtmlEscapedKeywords()%>" />
  <small>Spaties scheiden trefwoorden</small> </div>
</fieldset>
