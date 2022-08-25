/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.weapon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * !!--- work in progress ---!!
 * A ShipWeapon is a weapon intended to be deployed solely onto larger craft such as 
 * ships, bases, and orbital satellites, whose structure can handle the load and stress
 * inherent in operating larger weapons 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class ShipWeapon implements Serializable {
	
	// Damage(amount)
	// DegradingDamage(degredationRate);
	// PulseDamage();
	// Sys
	
	// TODO
	// type - string
	// Class - WeaponClass 1
	// Mode - WeaponMode[] 1+
		// Raking(6)
		// Neutron Laser (Impr.) S(3)
		// Piercing vs fighters uses std mode
	// Damage 
		// 15, 3d10+20, 4d10+12(-1 per hex)
		// Rad Cannon vs. structure 10, vs weapon/thruster 100%, vs other see rules
		// Plasma Accelerator Std 4d10+12(-1 per hex), Heavy Plasma Cannon Std 4d10+8(-1 per 2 hexes)
		// Med Blast Cannon Pulse 5 1d5 times
		// Graviton Pulsar Pulse 10 1d2 times 2 extra power 1d3+1 4 extra power 1d3+2 pulses
		// Graviton Bolt Std 9 2 extra power 12 4 extra power 15
		// Med Plasma Bolter Plasma 16 (-1 per 2 hexes after range 10)
		// Com Jammer Std 1d6 initiative next turn
		// Sensor Spear Std 1d3 sensor next turn
		// Comm Disruptor 1d6 init, 1d6 sensor, next turn 
		// None
		// 1X+12 - antimatter
		// energy mine 30/10
		// plasma accelerator turn 1 1d10+4 -1/hex, turn 2 2d10+8 -1/hex, turn 3 4d10+12 -1/hex
		// TODO more...
	// Maximum X - antimatter
		// Antiproton gun 10
	// Maximum Pulses - Pulse
		// 6
		// Graviton Pulsar Pulse 3, 4, or 5
	// Grouping Range - Pulse
		// +1 per 5
	// Range Penalty 
		// -1 per 3 hexes
		// -2 per hex
		// none
		// Class-L Missile Rack none (+10)
		// Antiproton gun 0-5 none, 6-10 -1/hex, 11+ -2/hex
		// energy mine none (max 50)
	// Fire Control
		// energy mine specify target hex
	// Maximum Range - Ballistic
		// 50 hexes
	// Intercept Rating 
		// n/a
		// -2
		// Improved Gatling Railgun -1 (balistic only)
	// Rate of Fire 
		// 1 per 3 turns
		// 4 per turn
		// Plasma Accelerator 1 per 3 turns 4d10+12 -1/hex, 1 per turn 1d10+4 -1/hex, 1 per 2 2d10+8 -1/hex
		// Gravitic Lance - can fire as 2 
		// Dual Plasma Cannon - may fire as two medium
		// Ballistic Torpedo - can fire up to 6
		// twin array - linked
	
	// Burst Torpedo - -1 power if structure hit, deactive system, +4 crits, auto dropout
	// defense/offensive mode, heavy interceptor battery - may switch modes without shutdown
	// grappling claw
	// Mass driver - targets immobile, enormous, bases, or planets, launching must be speed 0
	
	
	
	
	
	
	
	
	private static final long serialVersionUID = 7947653861981262450L;

	private static final List<ShipWeapon> WEAPONS = new ArrayList<ShipWeapon>(0); // all weapons
	
	// TODO add fields
	private final String      type;           // the weapon type
	private final int         damageBoxes;    // amount of damage the weapon can sustain before being destroyed
	private final int         power;          // amount of power required for the weapon to function
	private final FireControl fireControl;    // fire control for the weapon
	private final int         defensiveBonus; // the defensive bonus applied to incoming fire
	
	/**
	 * a list of all weapons
	 * 
	 * @return a list of all weapons
	 */
	protected static final List<ShipWeapon> getWeapons() {
		return WEAPONS;
	}
	
    /**
     * an iterator of all weapons
     * 
	 * @return iterator of all weapons
	 */
	public static final Iterator<ShipWeapon> getWeaponsIterator() {
		return getWeapons().iterator();
	}

	/**
     * find and return a weapon with a given type
     * 
     * @param  type the weapon type to return
     * @return      the weapon, or null if a weapon with the given type doesn't exist
     */
    public static final ShipWeapon getWeapon(final String type) {
    	ShipWeapon result = null;

    	for (Iterator<ShipWeapon> iterator = getWeapons().iterator(); iterator.hasNext();) {
    		ShipWeapon weapon = iterator.next();

            if ((weapon != null) && (weapon.getType().equalsIgnoreCase(type))) {
                result = weapon;
                break;
            }                
    	} 

        return result;
    }

	/**
	 * @param type        the weapon type
	 * @param damageBoxes amount of damage the weapon can sustain before being destroyed
	 * @param power       amount of power required for the weapon to function
	 * @param fireControl fire control for the weapon
	 */
	public ShipWeapon(final String type, final int damageBoxes, final int power, final FireControl fireControl) {
		this(type, damageBoxes, power, fireControl, 0);
	}
    
	/**
	 * @param type           the weapon type
	 * @param damageBoxes    amount of damage the weapon can sustain before being destroyed
	 * @param power          amount of power required for the weapon to function
	 * @param fireControl    fire control for the weapon
	 * @param defensiveBonus the defensive bonus applied to incoming fire
	 */
	public ShipWeapon(final String type, final int damageBoxes, final int power, final FireControl fireControl,
			final int defensiveBonus) {
        if ((type == null) || !(type.length() > 0)) {
            throw new IllegalArgumentException("type cannot be null or blank");
        } 
        if (getWeapon(type) != null) {
            throw new IllegalArgumentException("A Weapon with same type already exists!");
        }
        if (!(damageBoxes > 0)) {
            throw new IllegalArgumentException("damageBoxes must be a positive number");
        } 
        if (power < 0) {
            throw new IllegalArgumentException("power cannot be a negative number");
        } 
		if (fireControl == null) {
            throw new IllegalArgumentException("fireControl cannot be null");
        } 

		this.type           = type;
        this.damageBoxes    = damageBoxes;
        this.power          = power;
        this.fireControl    = fireControl;
        this.defensiveBonus = defensiveBonus;
        
		WEAPONS.add(this);
	}
	
	/**
	 * the weapon type
	 * 
	 * @return the weapon type
	 */
	public final String getType() {
		return type;
	}

	/**
	 * the amount of damage the weapon can sustain before being destroyed
	 * 
	 * @return the amount of damage the weapon can sustain before being destroyed
	 */
	public final int getDamageBoxes() {
		return damageBoxes;
	}
	
	/**
	 * the amount of power required for the weapon to function
	 * 
	 * @return the amount of power required for the weapon to function
	 */
	public final int getPower() {
		return power;
	}

	/**
	 * the fire control for the weapon
	 * 
	 * @return the fire control for the weapon
	 */
	public final FireControl getFireControl() {
		return fireControl;
	}
	
	/**
	 * the defensive bonus applied to incoming fire
	 * 
	 * @return the defensive bonus applied to incoming fire
	 */
	public final int getDefensiveBonus() {
		return defensiveBonus;
	}

	// temporarily add basic weapon stats until xml weapon definition is up and running
	static {
		// DONE: Abbai, Alacan, Balosian, Belt Alliance, Brakiri, Cascor, Centauri, Civilian, Corillani, 
		// Descari, Dilgar, Drazi, EA, Gaim, Grome, Hurr, Hyach, Ipsha, Kor-Lyan, Llort, Markab, Minbari, 
		// Mitoc, Narn, Orieni, Pak'ma'ra, Raider, Shadows, Streib, Torata, Vorlon, Vree, Yolu
		
		// TODO: Ancients (commented out weapons below, done systems)
		// TODO: Mindrider, Torvalus, Triad, Walker (done systems)
		
		// Particle Weapons
		new ShipWeapon("Heavy Pulse Cannon",			6,	4,	new FireControl(Integer.valueOf(4), Integer.valueOf(3), Integer.valueOf(-1)));
		new ShipWeapon("Medium Pulse Cannon",			6,	3,	new FireControl(Integer.valueOf(4), Integer.valueOf(3), Integer.valueOf(1)));
		new ShipWeapon("Light Pulse Cannon",			4,	2,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(4)));
		new ShipWeapon("Heavy Particle Cannon",			12,	9,	new FireControl(Integer.valueOf(6), Integer.valueOf(4), Integer.valueOf(0)));
		new ShipWeapon("Particle Cannon",				8,	7,	new FireControl(Integer.valueOf(5), Integer.valueOf(4), Integer.valueOf(2)));
		new ShipWeapon("Light Particle Cannon",			6,	5,	new FireControl(Integer.valueOf(4), Integer.valueOf(2), Integer.valueOf(0))); // JYD Converted Lias Supply Ship
		new ShipWeapon("Particle Blaster",				8,	5,	new FireControl(Integer.valueOf(4), Integer.valueOf(4), Integer.valueOf(0)));
		new ShipWeapon("Particle Repeater",				6, 4,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(4)));
		new ShipWeapon("Repeater Gun",					6, 4,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(2))); // Drazi Kestrel Corvette Leader
		new ShipWeapon("Particle Cutter",				8, 3,	new FireControl(Integer.valueOf(4), Integer.valueOf(3), Integer.valueOf(2)));
		new ShipWeapon("Solar Cannon",					7,	3,	new FireControl(Integer.valueOf(5), Integer.valueOf(3), Integer.valueOf(0)));
		new ShipWeapon("Heavy Particle Beam",			6,	2,	new FireControl(Integer.valueOf(4), Integer.valueOf(3), Integer.valueOf(2))); // Centauri Lupa Attack Boats
		new ShipWeapon("Standard Particle Beam",		4,	1,	new FireControl(Integer.valueOf(4), Integer.valueOf(4), Integer.valueOf(4)));
		new ShipWeapon("Light Particle Beam",			2,	1,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(3)));
		new ShipWeapon("Quad Particle Beam",			8,	4,	new FireControl(Integer.valueOf(4), Integer.valueOf(4), Integer.valueOf(4))); // Babylon 5 Battle Station
		new ShipWeapon("Particle Concentrator",			9,	7,	new FireControl(Integer.valueOf(5), Integer.valueOf(4), Integer.valueOf(2)));
		new ShipWeapon("Twin Array",					6,	2,	new FireControl(Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(6)));
		new ShipWeapon("Quad Array",					8,	4,	new FireControl(Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(6)));
