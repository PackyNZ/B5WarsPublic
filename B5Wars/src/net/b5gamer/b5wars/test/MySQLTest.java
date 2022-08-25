package net.b5gamer.b5wars.test;

import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.structural.StructuralUnit;

public class MySQLTest {

//	public static void main(String[] args) {
//		Connection connection = null;
//		Statement statement = null;
//		ResultSet resultSet = null;
//		
//		try {
//			Class.forName("com.mysql.jdbc.Driver").newInstance();
//			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/b5wars", "B5W", "4Ever");
//			statement = connection.createStatement();
//			statement.execute("Select Name From Test");
//			resultSet = statement.getResultSet();
//			
//			while (resultSet.next()) {
//				System.out.println("Name=" + resultSet.getString(1));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				resultSet.close();
//			} catch (SQLException e) {}
//			try {
//				statement.close();
//			} catch (SQLException e) {}
//			try {
//				connection.close();
//			} catch (SQLException e) {}			
//		}
//	}

//	public static void main(String[] args) {
//		Connection connection = null;
//		PreparedStatement statement = null;
//				
//		try {
//			List<Unit> units = UnitXMLReader.read("units\\Brakiri\\Brakiri Tashkava Advanced Lance Cruiser.xml");
//			StructuralUnit unit = (StructuralUnit) units.get(0);
//
//			Class.forName("com.mysql.jdbc.Driver").newInstance();
//			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/b5wars", "B5W", "4Ever");
//			statement = connection.prepareStatement("Insert Into Test(Name, Object) Values(?, ?)");
//			statement.setString(1, unit.getName());
//			statement.setObject(2, unit);
//			statement.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				statement.close();
//			} catch (SQLException e) {}
//			try {
//				connection.close();
//			} catch (SQLException e) {}			
//		}
//	}
	
	public static void main(String[] args) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/b5wars", "B5W", "4Ever");
			statement = connection.createStatement();
			statement.execute("Select * From Test");
			resultSet = statement.getResultSet();
			
			while (resultSet.next()) {
				System.out.println("Name=" + resultSet.getString(1));

				ObjectInputStream in = new ObjectInputStream(resultSet.getBinaryStream(2));
				UnitTester.outputUnit((StructuralUnit) (Unit) in.readObject()); 				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {}
			try {
				statement.close();
			} catch (SQLException e) {}
			try {
				connection.close();
			} catch (SQLException e) {}			
		}
	}
	
}
