<html>
<head>
<title>Ruud de Moor Centrum - Applicatie Z</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta name="copyright" content="&#169; Copyright 2006 team Meta/Z, Open Universiteit Nederland" />
<meta name="author" content="Team Meta/Z, Open Universiteit Nederland" />
<meta name="ROBOTS" content="INDEX, FOLLOW" />
<meta name="resource-type" content="document" />
<meta name="distribution" content="Global" />
<meta name="robots" content="All" />
<meta name="rating" content="General" />
<meta name="revisit-after" content="1 days" />
<meta http-equiv="imagetoolbar" content="no" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<style type="text/css">
	@import "stylesheets/main.css";
	@import "stylesheets/tabs.css";
	@import "stylesheets/form.css";
</style>
</head>
<body>
<table width="100%" border="0" cellspacing="0">
  <tr>
    <td bgcolor="#CC0000"><center>
        <br/>
        <ul id="tablist">
          <li><a class="current" href="http://www.cssdrive.com">Eenvoudig zoeken</a></li>
          <li><a href="http://www.yahoo.com">Uitgebreid zoeken</a></li>
          <li><a href="http://www.google.com">Help</a></li>
          <li><a href="http://www.google.com">Google</a></li>
        </ul>
      </center></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0">
  <tr>
    <td width="10%" bordercolor="#CCCCFF" bgcolor="#FFFFFF">&nbsp;</td>
    <td bordercolor="#CCCCFF" bgcolor="#FFFFFF"><table width="600px" border="0" align="center" cellspacing="0" bordercolor="#FFFFFF" bgcolor="#FFFFFF">
        <tr>
          <td><br/>
            <form action="/code/css/forms/" method="post" enctype="multipart/form-data">
              <br/>
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
            </form></td>
        </tr>
      </table></td>
    <td width="10%" bordercolor="#CCCCFF" bgcolor="#FFFFFF">&nbsp;</td>
  </tr>
</table>
</body>
</html>
