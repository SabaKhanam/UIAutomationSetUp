package com.careautomate.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.careautomate.baseclasses.ActionKeywords;
import com.careautomate.baseclasses.ManageBrowser;
import com.careautomate.utils.PropertiesFile;

public class SqlDbConnection extends ManageBrowser {
	ActionKeywords performAction;
	Statement statement = null;
	Connection connection = null;
	ResultSet resultSet = null;
	boolean isconnected = false;
	PropertiesFile pro = new PropertiesFile();

	public void DbConnection(WebDriver driver) {
		this.driver = driver;
	}

	public Statement sqlConn(String db) {
		// Initialize the connection variable

		// JDBC URL, username, and password of MySQL server
		String jdbcUrl = "jdbc:sqlserver://" + pro.getDBServer() + ";databaseName=" + db + ";encrypt=false";
		String user = pro.getDBUsername();
		String password = pro.getDBPassword();

		try {
			// Register JDBC driver
			// Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			// Open a connection
			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection(jdbcUrl, user, password);
			if (connection != null) {
				System.out.println("Connected to SQL Server successfully!");
			}
			// Execute a query
			System.out.println("Creating statement...");
			statement = connection.createStatement();
		} catch (Exception e) {
			System.out.println("Error connecting to the database: " + e.getMessage());
			throw new RuntimeException("Error connecting to the database: " + e);

		}
		return statement;
	}

	public String getID(String sqlQuery, String getDetails) {
		String ID = null;
		try {
			statement = getDetails.equals("zone") ? sqlConn(pro.getPtlDB()) : sqlConn(pro.getDB());
			resultSet = statement.executeQuery(sqlQuery);
			if (resultSet != null) {
				isconnected = true;
				System.out.println("query executed");
			}
			while (resultSet.next()) {
				ID = getDetails.equals("zone") ? resultSet.getString("ParentStorageId")
						: resultSet.getString("username");
			}
		} catch (Exception e) {
			System.out.println("Error connecting to the database: " + e.getMessage());
			throw new RuntimeException("Error connecting to the database: " + e);

		} finally {
			try {
				// Close the resources
				resultSet.close();
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}
		}
		return ID;
	}

	public void updateSql(String sqlQuery) {
		int rowsAffected = 0;

		try {
			statement = sqlConn(pro.getPtlDB());
			rowsAffected = statement.executeUpdate(sqlQuery);
			if (rowsAffected > 0) {
				System.out.println("Transaction committed successfully and records updated" + rowsAffected);
				isconnected = true;
			}
		} catch (Exception e) {
			System.out.println("Error connecting to the database: " + e.getMessage());
			throw new RuntimeException("Error connecting to the database: " + e);

		} finally {
			try {
				// Close the resources
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}
		}
	}

	public void updateAttributeOfZone(String locId, String value) {
		int rowsAffected = 0;

		try {
			statement = sqlConn(pro.getPtlDB());
			rowsAffected = statement
					.executeUpdate("update [Storage].[dbo].[Storage] set Attribute2=" + value + " where StorageId=\r\n"
							+ "(SELECT ParentStorageId FROM [Storage].[dbo].[Storage] where Storage='" + locId + "')");
			if (rowsAffected > 0) {
				System.out.println("Transaction committed successfully and records updated" + rowsAffected);
				isconnected = true;
			}
		} catch (Exception e) {
			System.out.println("Error connecting to the database: " + e.getMessage());
			throw new RuntimeException("Error connecting to the database: " + e);

		} finally {
			try {
				// Close the resources
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}
		}
	}

	public String getPrinterOfZone(String locId) {
		String ID = null;

		try {
			statement = sqlConn(pro.getPtlDB());
			resultSet = statement.executeQuery("SELECT Attribute1  FROM [Storage].[dbo].[Storage] where StorageId=\r\n"
					+ "(SELECT ParentStorageId FROM [Storage].[dbo].[Storage] where Storage='" + locId + "')");
			if (resultSet != null) {
				isconnected = true;
				System.out.println("query executed");
			}
			while (resultSet.next()) {
				ID = resultSet.getString("Attribute1");
			}
		} catch (Exception e) {
			System.out.println("Error connecting to the database: " + e.getMessage());
			throw new RuntimeException("Error connecting to the database: " + e);

		} finally {
			try {
				// Close the resources
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}
		}
		return ID;
	}