//		new ShipWeapon("Quad Particle Array",			8,	4,	new FireControl(Integer.valueOf(4), Integer.valueOf(4), Integer.valueOf(4))); // Quad Particle Beam?
		new ShipWeapon("Heavy Array",					8,	4,	new FireControl(Integer.valueOf(4), Integer.valueOf(3), Integer.valueOf(2)));
		new ShipWeapon("Interceptor Prototype",			4,	1,	new FireControl(null, null, Integer.valueOf(4)), 2); // EA Laertes Police Corvette
		new ShipWeapon("Interceptor Mk-I",				4,	1,	new FireControl(null, null, Integer.valueOf(6)), 3);
		new ShipWeapon("Interceptor Mk-II",				4,	2,	new FireControl(null, null, Integer.valueOf(8)), 4);
		new ShipWeapon("Heavy Interceptor Battery",		6,	3,	new FireControl(null, null, Integer.valueOf(10)), 4);
		new ShipWeapon("Guardian Array",				4,	2,	new FireControl(null, null, Integer.valueOf(8)));
		new ShipWeapon("Scattergun",					8,	3,	new FireControl(Integer.valueOf(0), Integer.valueOf(2), Integer.valueOf(5)));
		new ShipWeapon("Pulsar Mine",					6,	4,	new FireControl(null, null, Integer.valueOf(4))); // Narn G'Karith
		new ShipWeapon("Particle Impeder",				6,	2,	new FireControl());
		new ShipWeapon("Heavy Bolter",					10,	6,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(-1)));
		new ShipWeapon("Medium Bolter",					8,	4,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(1)));
		new ShipWeapon("Light Bolter",					6,	2,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(3)));
		new ShipWeapon("Quad Pulsar",					10,	4,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(-1)));
		new ShipWeapon("Scatter-Pulsar",				4,	2,	new FireControl(Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3)));
		new ShipWeapon("Energy Pulsar",					6,	3,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(1)));
		new ShipWeapon("Point Pulsar",					6,	3,	new FireControl(Integer.valueOf(5), Integer.valueOf(3), Integer.valueOf(-4)));
		new ShipWeapon("Interdictor",					4,	1,	new FireControl()); // Hyach Alichi Kav Stealth Cruiser
		new ShipWeapon("Pentagon Array",				8,	5,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(3))); // Torata Alovar Scout Carrier
		new ShipWeapon("Particle Accelerator",			8,	8,	new FireControl(Integer.valueOf(4), Integer.valueOf(4), Integer.valueOf(2))); // Torata Atlac Corvette
		new ShipWeapon("Pulse Accelerator",				9,	4,	new FireControl(Integer.valueOf(4), Integer.valueOf(3), Integer.valueOf(1))); // Torata Colotnar Defense Base
		new ShipWeapon("Heavy Particle Projector",		8,	3,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(-1))); // S8 Usuuth Orthuus Battle Leader
		new ShipWeapon("Particle Projector",			6,	1,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(1))); // WCR Centauri Astur Assault Ship
		new ShipWeapon("Light Particle Projector",		3,	1,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(3))); // S8 Usuuth Orthuus Battle Leader
		new ShipWeapon("Sentinel Point Defense",		4,	1,	new FireControl()); // WCR Centauri Kendari Fleet Scout
		new ShipWeapon("Particle Hammer",				12,	5,	new FireControl(Integer.valueOf(3), Integer.valueOf(1), Integer.valueOf(-2))); // S8 Usuuth Orthuus Battle Leader
