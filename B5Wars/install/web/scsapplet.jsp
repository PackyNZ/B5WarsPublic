<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%
	String mode    = request.getParameter("mode");
	String name    = request.getParameter("name");
	String model   = request.getParameter("model");
	String version = request.getParameter("version");
%>

<html>
<head>
	<title>B5Wars SCS <%= ("ALL".equalsIgnoreCase(mode) || "EDIT".equalsIgnoreCase(mode)) ? "Editor" : "Viewer" %></title>
    <meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="css/b5wars.css" />
</head>
<body class="applet">
    <script src="http://www.java.com/js/deployJava.js"></script>
    <script> 
        var attributes = { 
                code:'net.b5gamer.b5wars.ui.scs.SCSApplet',  
                width:1024, height:768}; 
        var parameters = {jnlp_href: 'scsapplet.jnlp', mode: '<%= mode %>', name: '<%= name %>', model: '<%= (model != null) ? model : "" %>', version: '<%= (version != null) ? version : "0" %>'}; 
        deployJava.runApplet(attributes, parameters, '1.5'); 
    </script>
</body>
</html>