	public void getUnexpiredSession(String badgeId) {
		int rowsAffected = 0;

		try {
			statement = sqlConn(pro.getPtlDB());
			rowsAffected = statement.executeUpdate(
					"update ptl.dbo.operatorSignedOn set signedOut=(select signedOn from ptl.dbo.operatorSignedOn  where operatorID="
							+ badgeId + " and signedOut is NULL ) where operatorID=" + badgeId
							+ " and signedOut is null");
			if (rowsAffected > 0) {
				System.out.println("Transaction committed successfully and records updated" + rowsAffected);
				isconnected = true;
			}
		} catch (Exception e) {
			System.out.println("Error connecting to the database: " + e.getMessage());
			throw new RuntimeException("Error connecting to the database: " + e);

		} finally {
			try {
				// Close the resources
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}
		}
	}

	public String Authenticate(String badgeId) {
		String ID = null;
		try {
			statement = sqlConn(pro.getPtlDB());
			resultSet = statement.executeQuery("select sessionID from ptl.dbo.operatorSignedOn where operatorID='"
					+ badgeId + "' and signedOut is NULL order by 1 desc ");
			if (resultSet != null) {
				isconnected = true;
				System.out.println("query executed");
			} else {
				// Thread.sleep(5000);
				resultSet = statement.executeQuery("select sessionID from ptl.dbo.operatorSignedOn where operatorID='"
						+ badgeId + "' and signedOut is NULL order by 1 desc ");
			}
			while (resultSet.next()) {
				ID = resultSet.getString("sessionID");
			}
		} catch (Exception e) {
			System.out.println("Error connecting to the database: " + e.getMessage());
			throw new RuntimeException("Error connecting to the database: " + e);

		} finally {
			try {
				// Close the resources
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}
		}
		return ID;
	}

	public boolean getOperatorID(String id) {
		String ID = null;
		try {
			statement = sqlConn(pro.getPtlDB());
			resultSet = statement.executeQuery(
					"SELECT *  FROM [Ptl].[dbo].[OperatorSignedOn] where signedOut IS NULL and operatorID='" + id
							+ "'");
			isconnected = resultSet != null ? true : false;
		} catch (Exception e) {
			System.out.println("Error connecting to the database: " + e.getMessage());
			throw new RuntimeException("Error connecting to the database: " + e);

		} finally {
			try {
				// Close the resources
				resultSet.close();
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}
		}
		return isconnected;
	}

	public void deleteGroup(String name) {
		String ID = null;
		try {
			statement = sqlConn(pro.getDB());
			resultSet = statement.executeQuery("select * from [Access].dbo.[Group] where id='" + name + "'");
			isconnected = resultSet != null ? true : false;
			if (isconnected) {
				updateSql("delete [Access].dbo.[GroupAuthorization] where groupid='" + name + "'");
				updateSql("delete [Access].dbo.[Group] where id='" + name + "'");
				getTest().info("Group Deleted from DB");
			} else {
				getTest().info("Group Deleted Successfully from Script,no failures");
			}
		} catch (Exception e) {
			System.out.println("Error connecting to the database: " + e.getMessage());
			throw new RuntimeException("Error connecting to the database: " + e);

		} finally {
			try {
				// Close the resources
				resultSet.close();
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}
		}
	}

	public void deleteStorageId(String... parameters) {

		try {
			for (String name : parameters) {
				statement = sqlConn(pro.getStorageDB());
				resultSet = statement
						.executeQuery("SELECT StorageId FROM [Storage].[dbo].[Storage] where Storage='" + name + "'");
				isconnected = resultSet != null ? true : false;
				if (isconnected) {
					updateSql("delete [Storage].[dbo].[Storage] where Storage='" + name + "'");
					getTest().info("Storage Deleted from DB");
				} else {
					getTest().info("Storage Deleted Successfully from Script,no failures");
				}
			}
		} catch (Exception e) {
			System.out.println("Error connecting to the database: " + e.getMessage());
			throw new RuntimeException("Error connecting to the database: " + e);

		} finally {
			try {
				// Close the resources
				resultSet.close();
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}
		}
	}