//		new ShipWeapon("Ultra Pulse Cannon",			28,	12,	new FireControl(Integer.valueOf(6), Integer.valueOf(6), Integer.valueOf(6))); // Ancients - Thoughtforce
//		new ShipWeapon("Trioptic Pulsar",				6,	3,	new FireControl(Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4))); // Ancients - Thoughtforce

		// Laser Weapons
		new ShipWeapon("Heavy Laser Cannon",			8,	6,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(-4)));
		new ShipWeapon("Medium Laser Cannon",			6,	5,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(-3)));
		new ShipWeapon("Light Laser Cannon",			4,	3,	new FireControl(Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(-2)));
		new ShipWeapon("Laser/Pulse Array",				9,	5,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(-3)));
		new ShipWeapon("Heavy Laser/Pulse Array",		10,	6,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(-4))); // EA Omega Command Destroyer
		new ShipWeapon("Neutron Laser",					10,	6,	new FireControl(Integer.valueOf(4), Integer.valueOf(4), Integer.valueOf(1)));
		new ShipWeapon("Improved Neutron Laser",		11,	7,	new FireControl(Integer.valueOf(5), Integer.valueOf(4), Integer.valueOf(1))); // Neutron Laser (Impr.)?
		new ShipWeapon("Battle Laser",					6,	6,	new FireControl(Integer.valueOf(4), Integer.valueOf(3), Integer.valueOf(-3)));
		new ShipWeapon("Combat Laser",					7,	7,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(-2)));
		new ShipWeapon("Assault Laser",					6,	4,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(-4)));
		new ShipWeapon("Laser Cutter",					6,	4,	new FireControl(Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(-2))); // Alacan
		new ShipWeapon("Spinal Laser",					12,	12,	new FireControl(Integer.valueOf(4), Integer.valueOf(2), null)); // Hyach Alichi Kav Stealth Cruiser
		new ShipWeapon("Maser",							6,	3,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(2))); // Hyach Alichi Tal Infiltrator
		new ShipWeapon("Proximity Laser",				6,	6,	new FireControl(Integer.valueOf(0), Integer.valueOf(0), null)); // Hyach Senchlat Kir Ballistic Cruiser
		new ShipWeapon("Blast Laser",					10,	5,	new FireControl(Integer.valueOf(4), Integer.valueOf(2), Integer.valueOf(0))); // Hyach Tachila Kor Scout Carrier
		new ShipWeapon("Improved Blast Laser",			10,	8,	new FireControl(Integer.valueOf(5), Integer.valueOf(3), Integer.valueOf(-1))); // Streib Collector Ship
		new ShipWeapon("Laser Accelerator",				7,	6,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(0))); // Torata Colotnar Defense Base
		new ShipWeapon("Heavy Laser Lance",				6,	4,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(-5))); // WCR Orieni Benevolent Heavy Scout
		new ShipWeapon("Laser Lance",					6,	4,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(-5))); // WCR Orieni Benevolent Heavy Scout
		new ShipWeapon("Tactical Laser",				5,	4,	new FireControl(Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(-5))); // WCR Centauri Balcirax Attack Destroyer
		new ShipWeapon("Imperial Laser",				8,	5,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(-5))); // WCR Centauri Celerian Warcruiser
