// scs.jsp ------------------------------------------------------------------------------------
var scsMode;
var scs;
var scsXML;

function scs_OnLoad(mode, name, model, version) {
	scsMode = mode;

	scsXML = CodeMirror(document.getElementById("editor"), {
		  value: '<unit author="AOG" hull="Warbird" name="Drazi Warbird Cruiser" source="SF" version="2"><specs pointValue="480" rammingFactor="110" type="Heavy Combat Vessel"/></unit>',
		  mode:  "xml",
		  indentUnit: 4,
		  tabMode: "shift"
		});
		
	scs = new ShipControlSheet("scs", scsMode);
	b5wars.getUnit(name, model, version, scs_DisplaySCS);			
}

function scs_DisplaySCS(unit) {
	if (unit) {
		scs.setShip(unit);
	} else {		
		alert("Failed to retrieve unit");
	}
}

function scs_XMLToSCSClick() {
	b5wars.getUnit(scsXML.getValue(), scs_DisplaySCS);				
}

function scs_XMLToPDFClick() {
	b5wars.saveForExport(scsXML.getValue(), scs_ExportPDF);					
}

function scs_ExportPDF(unit) {
	if (unit) {
	    window.open("Export/" + unit.fullName + "?format=PDF", "_blank", "location=no,menubar=no,status=no,toolbar=no");		
	} else {
		alert("Failed to export unit");		
	}
}