	public void deleteUser(String username) {
		String ID = null;
		try {
			statement = sqlConn(pro.getDB());
			resultSet = statement.executeQuery("select * from access.dbo.[User] where username='" + username + "'");
			isconnected = resultSet != null ? true : false;
			if (isconnected) {
				updateSql("delete access.dbo.userGroup where username='" + username + "'");
				updateSql("delete access.dbo.[User] where username='" + username + "'");
				getTest().info("User Deleted from DB");
			} else {
				getTest().info("User Deleted Successfully from Script,no failures");
			}
		} catch (Exception e) {
			System.out.println("Error connecting to the database: " + e.getMessage());
			throw new RuntimeException("Error connecting to the database: " + e);

		} finally {
			try {
				// Close the resources
				resultSet.close();
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}
		}
	}

	public void getPickTicketData(String Ticket, String value) {
		int qty = 0, Attribute4 = 0, Attribute2 = 0;
		int qtyComp = 0, WorkLineId = 0, WorkListId = 0;
		String Attribute1 = null, ID = null, Attribute5 = null;
		try {

			statement = sqlConn(pro.getWorkDB());
			resultSet = statement.executeQuery(
					"select WorkLineId,Quantity,QuantityCompleted,Attribute3 from [Work].[dbo].[WorkLine] where WorkListId in (select WorkListId from [Work].[dbo].[WorkList] where WorkList like '"
							+ Ticket + "-%')");
			if (resultSet != null) {
				isconnected = true;
				System.out.println("query executed for workLine");
			}
			while (resultSet.next()) {
				qtyComp = Integer.parseInt(resultSet.getString("QuantityCompleted"));
				ID = resultSet.getString("Attribute3");
				qty = Integer.parseInt(resultSet.getString("Quantity"));
				WorkLineId = Integer.parseInt(resultSet.getString("WorkLineId"));
				if (value.equals("ShortPick"))
					Assert.assertTrue(ID.equals("COMPLETE"),
							"qty completed is not correct for short pick of WorkLineId " + WorkLineId
									+ " QuantityCompleted " + qtyComp + " WorkLineStatusId " + ID + " Quantity " + qty);
				else if (value.equals("ZeroPick"))
					Assert.assertTrue(ID.equals("COMPLETE"),
							"qty completed is not correct for zero pick of WorkLineId " + WorkLineId
									+ " QuantityCompleted " + qtyComp + " WorkLineStatusId " + ID + " Quantity " + qty);
				else if (value.equals("FullPick"))
					Assert.assertTrue(qty == qtyComp && ID.equals("COMPLETE"),
							"qty completed is not correct for Full pick of WorkLineId " + WorkLineId
									+ " QuantityCompleted " + qtyComp + " WorkLineStatusId " + ID + " Quantity " + qty);

			}

			resultSet = statement.executeQuery(
					"select WorkListId,Attribute4,Attribute1,Attribute2 , Attribute5 from [Work].[dbo].[WorkList] where WorkList like '"
							+ Ticket + "-%'");

			if (resultSet != null) {
				isconnected = true;
				System.out.println("query executed for workList");
			}
			while (resultSet.next()) {
				Attribute4 = Integer.parseInt(resultSet.getString("Attribute4"));
				Attribute1 = resultSet.getString("Attribute1");
				Attribute2 = Integer.parseInt(resultSet.getString("Attribute2"));
				Attribute5 = resultSet.getString("Attribute5");

				WorkListId = Integer.parseInt(resultSet.getString("WorkListId"));
				if (value.equals("ShortPick"))
					Assert.assertTrue(Attribute2 == 3 && Attribute1.equals("COMPLETE") && Attribute5.equals("COMPLETE"),
							"qty completed is not correct for short pick of WorkListId" + WorkListId + " Attribute2 "
									+ Attribute2 + " Attribute1 " + Attribute1 + " Attribute4 " + Attribute4
									+ " Attribute5 " + Attribute5);
				else if (value.equals("ZeroPick"))
					Assert.assertTrue(Attribute2 == 3 && Attribute1.equals("COMPLETE") && Attribute5.equals("COMPLETE"),
							"qty completed is not correct for zero pick of WorkListId " + WorkListId + " Attribute2 "
									+ Attribute2 + " Attribute1 " + Attribute1 + " Attribute4 " + Attribute4
									+ " Attribute5 " + Attribute5);
				else if (value.equals("FullPick"))
					Assert.assertTrue(
							Attribute2 == 3 && Attribute1.equals("COMPLETE") && Attribute4 > 0
									&& Attribute5.equals("COMPLETE"),
							"qty completed is not correct for Full pick  of WorkListId " + WorkListId + " Attribute2 "
									+ Attribute2 + " Attribute1 " + Attribute1 + " Attribute4 " + Attribute4
									+ " Attribute5 " + Attribute5);

			}
		} catch (Exception e) {
			System.out.println("Error connecting to the database: " + e.getMessage());
			throw new RuntimeException("Error connecting to the database: " + e);

		} finally {
			try {
				// Close the resources
				resultSet.close();
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}
		}
	}