//		new ShipWeapon("Strike Laser",					7,	4,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), null)); // V6 Raider Ruffian Cruiser
		new ShipWeapon("Power Laser",					15,	7,	new FireControl(Integer.valueOf(6), Integer.valueOf(5), Integer.valueOf(4))); // Torvalus Dark Knife
		new ShipWeapon("Medium Power Laser",			9,	5,	new FireControl(Integer.valueOf(5), Integer.valueOf(4), Integer.valueOf(3))); // Torvalus Veiled Scimitar
		new ShipWeapon("Volley Laser",					9,	4,	new FireControl(Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(6))); // Torvalus Dark Knife
		
		// Plasma Weapons
		new ShipWeapon("Mega Plasma Cannon",			10,	8,	new FireControl(Integer.valueOf(3), Integer.valueOf(1), Integer.valueOf(-5)));
		new ShipWeapon("Heavy Plasma Cannon",			8,	5,	new FireControl(Integer.valueOf(3), Integer.valueOf(1), Integer.valueOf(-5)));
		new ShipWeapon("Medium Plasma Cannon",			5,	3,	new FireControl(Integer.valueOf(3), Integer.valueOf(1), Integer.valueOf(-5)));
		new ShipWeapon("Light Plasma Cannon",			4,	2,	new FireControl(Integer.valueOf(3), Integer.valueOf(1), Integer.valueOf(-5))); //Descari Hemsar OSATs
		new ShipWeapon("Dual Plasma Cannon",			10,	6,	new FireControl(Integer.valueOf(3), Integer.valueOf(1), Integer.valueOf(-5))); // Descari Hemsar OSATs
		new ShipWeapon("Fuser",							18,	12,	new FireControl(Integer.valueOf(5), Integer.valueOf(3), null));
		new ShipWeapon("Ranged Fuser",					12,	12,	new FireControl(Integer.valueOf(5), Integer.valueOf(3), null)); // Pak'ma'ra Thor'ka Defense Satellites
		new ShipWeapon("Plasma Accelerator",			10,	5,	new FireControl(Integer.valueOf(3), Integer.valueOf(1), Integer.valueOf(-4)));
		new ShipWeapon("Mag Gun",						9,	8,	new FireControl(Integer.valueOf(6), Integer.valueOf(2), null));
		new ShipWeapon("Plasma Stream",					9,	7,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(-4))); // Centauri Mograth Frigate
		new ShipWeapon("Dual Plasma Stream",			10,	10,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(-4))); // Pak'ma'ra Urik'hal Fast Destroyer
		new ShipWeapon("Plasma Web",					4,	2,	new FireControl()); 
		new ShipWeapon("Plasma Torch",					4,	2,	new FireControl(Integer.valueOf(2), Integer.valueOf(0), null));
		new ShipWeapon("Plasma Projector",				8,	5,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), null)); // Corillani
		new ShipWeapon("Heavy Plasma Bolter",			10,	5,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(-4))); // Descari Carrana Base
		new ShipWeapon("Medium Plasma Bolter",			8,	4,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(-3))); // Descari Cruscava Escort Frigate
		new ShipWeapon("Light Plasma Bolter",			6,	2,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(-2))); // Descari Cruscava Escort Frigate
