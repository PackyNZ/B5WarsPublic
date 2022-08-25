package net.b5gamer.b5wars.test;

import java.io.File;
import java.util.List;

import net.b5gamer.b5wars.io.unit.UnitXMLReader;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.structural.StructuralUnit;

public class SerializationTest {

	public static void main(String[] args) throws Exception {
//		String filename = "Earth\\EA Hyperion Heavy Cruiser (Theta Model).xml";
//		String filename = "Earth\\EA Hyperion Patrol Cruiser (Eta Model).xml";
//		String filename = "Earth\\EA Nova Dreadnought (Beta Model).xml";
//		String filename = "Minbari\\Minbari Sharlin War Cruiser.xml";
//		String filename = "Minbari\\Minbari White Star (Standard Model).xml";

		String filename = "Earth\\EA Hyperion Heavy Cruiser (Theta Model).xml";
		
		List<Unit> units = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + filename);
		StructuralUnit unit = (StructuralUnit) units.get(0);
		UnitTester.outputUnit((StructuralUnit) unit); 
		 
//		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + filename + ".ufo"));
//		out.writeObject(unit);
//		out.close();
		
//		ObjectInputStream in = new ObjectInputStream(new FileInputStream(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + filename + ".ufo"));
//		Unit inputUnit = (Unit) in.readObject();
		
//		UnitTester.outputUnit((StructuralUnit) inputUnit); 
		
//		ObjectInputStream in = new ObjectInputStream(new FileInputStream(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + filename + ".ufo"));
//		Unit unit1 = (Unit) in.readObject();
//		in = new ObjectInputStream(new FileInputStream(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + filename + ".ufo"));
//		Unit unit2 = (Unit) in.readObject();
//
//		System.out.println("unit1=" + unit1);
//		System.out.println("unit2=" + unit2);
//		
//		HitLocationTester.hitLocation((StructuralUnit) unit1, ((StructuralUnit) unit1).getSection(Section.PRIMARY), 40);
//		
//		if (unit1 == unit2) {
//			System.out.println("unit1 == unit2");
//		} else {
//			System.out.println("unit1 != unit2");
//		}
//
//		UnitTester.outputUnit((StructuralUnit) unit1); 
//		UnitTester.outputUnit((StructuralUnit) unit2); 
	}

}
