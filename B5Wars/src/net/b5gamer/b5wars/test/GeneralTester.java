package net.b5gamer.b5wars.test;

import net.b5gamer.b5wars.ui.logging.Logger;
import net.b5gamer.b5wars.unit.Size;
import net.b5gamer.b5wars.weapon.FireControl;
import net.b5gamer.map.Arc;
import net.b5gamer.map.Direction;

public class GeneralTester {

	public static void main(String[] args) {
		// arc
		Logger.info("Arc test...");
		Logger.info("Arc " + Arc.FWD_120.getName() + " contains 270 degrees = " + Arc.FWD_120.contains(270) + " (false)");
		Logger.info("Arc " + Arc.FWD_120.getName() + " contains 330 degrees = " + Arc.FWD_120.contains(330) + " (true)");
		Logger.info("Arc " + Arc.FWD_120.getName() + " contains 30 degrees = " + Arc.FWD_120.contains(30) + " (true)");		
		Logger.info("Arc " + Arc.FWD_120.getName() + " contains 90 degrees = " + Arc.FWD_120.contains(90) + " (false)");
		Logger.info("Arc " + Arc.AFT_120.getName() + " contains 90 degrees = " + Arc.AFT_120.contains(90) + " (false)");
		Logger.info("Arc " + Arc.AFT_120.getName() + " contains 180 degrees = " + Arc.AFT_120.contains(180) + " (true)");
		Logger.info("Arc " + Arc.AFT_120.getName() + " contains 270 degrees = " + Arc.AFT_120.contains(270) + " (false)");		
		Logger.info("Arc " + Arc.AFT_120.getName() + " contains 119 degrees = " + Arc.AFT_120.contains(119) + " (false)");
		Logger.info("Arc " + Arc.AFT_120.getName() + " contains 120 degrees = " + Arc.AFT_120.contains(120) + " (true)");		
		Logger.info("Arc " + Arc.FULL_360.getName() + " contains 0 degrees = " + Arc.FULL_360.contains(0) + " (true)");		
		Logger.info("Arc " + Arc.FULL_360.getName() + " contains 180 degrees = " + Arc.FULL_360.contains(180) + " (true)");		
		Logger.info("Arc " + Arc.FULL_360.getName() + " contains 359 degrees = " + Arc.FULL_360.contains(359) + " (true)");		
		Logger.info("Arc " + Arc.FULL_360.getName() + " contains 1 degrees = " + Arc.FULL_360.contains(1) + " (true)");		

		Logger.info("");

		// direction
		Logger.info("Direction test...");
		Logger.info("Relative angle incoming 0 for 0 = " + Direction.getRelativeAngle(139, 0) + " (319)");
		Logger.info("Relative angle incoming 0 for 0 = " + Direction.getRelativeAngle(139, 60) + " (259)");
		
		Logger.info("Relative angle incoming 0 for 0 = " + new Direction(0).getRelativeAngle(0) + " (180)");		
		Logger.info("Relative angle incoming 60 for 0 = " + new Direction(0).getRelativeAngle(60) + " (240)");		
		Logger.info("Relative angle incoming 90 for 0 = " + new Direction(0).getRelativeAngle(90) + " (270)");		
		Logger.info("Relative angle incoming 150 for 0 = " + new Direction(0).getRelativeAngle(150) + " (330)");		
		Logger.info("Relative angle incoming 180 for 0 = " + new Direction(0).getRelativeAngle(180) + " (0)");		
		Logger.info("Relative angle incoming 210 for 0 = " + new Direction(0).getRelativeAngle(210) + " (30)");		
		Logger.info("Relative angle incoming 270 for 0 = " + new Direction(0).getRelativeAngle(270) + " (90)");		
		Logger.info("Relative angle incoming 0 for 90 = " + new Direction(90).getRelativeAngle(0) + " (90)");		
		Logger.info("Relative angle incoming 60 for 90 = " + new Direction(90).getRelativeAngle(60) + " (150)");		
		Logger.info("Relative angle incoming 90 for 90 = " + new Direction(90).getRelativeAngle(90) + " (180)");		
		Logger.info("Relative angle incoming 150 for 90 = " + new Direction(90).getRelativeAngle(150) + " (60)");		
		Logger.info("Relative angle incoming 180 for 90 = " + new Direction(90).getRelativeAngle(180) + " (270)");		
		Logger.info("Relative angle incoming 210 for 90 = " + new Direction(90).getRelativeAngle(210) + " (120)");		
		Logger.info("Relative angle incoming 270 for 90 = " + new Direction(90).getRelativeAngle(270) + " (0)");		
				
		Logger.info("");

		Logger.info("");

		// fire control
		Logger.info("Fire Control test...");
		FireControl fireControl = new FireControl(Integer.valueOf(4), Integer.valueOf(2), null);
		Logger.info("FireControl=" + fireControl);
		Logger.info("FireControl.getModifier(" + Size.ENORMOUS + ")=" + fireControl.getToHitModifier(Size.ENORMOUS) + " (4)");
		Logger.info("FireControl.canTarget(" + Size.LARGE + ")=" + fireControl.isValidTarget(Size.LARGE) + " (true)");
		Logger.info("FireControl.getModifier(" + Size.MEDIUM + ")=" + fireControl.getToHitModifier(Size.MEDIUM) + " (2)");
		Logger.info("FireControl.canTarget(" + Size.SMALL + ")=" + fireControl.isValidTarget(Size.SMALL) + " (false)");
//		Logger.info("FireControl.getModifier(" + Size.SMALL + ")=" + fireControl.getToHitModifier(Size.SMALL) + " (exception)");			
	}

}