//		new ShipWeapon("Hyperplasma Cutter",			16,	9,	new FireControl(Integer.valueOf(6), Integer.valueOf(6), Integer.valueOf(6))); // The Triumviron
		
		// Molecular Weapons
		new ShipWeapon("Molecular Pulsar",				8,	2,	new FireControl(Integer.valueOf(4), Integer.valueOf(3), Integer.valueOf(2)));
//		new ShipWeapon("Super Heavy Molecular Disruptor",	12,	10,	new FireControl(Integer.valueOf(3), Integer.valueOf(1), null)); // Yolu Early Defence Satellites
		new ShipWeapon("Molecular Disruptor",			8,	6,	new FireControl(Integer.valueOf(4), Integer.valueOf(2), Integer.valueOf(-4)));
//		new ShipWeapon("Light Molecular Disruptor",		6,	4,	new FireControl(Integer.valueOf(3), Integer.valueOf(0), Integer.valueOf(-4))); // Yolu Early Defence Satellites
//		new ShipWeapon("Heavy Fusion Cannon",			10,	4,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(3))); // Yolu Hastan Escort Frigate
		new ShipWeapon("Fusion Cannon",					8,	1,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(4)));
		new ShipWeapon("Heavy Molecular Slicer Beam",	18,	16,	new FireControl(Integer.valueOf(8), Integer.valueOf(6), Integer.valueOf(4))); // Shadow Battle Cruiser
		new ShipWeapon("Molecular Slicer Beam",			15,	12,	new FireControl(Integer.valueOf(8), Integer.valueOf(6), Integer.valueOf(4)));
		new ShipWeapon("Light Molecular Slicer Beam",	10,	10,	new FireControl(Integer.valueOf(6), Integer.valueOf(4), Integer.valueOf(2))); // EA Shadow Omega Destroyer
		new ShipWeapon("Multiphased Cutter",			10,	4,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(6))); // Shadow Destroyer
		new ShipWeapon("Light Multiphased Cutter",		4,	3,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(6))); // EA Shadow Omega Destroyer
		new ShipWeapon("Heavy Phasing Pulse Cannon",	9, 	5,	new FireControl(Integer.valueOf(6), Integer.valueOf(4), Integer.valueOf(2))); // EA Shadow Omega Destroyer
		new ShipWeapon("Medium Phasing Pulse Cannon",	7, 	4,	new FireControl(Integer.valueOf(6), Integer.valueOf(4), Integer.valueOf(2))); // Shadow Scout
		new ShipWeapon("Destabilizer Beam",				10,	8,	new FireControl(Integer.valueOf(6), Integer.valueOf(1), Integer.valueOf(-5))); // Yolu Aluin Gunship
