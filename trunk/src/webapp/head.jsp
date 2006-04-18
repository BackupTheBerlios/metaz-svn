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
	@import "stylesheets/displaytag.css";
</style>

  <script type='text/javascript'
    src='/metaz/dwr/engine.js'>
</script>
<script type="text/javascript"
    src="/metaz/dwr/interface/SearchBean.js"> </script>
<script type='text/javascript'
    src='/metaz/js/util.js'>
</script>
<script type="text/javascript">
<!--
 function update(){
 	var parameter = DWRUtil.getValue("<%= org.metaz.domain.MetaData.SCHOOLTYPE %>");
 	SearchBean.getSchoolDisplinesBySchoolType(parameter, createList);
 }
 
 function createList(data){
 	DWRUtil.removeAllOptions("<%= org.metaz.domain.MetaData.SCHOOLDISCIPLINE %>");
 	DWRUtil.addOptions("<%= org.metaz.domain.MetaData.SCHOOLDISCIPLINE %>", data, 'key', 'value');
 }
 
 	function selectTargetEndUser(){
	 	var parameter = DWRUtil.getValue("<%= org.metaz.domain.MetaData.TARGETENDUSER %>");
 		SearchBean.setSelectedTargetEndUserOption(parameter);
 	}

	function selectSchoolType(){
		var parameter = DWRUtil.getValue("<%= org.metaz.domain.MetaData.SCHOOLTYPE %>");
		SearchBean.setSelectedSchoolTypeOption(parameter);
	}
	
	function selectSchoolDiscipline(){
		var parameter = DWRUtil.getValue("<%= org.metaz.domain.MetaData.SCHOOLDISCIPLINE %>");
		SearchBean.setSelectedSchoolDisciplineOption(parameter);
	}
	
	function selectDidacticFunction(){
		var parameter = DWRUtil.getValue("<%= org.metaz.domain.MetaData.DIDACTICFUNCTION %>");
		SearchBean.setSelectedDidacticFunctionOption(parameter);
	}
	
	function selectProductType(){
		var parameter = DWRUtil.getValues("<%= org.metaz.domain.MetaData.PRODUCTTYPE %>");
		SearchBean.setSelectedProductTypeOption(parameter);
	}
	
	function selectProfessionalSituation(){
		var parameter = DWRUtil.getValues("<%= org.metaz.domain.MetaData.PROFESSIONALSITUATION %>");
		SearchBean.setSelectedProfessionalSituationOption(parameter);
	}
	
	function selectCompetence(){
		var parameter = DWRUtil.getValues("<%= org.metaz.domain.MetaData.COMPETENCE %>");
		SearchBean.setSelectedCompetenceOption(parameter);
	}
//-->
</script>
</head>
