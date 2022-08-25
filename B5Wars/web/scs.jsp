<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%
    String mode  = request.getParameter("mode");
    String name  = request.getParameter("name");
    String model = request.getParameter("model");
    String versn = request.getParameter("version");
    
    int version = 0;
    if ((versn != null) && (versn.length() > 0)) {
        try {
        	version = Integer.parseInt(versn);
        } catch (Exception e) {
        	version = 0;
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title><%= name %><%= (model != null) ? " (" + model + ")" : "" %><%= (version > 0) ? " " + version : "" %></title>
    <meta charset="utf-8">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">    
    <link rel="stylesheet" type="text/css" href="css/b5wars.css" />    
    <link rel="stylesheet" type="text/css" href="css/codemirror.css" />    
    <link rel="stylesheet" type="text/css" href="css/codemirror-theme.css" />    
    <script type="text/javascript" src="js/b5wars.js"></script>
    <script type="text/javascript" src="js/b5wars-scs.js"></script>
    <script type='text/javascript' src='dwr/interface/b5wars.js'></script>
    <script type='text/javascript' src='dwr/engine.js'></script>    
    <script type="text/javascript" src="js/codemirror.js"></script>
    <script type="text/javascript" src="js/codemirror-xml.js"></script>
    <script type="text/javascript" src="js/raphael.js"></script>
</head>
<body class="scs" onload="scs_OnLoad('<%= mode %>', '<%= name %>', <%= (model != null) ? "'" + model + "'" : "null" %>, <%= version %>)">

    <table>
        <tr>
            <td>
                <div id="editor" class="editor">
                </div>
            </td>
            <td valign="middle">
                <input type="button" value="SCS ->" title="Display SCS from XML" onclick="scs_XMLToSCSClick();">
            </td>
            <td>
                <div id="scs" class="scs">
                </div>
            </td>
            <td valign="middle">
                <input type="button" value="PDF ->" title="Generate PDF of SCS" onclick="scs_XMLToPDFClick();">
            </td>
	    </tr>
    </table>
    
</body>
</html>