//		new ShipWeapon("Fusion Cutter",					7,	4,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), null)); // Yolu Early Defence Satellites
//		new ShipWeapon("Molecular Penetrator",			10,	6,	new FireControl(Integer.valueOf(4), Integer.valueOf(-1), null)); // Yolu Defence Satellites
//		new ShipWeapon("Early Fusion Agitator",			9,	4,	new FireControl(Integer.valueOf(4), Integer.valueOf(2), null)); // Yolu Defence Satellites
//		new ShipWeapon("Fusion Agitator",				10,	4,	new FireControl(Integer.valueOf(4), Integer.valueOf(4), null)); // Yolu
//		new ShipWeapon("Molecular Flayer",				8,	4,	new FireControl(Integer.valueOf(4), Integer.valueOf(0), null)); // Yolu
		
		// Electromagnetic Weapons
		new ShipWeapon("Electro-Pulse Gun",				6, 	3,	new FireControl(null, null, Integer.valueOf(3)));
		new ShipWeapon("Burst Beam",					6, 	3,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(4))); // Electro-Burst Beam? Narn
		new ShipWeapon("Heavy Burst Beam",				9, 	8,	new FireControl(Integer.valueOf(5), Integer.valueOf(4), Integer.valueOf(-2))); // Streib Collector Ship
		new ShipWeapon("Medium Burst Beam",				7, 	6,	new FireControl(Integer.valueOf(4), Integer.valueOf(3), Integer.valueOf(0))); // Streib Collector Ship
		new ShipWeapon("Dual Burst Beam",				8, 	5,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(4))); // Streib Collector Ship
		new ShipWeapon("Burst Pulse Cannon",			6, 	7,	new FireControl(Integer.valueOf(4), Integer.valueOf(3), Integer.valueOf(2))); // Streib Collector Ship
		new ShipWeapon("Shock Cannon",					6, 	4,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(3))); // Minbari Esharan Anti-Fighter Frigate
		new ShipWeapon("Comm Disruptor",				6, 	3,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(-1))); // Abbai Shyarie Jammer Frigate
		new ShipWeapon("Lightning Cannon",				15,	1,	new FireControl(Integer.valueOf(5), Integer.valueOf(5), Integer.valueOf(8)));
		new ShipWeapon("Lightning Gun",					11,	1,	new FireControl(Integer.valueOf(4), Integer.valueOf(4), Integer.valueOf(7))); // Vorlon Strike Cruiser
		new ShipWeapon("Discharge Gun",					10,	2,	new FireControl(Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4)));
		new ShipWeapon("Discharge Cannon",				15,	5,	new FireControl(Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4)));
		new ShipWeapon("Discharge Pulsar",				12,	4,	new FireControl(Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(5))); // Vorlon Strike Cruiser
		new ShipWeapon("Planet-Cracker Beam",          110, 96, new FireControl()); 
		new ShipWeapon("Vortex Disruptor", 				4, 	8,	new FireControl());
		new ShipWeapon("Stun Beam",						6,	5,	new FireControl(Integer.valueOf(4), Integer.valueOf(2), Integer.valueOf(0))); // Markab Shafab Heavy Cruiser
		new ShipWeapon("Sensor Spear",					6,	3,	new FireControl(Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(-1))); // S8 Abbai Nota Deep Scout
//		new ShipWeapon("Sensor Spike",					6,	3,	new FireControl(Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(-1))); // V6 Abbai Kirva Jammer Satellites
		new ShipWeapon("Comm Jammer",					4,	3,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(0))); // S8 Abbai Nota Deep Scout, Com Jammer
