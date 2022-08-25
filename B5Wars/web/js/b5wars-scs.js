function ShipControlSheet(container, mode) {
	this.WIDTH  = 612;
	this.HEIGHT = 792;
	this.HEADER_HEIGHT = 138;

	var page       = null;
    var silhouette = null;
    var icons      = new Array();
	
	setupPage();
	
    this.setShip = function(ship) {
    	setupPage();
    	
        silhouette = page.path(ship.silhouette);
        silhouette.attr("fill", "#C7C7C7");
        
//		if (ship.$dwrClassName == "SmallUnit") {
//			alert("SmallUnit");
//		} else if (ship.$dwrClassName == "StructuralUnit") {
//			alert("StructuralUnit");
//		} else if (ship.$dwrClassName == "Unit") {
//			alert("Unit");
//		} else {
//			alert("Unknown");
//		}
		
    	for (var i = 0; i < ship.systems.length; i++) {
    		var system = ship.systems[i];
    		
//    		if (i == 0) {
//	    		if (system instanceof Weapon) {
//	    			alert("Weapon");
//	    		} else if (system instanceof PoweredSystem) {
//	    			alert("PoweredSystem");
//	    		} else if (system instanceof System) {
//	    			alert("System");
//	    		} else {
//	    			alert("Unknown");
//	    		}
//    		}
    		
    		if (((system.iconOutlines) || (system.iconDamageBoxes)) && (system.iconPosition) && (system.iconPosition.x > 0) && (system.iconPosition.y > 0)) {
    			icons.push(new SystemIcon(system));
    		}
    		
    		if ((system.armor) && (system.armorIconPosition) && (system.armorIconPosition.x > 0) && (system.armorIconPosition.y > 0)) {
    			icons.push(new ArmorIcon(system));
    		}

    		if ((system.arcIconOutline) && (system.arcIconPosition) && (system.arcIconPosition.x > 0) && (system.arcIconPosition.y > 0)) {
    			icons.push(new ArcIcon(system));
    		}

    		if ((system.$dwrClassName == "PoweredSystem") && (system.power) && (system.powerIconPosition) && (system.powerIconPosition.x > 0) && (system.powerIconPosition.y > 0)) {
    			icons.push(new PowerIcon(system));
    		}

    		if ((system.$dwrClassName == "Weapon") && (system.name) && (system.weaponNumberPosition) && (system.weaponNumberPosition.x > 0) && (system.weaponNumberPosition.y > 0)) {
    			icons.push(new WeaponNumberIcon(system));
    		}
    	}
    };
    
    function setupPage() {
        if (!page) {
        	page = Raphael(container, this.WIDTH, this.HEIGHT);
        } else {
        	page.clear();        	
        }
        
        page.rect(0, 0, this.WIDTH, this.HEIGHT).attr("fill", "#FFFFFF");
    };

    function ArcIcon(system) {
    	this.arc   = null;
    	this.hexes = new Array();

    	this.arc = page.path(system.arcIconOutline);
    	this.arc.attr("stroke", "#000000");
    	this.arc.attr("stroke-width", "0.375");
    	this.arc.attr("fill", "#68696C");			
    	this.arc.translate(system.arcIconPosition.x, system.arcIconPosition.y);
    	
    	this.hexes.push(page.path("M 9.10625 5.025 L 10.79375 5.025 L 11.6375 6.7125 L 10.79375 8.4 L 9.10625 8.4 L 8.2625 6.7125 L 9.10625 5.025 Z"));				
    	this.hexes.push(page.path("M 9.10625 8.4 L 10.79375 8.4 L 11.6375 10.0875 L 10.79375 11.775 L 9.10625 11.775 L 8.2625 10.0875 L 9.10625 8.4 Z"));				
    	this.hexes.push(page.path("M 9.10625 11.775 L 10.79375 11.775 L 11.6375 13.4625 L 10.79375 15.15 L 9.10625 15.15 L 8.2625 13.4625 L 9.10625 11.775 Z"));				
    	this.hexes.push(page.path("M 11.6375 10.0875 L 13.325 10.0875 L 14.16875 11.775 L 13.325 13.4625 L 11.6375 13.4625 L 10.79375 11.775 L 11.6375 10.0875 Z"));				
    	this.hexes.push(page.path("M 11.6375 6.7125 L 13.325 6.7125 L 14.16875 8.4 L 13.325 10.0875 L 11.6375 10.0875 L 10.79375 8.4 L 11.6375 6.7125 Z"));				
    	this.hexes.push(page.path("M 6.575 10.0875 L 8.2625 10.0875 L 9.10625 11.775 L 8.2625 13.4625 L 6.575 13.4625 L 5.73125 11.775 L 6.575 10.0875 Z"));				
    	this.hexes.push(page.path("M 6.575 6.7125 L 8.2625 6.7125 L 9.10625 8.4 L 8.2625 10.0875 L 6.575 10.0875 L 5.73125 8.4 L 6.575 6.7125 Z"));
    	
		for (var i = 0; i < this.hexes.length; i++) {
			this.hexes[i].attr("stroke", "#000000");
			this.hexes[i].attr("stroke-width", "0.375");
			// TODO
//	 		if (hex == 360) {
//				this.hexes[i].attr("fill", "#FFFFFF");			
//			}
			this.hexes[i].translate(system.arcIconPosition.x, system.arcIconPosition.y);
		}
    }
    
    function ArmorIcon(system) {
    	this.shape = null;
    	this.armor = null;
    	
    	this.shape = page.path("M 8.75 2.0 C 12.4765625 2.0 15.5 5.0234375 15.5 8.75 C 15.5 12.4765625 12.4765625 15.5 8.75 15.5 C 5.0234375 15.5 2.0 12.4765625 2.0 8.75 C 2.0 5.0234375 5.0234375 2.0 8.75 2.0 Z");
    	this.shape.attr("stroke", "#000000");
    	this.shape.attr("stroke-width", "1.5");
    	this.shape.attr("fill", "#FFFFFF");			
    	this.shape.translate(system.armorIconPosition.x, system.armorIconPosition.y);
    	
    	this.armor = page.text(0, 0, system.armor.current);
    	this.armor.attr("font", "8pt Arial");
    	this.armor.attr("fill", (system.armor.current < system.armor.base) ? "#FF0000" : "#000000");
    	this.armor.translate(
    			system.armorIconPosition.x + (this.shape.getBBox().width  / 2.0) + 2, 
    			system.armorIconPosition.y + (this.shape.getBBox().height / 2.0) + 2);    	
    }

    function PowerIcon(system) {
    	this.shape = null;
    	this.power = null;

    	this.shape = page.path("M 2.25 9.25 L 9.0 2.5 L 15.75 9.25 L 9.0 16.0 L 2.25 9.25 Z");
    	this.shape.attr("stroke", "#000000");
    	this.shape.attr("stroke-width", "1.5");
    	this.shape.attr("fill", "#FFFFFF");			
    	this.shape.translate(system.powerIconPosition.x, system.powerIconPosition.y);
    	
    	this.power = page.text(0, 0, system.power.current);
    	this.power.attr("font", "6pt Arial");
    	this.power.attr("fill", (system.power.current < system.power.base) ? "#FF0000" : "#000000");
    	this.power.translate(
    			system.powerIconPosition.x + (this.shape.getBBox().width  / 2.0) + 2, 
    			system.powerIconPosition.y + (this.shape.getBBox().height / 2.0) + 2);    	
    }

    function SystemIcon(system) {
    	this.outlines    = new Array();
    	this.damageBoxes = new Array();

		if (system.iconOutlines) {
			for (var i = 0; i < system.iconOutlines.length; i++) {
				var outline = page.path(system.iconOutlines[i]);
				outline.attr("stroke", "#000000");
				outline.attr("stroke-width", "0.75");
				outline.attr("fill", "#FFFFFF");			
				outline.translate(system.iconPosition.x, system.iconPosition.y);
				
				this.outlines.push(damageBox);				
			}
		}
	 
		if (system.iconDamageBoxes) {
			for (var i = 0; i < system.iconDamageBoxes.length; i++) {
				var damageBox = page.path(system.iconDamageBoxes[i]);
				damageBox.attr("stroke", "#000000");
				damageBox.attr("stroke-width", "1.5");
				damageBox.attr("fill", "#FFFFFF");
				damageBox.translate(system.iconPosition.x, system.iconPosition.y);

//    				damageBox.click(function (event) {
//    				    this.attr({fill: "#FF0000"});
//    				});

				this.damageBoxes.push(damageBox);	
				
				// TODO draw megabox text? 
			}
		}
		
		// TODO annotations
    }
    
    function WeaponNumberIcon(system) {
    	this.number = null;

    	this.number = page.text(0, 0, system.name);
    	this.number.attr("font", "bold 9pt Arial");
    	this.number.translate(
    			system.weaponNumberPosition.x + 8, 
    			system.weaponNumberPosition.y + 5.5);    	
    }
    
};