	public void getDeregisteredPickTicketData(String Ticket) {
		int WorkListId = 0, Attribute4 = 0, Attribute2 = 0, WorkListStatusId = 0;
		String Attribute1 = null;
		try {

			statement = sqlConn(pro.getWorkDB());
			resultSet = statement.executeQuery(
					"select WorkListId,Attribute4,Attribute1,Attribute2 , WorkListStatusId from [Work].[dbo].[WorkList] where WorkList like '"
							+ Ticket + "-%'");

			if (resultSet != null) {
				isconnected = true;
				System.out.println("query executed for workList");
			}
			while (resultSet.next()) {
				Attribute4 = Integer.parseInt(resultSet.getString("Attribute4"));
				Attribute1 = resultSet.getString("Attribute1");
				Attribute2 = Integer.parseInt(resultSet.getString("Attribute2"));
				WorkListStatusId = Integer.parseInt(resultSet.getString("WorkListStatusId"));

				Assert.assertTrue(
						Attribute2 == 1 && Attribute1.equals("OPEN") && Attribute4 >= 0 && WorkListStatusId == 1,
						"qty completed is not correct for Full pick  of WorkListId " + WorkListId + " Attribute2 "
								+ Attribute2 + " Attribute1 " + Attribute1 + " Attribute4 " + Attribute4
								+ " WorkListStatusId " + WorkListStatusId);

			}
		} catch (Exception e) {
			System.out.println("Error connecting to the database: " + e.getMessage());
			throw new RuntimeException("Error connecting to the database: " + e);

		} finally {
			try {
				// Close the resources
				resultSet.close();
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}
		}
	}

	public String getPickPriorityData(String locId, String operatorId, String value) {
		String zoneID = null;
		int pickPriority = 0, status = 0;
		try {

			String query = "SELECT ParentStorageId FROM [Storage].[dbo].[Storage] where Storage='" + locId + "'";
			zoneID = getID(query, "zone");

			System.out.println(zoneID);
			statement = sqlConn(pro.getPtlDB());
			resultSet = statement.executeQuery(
					"SELECT status,pickPriority from ptl.dbo.PickZoneAssignment where ID=(select max(ID) from ptl.dbo.PickZoneAssignment where zoneID='"
							+ zoneID + "' and operatorID='" + operatorId + "')");
			if (resultSet != null) {
				isconnected = true;
				System.out.println("query executed");
			}
			while (resultSet.next()) {
				status = Integer.parseInt(resultSet.getString("status"));
				pickPriority = Integer.parseInt(resultSet.getString("pickPriority"));
				if (value.equals("allocate")) {
					Assert.assertTrue(status == 1 && pickPriority == 1,
							"zone is not properly allocated, status " + status + " pickPriority " + pickPriority);
				} else if (value.equals("deallocate")) {
					Assert.assertTrue(status == 0 && pickPriority == 1,
							"zone is not properly deallocated, status " + status + " pickPriority " + pickPriority);
				}
			}
		} catch (Exception e) {
			System.out.println("Error connecting to the database: " + e.getMessage());
			throw new RuntimeException("Error connecting to the database: " + e);

		} finally {
			try {
				// Close the resources
				resultSet.close();
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}
		}
		return zoneID;
	}