//		new ShipWeapon("Improved Comm Jammer",			4,	3,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(0))); // V6 Abbai Kirva Jammer Satellites, Imp. Com Jammer
		new ShipWeapon("Surge Cannon",					6,	3,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(2))); // Ipsha Battleglobe
		new ShipWeapon("Surge Blaster",					6,	6,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(1))); // Ipsha Tetraship
		new ShipWeapon("Spark Field",					8,	2,	new FireControl()); // Ipsha Battleglobe
		new ShipWeapon("Resonance Generator",			8,	6,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), null)); // Ipsha Battlehex
		new ShipWeapon("EM Bolter",						10,	9,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(0))); // Ipsha Boltglobe
		new ShipWeapon("EM Pulsar",						6,	3,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(1))); // Ipsha Carrier Cube
		new ShipWeapon("EM Wave Disruptor",				8,	4,	new FireControl(null, null, Integer.valueOf(4))); // Streib Collector Ship
		new ShipWeapon("Lightning Array",				40,	16,	new FireControl(Integer.valueOf(4), Integer.valueOf(6), Integer.valueOf(8))); // The Traveler
		new ShipWeapon("Chromatic Pulse Driver",		15,	6,	new FireControl(Integer.valueOf(4), Integer.valueOf(4), Integer.valueOf(4))); // The Traveler
		new ShipWeapon("Energy Draining Field",			40,	8,	new FireControl()); // The Traveler
		
		// Matter Weapons
		new ShipWeapon("Mass Driver",					18,	9,	new FireControl(Integer.valueOf(2), null, null)); 
		new ShipWeapon("Matter Cannon",					7,	4,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(-2)));
		new ShipWeapon("Heavy Railgun",					12,	9,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(-3))); // Grome Gormok Orbital Satellites
		new ShipWeapon("Railgun",						9,	6,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(-3)));
		new ShipWeapon("Medium Railgun",				9,	6,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(-3))); // Grome Adrina War Escort
		new ShipWeapon("Light Railgun",					6,	3,	new FireControl(Integer.valueOf(0), Integer.valueOf(2), Integer.valueOf(3))); // Grome Cargo Barge
		new ShipWeapon("Heavy Blast Cannon",			6,	4,	new FireControl(Integer.valueOf(4), Integer.valueOf(3), Integer.valueOf(0))); // Belt Alliance Heavy Gunboat
		new ShipWeapon("Medium Blast Cannon",			5,	2,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(0))); // Belt Alliance Escort Carrier
		new ShipWeapon("Light Blast Cannon",			4,	1,	new FireControl(Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(0))); // Belt Alliance Heavy Gunboat
		new ShipWeapon("Flak Cannon",					4,	2,	new FireControl(null, null, Integer.valueOf(4))); // Grome Adrina War Escort
		new ShipWeapon("Gatling Railgun",				4,	1,	new FireControl(Integer.valueOf(0), Integer.valueOf(2), Integer.valueOf(4))); // WCR - Orieni Benevolent Heavy Scout
		new ShipWeapon("Rapid Gatling Railgun",			4,	1,	new FireControl(Integer.valueOf(0), Integer.valueOf(2), Integer.valueOf(4))); // WCR - Orieni Benevolent Heavy Scout
		new ShipWeapon("Gauss Cannon",					10,	4,	new FireControl(Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(-3))); // WCR - Orieni Obedient Patrol Frigate
		new ShipWeapon("Heavy Gauss Cannon",			10,	4,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(-2))); // WCR - Orieni Paragon Strike Force Command Ship
		new ShipWeapon("Matter Accelerator",			13,	7,	new FireControl(Integer.valueOf(5), Integer.valueOf(5), Integer.valueOf(0))); // Kirishiac Conqueror
		
		// Gravitic Weapons
		// Tractor Beam?
		new ShipWeapon("Gravity Net",					8,	5,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(1))); // Plasma Net?
		new ShipWeapon("Graviton Beam",					8,	8,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(-5)));
		new ShipWeapon("Gravitic Lance",				12,	16,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(-5)));
		new ShipWeapon("Grav Cannon",					6,	5,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(-1)));
		new ShipWeapon("Graviton Pulsar",				5,	2,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(4)));
		new ShipWeapon("Gravitic Bolt",					5,	2,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(4)));
		new ShipWeapon("Gravitic Shifter",				6,	5,	new FireControl(Integer.valueOf(5), Integer.valueOf(3), Integer.valueOf(-3)));
		new ShipWeapon("Gravitic Cutter",				6,	5,	new FireControl(Integer.valueOf(4), Integer.valueOf(2), Integer.valueOf(-4))); // Brakiri Brathon Auxiliary Cruiser
		new ShipWeapon("Gravitic Mine",					6,	6,	new FireControl()); // Graviton Mine?
		new ShipWeapon("Hypergraviton Beam",			20,	12,	new FireControl(Integer.valueOf(5), Integer.valueOf(4), Integer.valueOf(3))); // Kirishiac Conqueror
		new ShipWeapon("Hypergraviton Blaster",			30,	15,	new FireControl(Integer.valueOf(6), Integer.valueOf(6), Integer.valueOf(6))); // Kirishiac Lordship
		new ShipWeapon("Antigravity Beam",				6,	3,	new FireControl(Integer.valueOf(1), Integer.valueOf(3), Integer.valueOf(5))); // Kirishiac
		new ShipWeapon("Medium Antigravity Beam",		6,	2,	new FireControl(Integer.valueOf(1), Integer.valueOf(3), Integer.valueOf(4))); // Kirishiac Conqueror
		new ShipWeapon("Gravitic Augmenter",			12,	7,	new FireControl(Integer.valueOf(6), Integer.valueOf(5), Integer.valueOf(0))); // Kirishiac Mastership
		
		// Antimatter Weapons
		new ShipWeapon("Antimatter Converter",			7,	5,	new FireControl(Integer.valueOf(4), Integer.valueOf(4), Integer.valueOf(-6)));
		new ShipWeapon("Antimatter Cannon",				9,	8,	new FireControl(Integer.valueOf(5), Integer.valueOf(3), Integer.valueOf(-2)));
		new ShipWeapon("Antiproton Gun",				8,	4,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(2)));
		new ShipWeapon("Antiproton Defender",			4,	3,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(4)));
		new ShipWeapon("Antimatter Shredder",			10,	8,	new FireControl(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0)));

		// Ionic Weapons
		new ShipWeapon("Ion Cannon",					6,	4,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(0)));
		new ShipWeapon("Dual Ion Bolter",				4,	4,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(2))); // Cascor
		new ShipWeapon("Ion Field Generator",			8,	4,	new FireControl()); // Cascor
		new ShipWeapon("Rad Cannon",					8,	6,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), null)); // Cascor
		new ShipWeapon("Ionic Laser",					6,	4,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(-3))); // Cascor

		// Ballistic Weapons
		new ShipWeapon("Class-A Missile Rack",			6,	0,	new FireControl(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(4))); // EA Hyperion Aegis
		new ShipWeapon("Class-B Missile Rack",			9,	0,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(3)));
		new ShipWeapon("Class-D Missile Rack",			6,	0,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(3))); // Kor-Lyan
		new ShipWeapon("Class-F Missile Rack",			6,	0,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(3))); // Kor-Lyan
		new ShipWeapon("Class-L Missile Rack",			6,	0,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(3)));
		new ShipWeapon("Class-LH Missile Rack",			8,	0,	new FireControl(Integer.valueOf(4), Integer.valueOf(4), Integer.valueOf(4)));
		new ShipWeapon("Class-R Missile Rack",			6,	0,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(3))); // Kor-Lyan Solyrn Missile Destroyer
		new ShipWeapon("Class-S Missile Rack",			6,	0,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(3)));
		new ShipWeapon("Class-SO Missile Rack",			6,	0,	new FireControl(Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(2)));
		new ShipWeapon("Ion Torpedo",					5,	4,	new FireControl(Integer.valueOf(3), Integer.valueOf(1), Integer.valueOf(-4)));
		new ShipWeapon("Ballistic Torpedo",				5,	6,	new FireControl(Integer.valueOf(4), Integer.valueOf(3), Integer.valueOf(0)));
		new ShipWeapon("Packet Torpedo",				6,	5,	new FireControl(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(-6)));
		new ShipWeapon("Antimatter Torpedo",			6,	7,	new FireControl(Integer.valueOf(4), Integer.valueOf(2), Integer.valueOf(-2)));
		new ShipWeapon("Energy Mine",					5,	4,	new FireControl());
		new ShipWeapon("Bomb Rack",						6,	0,	new FireControl(Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(1)));
		new ShipWeapon("Limpet-Bore Torpedo",			5,	3,	new FireControl(Integer.valueOf(4), Integer.valueOf(2), null)); // Kor-Lyan Fenja Assault Leader
		new ShipWeapon("Ballistic Mine Launcher",		7,	4,	new FireControl()); // Kor-Lyan Trylkan Ballistic Destroyer
		new ShipWeapon("Plasma Wave",					7,	4,	new FireControl(Integer.valueOf(2), Integer.valueOf(0), null)); // Markab Shafab Heavy Cruiser
//		new ShipWeapon("Mine Launcher",					6,	3,	new FireControl()); // V6 Abbai Motenai Heavy Mine Layer
		new ShipWeapon("Phased Gravitic Torpedo",		16,	6,	new FireControl(Integer.valueOf(5), Integer.valueOf(4), Integer.valueOf(3))); // Kirishiac Knightship
		
		// Aegis Sensor Pod - EA Hyperion Aegis Cruiser
		// Transverse Drive - Torvalus
	}
	
}
