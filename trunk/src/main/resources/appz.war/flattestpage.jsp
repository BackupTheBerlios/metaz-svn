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
<body background="images/binocs.jpg">
<table width="100%" border="0" cellspacing="0">
  <tr>
    <td bgcolor="#FF0000"><center>
        <br/>
        <ul id="tablist">
          <li><a class="current" href="simple.jsp" title="Zoeken naar leerobjecten">Eenvoudig zoeken</a></li>
          <li><a href="advanced.jsp" title="Uitgebreid zoeken naar leerobjecten">Uitgebreid zoeken</a></li>
          <li><a href="help.jsp" title="Help">Help</a></li>
          <li><a href="info.jsp" title="Informatie over deze portal en het Rdmc">Informatie</a></li>
          <li><a href="http://www.google.nl" title="Naar google">Google</a></li>
        </ul>
      </center></td>
  </tr>
</table>
<br/>
<table width="100%" border="0" cellspacing="0">
  <tr>
    <td width="10%">&nbsp;</td>
    <td><table width="600px" border="0" align="center" cellspacing="0">
        <tr>
          <td><form action="/code/forms/" method="post" enctype="multipart/form-data">
              <fieldset>
              <legend>Persoonlijke informatie </legend>
              <div class="notes">
                <h4>Persoonlijke<br>
                  Informatie</h4>
                Vul de informatie in zoals het op uw bankafschrift voorkomt </div>
              <div class="required">
                <label for="first_name">Voornaam:</label>
                <input type="text" name="first_name" id="first_name" class="inputText" size="10" maxlength="100" value="" />
              </div>
              <div class="required">
                <label for="last_name">Achternaam:</label>
                <input type="text" name="last_name" id="last_name" class="inputText" size="10" maxlength="100" value="" />
              </div>
              <div class="optional">
                <label for="address_1">Addres:</label>
                <input type="text" name="address_1" id="address_1" class="inputText" size="10" maxlength="100" value="" />
                <input type="text" name="address_2" id="address_2" class="inputText" size="10" maxlength="100" value="" />
              </div>
              <div class="optional">
                <label for="city">Stad:</label>
                <input type="text" name="city" id="city" class="inputText" size="10" maxlength="100" value="" />
              </div>
              <div class="optional">
                <label for="state">Provincie:</label>
                <select name="state" id="state" class="selectOne">
                  <option value="NULL">Kies provincie</option>
                  <option value="ZH">Zuid Holland</option>
                  <option value="UT">Utrecht</option>
                  <option value="LI">Limburg</option>
                  <option value="XX">Anders</option>
                </select>
              </div>
              <div class="optional">
                <label for="postal">Post code:</label>
                <input type="text" name="postal" id="postal" class="inputText" size="10" maxlength="50" value="" />
              </div>
              <div class="optional">
                <label for="country">Land:</label>
                <select name="country" id="country" class="selectOne">
                  <option value="NULL">Kies land</option>
                  <option value="NL" selected="selected">Nederland</option>
                  <option value="BE">Belgie</option>
                </select>
              </div>
              </fieldset>
              <fieldset>
              <legend>Contact Informatie</legend>
              <div class="notes">
                <h4>Contact Info</h4>
                <p>Voer volledig email adres in, bijvoorbeeld, <strong>name@domain.com</strong></p>
                <p>Het is belangrijk dat een correct adres wordt ingegeven</p>
              </div>
              <div class="optional">
                <div class="subfieldset">
                  <fieldset>
                  <h3>Wijze van communicatie</h3>
                  <label for="how_contact_phone" class="labelRadio compact">
                  <input type="radio" name="how_contact" id="how_contact_phone" class="inputRadio" value="Telefoon" />
                  Phone</label>
                  <label for="how_contact_email" class="labelRadio compact">
                  <input type="radio" name="how_contact" id="how_contact_email" class="inputRadio" value="Email" checked="checked" />
                  Email</label>
                  </fieldset>
                </div>
              </div>
              <div class="required">
                <label for="email">Email:</label>
                <input type="text" name="email" id="email" class="inputText" size="10" maxlength="250" value="" />
                <small>Als uw account is aangemaakt kunt u meer email adresssen invoeren</small> </div>
              <div class="required">
                <label for="confirm_email">Nogmaals invoeren SVP:</label>
                <input type="text" name="confirm_email" id="confirm_email" class="inputText" size="10" maxlength="250" value="" />
                <small>Beide invoervelden moeten overeenkomen.</small> </div>
              <div class="optional">
                <label for="phone">Telefoon:</label>
                <input type="text" name="phone" id="phone" class="inputText" size="10" maxlength="50" value="" />
              </div>
              <div class="optional">
                <label for="fax">Fax:</label>
                <input type="text" name="fax" id="fax" class="inputText" size="10" maxlength="50" value="" />
                <small>Uw persoonlijke informatie wordt niet aan derden ter beschikking gesteld </small> </div>
              <div class="optional">
                <div class="subfieldset">
                  <fieldset>
                  <h3>Bericht onderwerp :</h3>
                  <label for="subject_help" class="labelRadio">
                  <input type="radio" name="message_subject" id="subject_help" class="inputRadio" value="Help, my brother/sister is driving me crazy!" />
                  Help, mijn computer doet het niet meer!</label>
                  <label for="subject_retire" class="labelRadio">
                  <input type="radio" name="message_subject" id="subject_retire" class="inputRadio" value="How can I tell my father/mother, it's time for them to retire?" />
                  Ik heb een virus </label>
                  <label for="subject_partner" class="labelRadio">
                  <input type="radio" name="message_subject" id="subject_partner" class="inputRadio" value="I'm exasperated with an awkward partner!" />
                  Ik heb spyware op mijn computer </label>
                  <label for="subject_interfere" class="labelRadio">
                  <input type="radio" name="message_subject" id="subject_interfere" class="inputRadio" value="How do I stop my family members from interfering?" />
                  Ik snap het niet meer </label>
                  </fieldset>
                </div>
              </div>
              <div class="required">
                <label for="note_narrow">Uw bericht :</label>
                <textarea name="note_narrow" id="note_narrow" class="inputTextarea" rows="10" cols="21"></textarea>
                <small>Maximaal  250 tekens invoeren </small> </div>
              <div class="optional">
                <label for="image">Bestand  :</label>
                <input type="file" name="image" id="image" class="inputFile" />
                <small>Een bestand invoegen... </small> </div>
              <div class="required">
                <label for="availability_select">Werktijden : </label>
                <select name="availability_select" id="availability_select" class="selectMultiple" size="5" multiple="multiple">
                  <option value="Parttime">Part-tijd</option>
                  <option value="Fulltime">Volledig</option>
                  <option value="Sometimes">Soms</option>
                  <option value="Often">Vaak</option>
                  <option value="Weekends">Weekends</option>
                </select>
                <small>Gebruik de <kbd>CTRL</kbd> toets om meerdere opties te kiezen </small> </div>
              <div class="required">
                <div class="subfieldset">
                  <fieldset>
                  <h3>Beschikbaarheid :</h3>
                  <label for="availability_checkbox_0" class="labelCheckbox">
                  <input type="checkbox" name="availability_checkbox" id="availability_checkbox_0" class="inputCheckbox" value="Parttime" />
                  Part-tijd</label>
                  <label for="availability_checkbox_1" class="labelCheckbox">
                  <input type="checkbox" name="availability_checkbox" id="availability_checkbox_1" class="inputCheckbox" value="Fulltime" />
                  Volledig </label>
                  <label for="availability_checkbox_2" class="labelCheckbox">
                  <input type="checkbox" name="availability_checkbox" id="availability_checkbox_2" class="inputCheckbox" value="Sometimes" />
                  Soms</label>
                  <label for="availability_checkbox_3" class="labelCheckbox">
                  <input type="checkbox" name="availability_checkbox" id="availability_checkbox_3" class="inputCheckbox" value="Often" />
                  Vaak</label>
                  <label for="availability_checkbox_4" class="labelCheckbox">
                  <input type="checkbox" name="availability_checkbox" id="availability_checkbox_4" class="inputCheckbox" value="Weekends" />
                  Weekends</label>
                  </fieldset>
                </div>
              </div>
              </fieldset>
              <fieldset>
              <legend>Login Informatie</legend>
              <div class="notes">
                <h4>Login Informatie</h4>
                <p>Onthoud uw wachtwoord goed!</p>
              </div>
              <div class="required">
                <label for="username">Username:</label>
                <input type="text" name="username" id="username" class="inputText" size="10" maxlength="20" value="" />
                <small>Letters en cijfers </small> </div>
              <div class="required">
                <label for="password">Wachtwoord:</label>
                <input type="password" name="password" id="password" class="inputPassword" size="10" maxlength="25" value="" />
                <small>Moet 8-12 tekens lang zijn </small> </div>
              <div class="required">
                <label for="confirm_password">Nogmaals invoeren :</label>
                <input type="password" name="confirm_password" id="confirm_password" class="inputPassword" size="10" maxlength="25" value="" />
                <small>Beide invoervelden moeten overeenstemmen.</small> </div>
              <div class="optional">
                <label for="remember" class="labelCheckbox">
                <input type="checkbox" name="remember" id="remember" class="inputCheckbox" value="1" />
                Onthoud mij </label>
                <small>Selecteer deze optie als u niet steeds opnieuw wilt aanloggen </small> </div>
              </fieldset>
              <fieldset>
              <div class="submit">
                <input type="submit" class="inputSubmit" value="Submit &raquo;" />
                <input type="submit" class="inputSubmit" value="Cancel" />
              </div>
              </fieldset>
            </form></td>
        </tr>
      </table></td>
    <td width="10%">&nbsp;</td>
  </tr>
</table>
</body>
</html>