	public int getPickItems(String Ticket, String locId) throws SQLException {
		String ID = null, zoneID;
		int i = 0;
		try {
			String query = "SELECT ParentStorageId FROM [Storage].[dbo].[Storage] where Storage='" + locId + "'";
			zoneID = getID(query, "zone");

			System.out.println(zoneID);
			statement = sqlConn(pro.getWorkDB());
			resultSet = statement.executeQuery("SELECT * FROM [Work].[dbo].[WorkLine]  where\r\n"
					+ "WorkListId in (select WorkListId from [Work].[dbo].[WorkList] where WorkList like '" + Ticket
					+ "-" + zoneID + "')");

			if (resultSet != null) {
				isconnected = true;
				while (resultSet.next()) {
					i++;
				}
				System.out.println("query executed and value is " + i);
			}

		} catch (Exception e) {
			System.out.println("Error connecting to the database: " + e.getMessage());
			throw new RuntimeException("Error connecting to the database: " + e);

		} finally {
			try {
				// Close the resources
				resultSet.close();
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}
		}
		return i;
	}

	public void updateUomData(String Ticket, int quantity, int shelfPack, String uom, String vasTag) {
		int rowsAffected = 0;
		try {
			statement = sqlConn(pro.getWorkDB());
			rowsAffected = statement.executeUpdate("\r\n" + " update [Work].[dbo].[WorkLine] set Quantity=" + quantity
					+ ",Attribute2='" + vasTag + "' from [Work].[dbo].[WorkLine] w\r\n"
					+ " inner join [inventory].[dbo].[item] i on  w.ItemId=i.ItemId \r\n"
					+ " where w.WorkLineId in(select WorkLineId FROM [Work].[dbo].[WorkLine]  where\r\n"
					+ "WorkListId in (select WorkListId from [Work].[dbo].[WorkList] where WorkList like '%" + Ticket
					+ "-%'))");
			if (rowsAffected > 0) {
				System.out.println("Transaction committed successfully and Quantity updated in Records " + quantity
						+ "and vasTag :" + vasTag);
				isconnected = true;
			}
			statement = sqlConn(pro.getWorkDB());
			rowsAffected = statement.executeUpdate(" update [inventory].[dbo].[item] set Attribute3=" + shelfPack
					+ " from [Work].[dbo].[WorkLine] w\r\n"
					+ " inner join [inventory].[dbo].[item] i on  w.ItemId=i.ItemId \r\n"
					+ " where w.WorkLineId in(select WorkLineId FROM [Work].[dbo].[WorkLine]  where\r\n"
					+ "WorkListId in (select WorkListId from [Work].[dbo].[WorkList] where WorkList like '%" + Ticket
					+ "-%'))");
			if (rowsAffected > 0) {
				System.out.println("Transaction committed successfully and shelfPack updated in Records " + shelfPack);
				isconnected = true;
			}
			statement = sqlConn(pro.getWorkDB());
			rowsAffected = statement.executeUpdate(
					"update [inventory].[dbo].[itemuom] set Name ='" + uom + "' from [inventory].[dbo].[itemuom] u\r\n"
							+ " inner join [Work].[dbo].[WorkLine] w on  w.ItemId=u.ItemId \r\n"
							+ " where w.WorkLineId in(select WorkLineId FROM [Work].[dbo].[WorkLine]  where\r\n"
							+ "WorkListId in (select WorkListId from [Work].[dbo].[WorkList] where WorkList like '%"
							+ Ticket + "-%'))");
			if (rowsAffected > 0) {
				System.out.println("Transaction committed successfully and UOM updated in Records " + uom);
				isconnected = true;
			}
		} catch (Exception e) {
			System.out.println("Error connecting to the database: " + e.getMessage());
			throw new RuntimeException("Error connecting to the database: " + e);
		} finally {
			try {
				// Close the resources
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				System.out.println("Error closing database resources: " + e.getMessage());
			}
		}
	}

}
