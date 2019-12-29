package com.functions;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.functions.empPanelFunctions;
import javax.swing.JOptionPane;

public class mysql {
	
	/**
	 * @param args
	 * @throws ParseException 
	 */
	public String PORT = "3306";
	public String IP = "192.168.2.11:" + PORT;
	public String DATABASE = "BestHotels";
	public String USER="softEng";
	public String PASS="yolo123";
	
	public String[] dbGetCoupons() {
		try{
			int count = 0;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			Statement stm=conn.createStatement();
			ResultSet rs=stm.executeQuery("select * from coupons");
			while(rs.next()){
				count++;
			}
			String[] books = new String[count];
			rs=stm.executeQuery("select * from coupons");
			while(rs.next()){
				books[--count] = rs.getString("code") + ":" + rs.getString("discount") +"%";
			}
			return books;
		}catch(Exception e){
			
			String[] error = new String[1];
			return error;
		}
	}
	
	public String[] dbGetDiscounts() {
		try{
			int count = 0;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			Statement stm=conn.createStatement();
			ResultSet rs=stm.executeQuery("select * from roomTypes");
			while(rs.next()){
				if(rs.getString("discount").compareTo("0") != 0) {
				count++;
				}
			}
			String[] books = new String[count];
			rs=stm.executeQuery("select * from roomTypes");
			while(rs.next()){
				if(rs.getString("discount").compareTo("0") != 0) {
				books[--count] = rs.getString("hotel") + ":" + rs.getString("type") + ":" + rs.getString("discount") +"%";
				}
			}
			return books;
		}catch(Exception e){
			
			String[] error = new String[1];
			return error;
		}
	}
	
	public Date convertDate(String input)  {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date;
			try {
				date = formatter.parse(input);
				return date;
			} catch (ParseException e) {
				return null;
			}
            
	}
	
	public boolean dbAddDiscount(String Hotel,String Type,String Discount) {
		try {
			String query = "update roomTypes set discount="+ Discount +" where hotel='" + Hotel + "' and type='"+ Type +"'";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			PreparedStatement ps=conn.prepareStatement(query);
			ps.execute();
				return true;
			}catch(Exception e) {
				return false;
			}
	}
	
	public boolean dbAddDiscountAll(String Hotel,String Discount) {
		try {
			String query = "update roomTypes set discount="+ Discount +" where hotel='" + Hotel + "'";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			PreparedStatement ps=conn.prepareStatement(query);
			ps.execute();
				return true;
			}catch(Exception e) {
				return false;
			}
	}
	
	public void dbRemoveDiscount(String Hotel,String Type) {
		try {
			String query = "update roomTypes set discount=0 where hotel='" + Hotel + "' and type='"+ Type +"'";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			PreparedStatement ps=conn.prepareStatement(query);
			ps.execute();
			}catch(Exception e) {
			}
	}
	
	public boolean dbAddCoupon(String code,String discount) {
		try {
			String query = "insert into coupons (code,discount) values ('"+ code +"', "+ discount + ")";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			PreparedStatement ps=conn.prepareStatement(query);
			ps.execute();
				return true;
			}catch(Exception e) {
				
				return false;
			}
	}
	
	public void dbRemoveCoupon(String code) {
		try {
			String query = "delete from coupons where code='" + code + "'";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			PreparedStatement ps=conn.prepareStatement(query);
			ps.execute();
			}catch(Exception e) {
			}
	}
	
	public boolean dbAddUser(String username,String level,String pass,String Fname,String Lname) {
		try {
			empPanelFunctions EmployeePanelFunctions =new empPanelFunctions();
			String query = "insert into users (level,username,password,Fname,Lname) values ('"+ level +"', '"+ username +"', '"+ EmployeePanelFunctions.toMd5(pass) +"', '"+ Fname +"', '"+ Lname +"')";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			PreparedStatement ps=conn.prepareStatement(query);
			ps.execute();
				return true;
			}catch(Exception e) {
				
				return false;
			}
	}
	
	public void dbRemoveUser(String user) {
		try {
			String query = "delete from users where username='" + user + "'";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			PreparedStatement ps=conn.prepareStatement(query);
			ps.execute();
			}catch(Exception e) {
			}
	}
	
	public boolean dbRemoveOldBooks(String today) {
		try {
			String query = "delete from books where CheckIn='" + today + "' or CheckOut='" + today + "'";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			PreparedStatement ps=conn.prepareStatement(query);
			ps.execute();
			return true;
			}catch(Exception e) {
				return false;
			}
	}
	
	public String[] dbGetAllUsers() {
		try{
			int count = 0;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			Statement stm=conn.createStatement();
			ResultSet rs=stm.executeQuery("select * from users");
			while(rs.next()){
				count++;
			}
			String[] books = new String[count];
			rs=stm.executeQuery("select * from users");
			while(rs.next()){
				books[--count] = rs.getString("username") + ":" + rs.getString("level");
			}
			return books;
		}catch(Exception e){
			
			String[] error = new String[1];
			return error;
		}
	}
	
	
	
	public boolean dbConfirmArrival(String key) {
		try {
			String query = "update books set CheckInMade='YES' where code='" + key + "'";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			PreparedStatement ps=conn.prepareStatement(query);
			ps.execute();
			return true;
			}catch(Exception e) {
				return false;
			}
	}
	
	public boolean dbCreateHotel(String name) {
		try {
			String query = "insert into hotels (name) values ('" + name +"')";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			PreparedStatement ps=conn.prepareStatement(query);
			ps.execute();
				return true;
			}catch(Exception e) {
				
				return false;
			}
	}
	
	public String dbGetDiscount(String hotel,String type) {
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			Statement stm=conn.createStatement();
			ResultSet rs=stm.executeQuery("select discount from roomTypes where hotel='" + hotel + "' and type='" + type +"'");
			if(rs.next()){
				return rs.getString("discount");
			}
			return "error!";
		}catch(Exception e){
			
			return "error!";
		}
	}
	public String dbGetRoomCost(String hotel,String type) {
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			Statement stm=conn.createStatement();
			ResultSet rs=stm.executeQuery("select cost from roomTypes where hotel='" + hotel + "' and type='" + type +"'");
			if(rs.next()){
				return rs.getString("cost");
			}
			return "error!";
		}catch(Exception e){
			
			return "error!";
		}
	}
	
	public String[] dbGetRoomTypes(String hotel) {
		try{
			
			int count = 0;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			Statement stm=conn.createStatement();
			ResultSet rs=stm.executeQuery("select type from roomTypes where hotel='" + hotel + "'");
			while(rs.next()){
				count++;		
			}
			String[] hotels = new String[count];
			rs=stm.executeQuery("select type from roomTypes where hotel='" + hotel + "'");
			while(rs.next()){
				hotels[--count] = rs.getString("type");
			}
			return hotels;
		}catch(Exception e){
			String[] error = new String[1];
			return error;
		}
	}
	
	public String[] dbGetBooks(String hotel) {
		try{
			int count = 0;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			Statement stm=conn.createStatement();
			ResultSet rs=stm.executeQuery("select * from books where hotel='" + hotel +"'");
			while(rs.next()){
				count++;
			}
			String[] books = new String[count];
			rs=stm.executeQuery("select * from books where hotel='" + hotel +"'");
			while(rs.next()){
				books[--count] = "Room " + rs.getString("number")  + " || Check In: " + rs.getString("CheckIn") + 
						" || Check Out: " + rs.getString("CheckOut") + " || Cost: " + rs.getString("cost") + " || Name: " + rs.getString("FirstName") +
						" " + rs.getString("LastName") + " || Phone: " + rs.getString("phone") + " || Email: " + rs.getString("email") + " || key:" +rs.getString("code");
				
			}
			return books;
		}catch(Exception e){
			
			String[] error = new String[1];
			return error;
		}
	}
	
	
	public double dbGetBookCost(String key) {
		try {
		String query = "select * from books where code='"+ key +"'";
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
		Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
		Statement stm=conn.createStatement();
		ResultSet rs=stm.executeQuery(query);
		rs.next();
		return Double.parseDouble(rs.getString("cost"));
		}catch(Exception e) {
			
			return -1;
		}
	}
	
	public void dbSetRoomOnhold(String hotel,String room, String key){
		try {
		String query = "insert into books (hotel, number, code, CheckIn, CheckOut) values ('" + hotel + "', " + room + ", '" + key +"', 'onhold', 'onhold')";
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
		Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
		PreparedStatement ps=conn.prepareStatement(query);
		ps.execute();
		}catch(Exception e) {
			
		}
	}
	
	
	public boolean dbCheckIfBookExists(String key){
		try {
			String query = "select * from books where code='"+ key +"'";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			Statement stm=conn.createStatement();
			ResultSet rs=stm.executeQuery(query);
			if(rs.next()){
				return true;
			}else {
				return false;
			}
			}catch(Exception e) {
				
				return false;
			}
		}
	
	
	public boolean dbRemoveBook(String key){
		try {
		String query = "delete from books where code='" + key + "'";
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
		Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
		PreparedStatement ps=conn.prepareStatement(query);
		ps.execute();
		return true;
		}catch(Exception e) {
			
			return false;
		}
	}
	
	public void dbRook(String hotel,String room,String CheckIn,String CheckOut,double cost,String phone,String mail,String Fname,String Lname,String key){
		try {
		String query = "update books set FirstName='" + Fname + "', LastName='" + Lname + "', email='" + mail + "', phone='" + phone + "', cost=" + cost + ",  CheckIn='" + CheckIn +"' , CheckOut = '" + CheckOut +"' where code='" + key + "'";
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
		Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
		PreparedStatement ps=conn.prepareStatement(query);
		ps.execute();
		}catch(Exception e) {
			
		}
	}
	
	public int rCount =0;
	public int aCount =0;
	public boolean dbCheckDates(String CheckIn,String CheckOut,String From,String Until) {	
		Date fromIn = convertDate(From);
		Date UntilIn = convertDate(Until);
		Date from = convertDate(CheckIn);
		Date until = convertDate(CheckOut);
		if((CheckIn.compareTo("onhold") == 0) || (CheckOut.compareTo("onhold") == 0)) {
			return false;
		}
		if((fromIn.after(until) && UntilIn.after(until)) || (fromIn.before(from) && UntilIn.before(from))){
			aCount++;
			return true;
		}else {
			
			return false;
		}
		}
	
		
	public boolean dbCheckIfRoomAvailable(String hotel,String room,String in,String out){
		try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
		Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);		
		Statement stm2=conn.createStatement();
		ResultSet rs2=stm2.executeQuery("select * from books where number=" + room + " and hotel='" + hotel + "'");
		List<String> aRooms = new ArrayList<>();
			 rCount = 0;
			while(rs2.next()) {
				 rCount++;
			}
			rs2.beforeFirst();
				if(rs2.next()){
					rs2.beforeFirst();
				while(rs2.next()) {
				 if((dbCheckDates(rs2.getString("CheckIn"), rs2.getString("CheckOut"), in, out)) && (rCount == aCount)){
					 aRooms.add("Room " + room);
				      break;
				 }
				}					
				}else {
					 aRooms.add("Room " + room);
				}	
		if(aRooms.contains("Room " + room)) {
			return true;
		}else {
			return false;
		}
		}catch(Exception e){
			
			return false;
		}
}
	
	
	public String[] dbGetRoomsByType(String hotel,String type) {
		try {
		String query = "select * from rooms where hotel='" + hotel + "' and type='" + type + "'";
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
		Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
		Statement stm=conn.createStatement();
		ResultSet rs=stm.executeQuery(query);
		List<String> Rooms = new ArrayList<>();
		while(rs.next()){
			 Rooms.add("Room " + rs.getString("number"));
		}
		String[] rooms = new String[Rooms.size()];
		rooms = Rooms.toArray(rooms);
		return rooms;
		}catch(Exception e) {
			
			String[] error = new String[1];
			return error;
		}
	}
	
	public boolean dbEditType (String hotel,String type,String NewType,String NewCost) {
		try {
		String query = "update roomTypes set type='"+ NewType +"', cost='" + NewCost + "' where hotel='" + hotel + "' and type='"+ type +"'";
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
		Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
		PreparedStatement ps=conn.prepareStatement(query);
		ps.execute();
			return true;
		}catch(Exception e) {
			
			return false;
		}
	}
	
	public String[] dbGetAvailableRooms(String hotel,String type,int Single, int Double, String From, String Until) {
		try{
			String query = "select * from rooms where hotel='" + hotel + "' and type='" + type + "' and SingleBedNum='" + Single + "' and DoubleBedNum='" + Double + "'";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			Statement stm=conn.createStatement();
			Statement stm2=conn.createStatement();
			ResultSet rs=stm.executeQuery(query);
			List<String> Rooms = new ArrayList<>();
			while(rs.next()){
				ResultSet rs2=stm2.executeQuery("select * from books where number=" + rs.getString("number") + " and hotel='" + hotel + "'");
				 rCount = 0;
				while(rs2.next()) {
					 rCount++;
				}
				rs2.beforeFirst();
					if(rs2.next()){
						rs2.beforeFirst();
					while(rs2.next()) {
					 if((dbCheckDates(rs2.getString("CheckIn"), rs2.getString("CheckOut"), From, Until)) && (rCount == aCount)){
						 Rooms.add("Room " + rs.getString("number"));
					      break;
					 }
					}					
					}else {
						 Rooms.add("Room " + rs.getString("number"));
					}	
				}
			String[] rooms = new String[Rooms.size()];
			rooms = Rooms.toArray(rooms);
			if(Rooms.size() == 0) {
				JOptionPane.showMessageDialog(null, "0 rooms found!","No rooms found", JOptionPane.INFORMATION_MESSAGE);
			}
			return rooms;
		}catch(Exception e){
			
			String[] error = new String[1];
			return error;
		}
	}
	
	public String[] dbGetHotels() {
		try{
			int count = 0;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			Statement stm=conn.createStatement();
			ResultSet rs=stm.executeQuery("select name from hotels");
			while(rs.next()){
				count++;
			}
			String[] hotels = new String[count];
			rs=stm.executeQuery("select name from hotels");
			while(rs.next()){
				hotels[--count] = rs.getString("name");
			}
			return hotels;
		}catch(Exception e){
			String[] error = new String[1];
			
			e.printStackTrace();
			return error;
		}
	}
	
	public boolean dbRemoveHotel(String hotel){
		try {
		String query1 = "delete from hotels where name='" + hotel + "'";
		String query2 = "delete from books where hotel='" + hotel + "'";
		String query3 = "delete from roomTypes where hotel='" + hotel + "'";
		String query4 = "delete from rooms where hotel='" + hotel + "'";
		String query5 = "delete from coupons where hotel='" + hotel + "'";
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
		Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
		PreparedStatement ps1=conn.prepareStatement(query1);
		ps1.execute();
		PreparedStatement ps2=conn.prepareStatement(query2);
		ps2.execute();
		PreparedStatement ps3=conn.prepareStatement(query3);
		ps3.execute();
		PreparedStatement ps4=conn.prepareStatement(query4);
		ps4.execute();
		PreparedStatement ps5=conn.prepareStatement(query5);
		ps5.execute();
		return true;
		}catch(Exception e) {
			
			return false;
		}
	}
	

	
	public String dbGetUserInfo(String username,String info) {
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			Statement stm=conn.createStatement();
			ResultSet rs=stm.executeQuery("select * from users where username='"+ username +"'");
			rs.next();
				return rs.getString(info);
		}catch(Exception e){
			
			return "";
		}
	}
	
	public void dbCreateUser(String level,String user, String pass) {
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			PreparedStatement ps=conn.prepareStatement("insert into users values(?,?,?)");
			ps.setString(1, level);
			ps.setString(2, user);
			ps.setString(3, pass);
			ps.execute();
		}catch(Exception e){
			
			e.printStackTrace();
		}
			}
	
	public boolean dbCheckIfUserExists(String user) {
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			Statement stm=conn.createStatement();
			ResultSet rs=stm.executeQuery("select * from users where username='"+ user +"'");
			if(rs.next()){
			return true;
			}
			return false;
		}catch(Exception e){
			
			return false;
		}
	}
	
	public boolean dbValidateUser(String level,String user, String pass) {
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			Statement stm=conn.createStatement();
			ResultSet rs=stm.executeQuery("select * from users where level='" + level + "' and username='"+ user +"' and password='" + pass + "'"  );
			if(rs.next()){
			return true;
			}
			return false;
		}catch(Exception e){
			
			return false;
		}
	}
	
	public int dbGetCouponDiscount(String code) {
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			Statement stm=conn.createStatement();
			ResultSet rs=stm.executeQuery("select * from coupons where code='" + code + "'");
			String query2 = "delete from coupons where code='" + code + "'";
			PreparedStatement ps2=conn.prepareStatement(query2);
			if(rs.next()){
			ps2.execute();
			return Integer.parseInt(rs.getString("discount"));
			}
			return 0;
		}catch(Exception e){
			
			return 0;
		}
	}
	
	public boolean dbCreateType(String hotel,String type,String cost) {
		try {
			String query = "insert into roomTypes (hotel, type, cost, discount) values ('"+ hotel +"', '"+ type +"', "+ cost +", 0)";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			PreparedStatement ps=conn.prepareStatement(query);
			ps.execute();
				return true;
			}catch(Exception e) {
				return false;
			}
	}
	
	public boolean dbRenameHotel(String name,String NewName) {
		try {
			String query = "update hotels set name='"+ NewName +"' where name='"+ name +"'";
			String query2 = "update books set hotel='"+ NewName +"' where hotel='"+ name +"'";
			String query3 = "update roomTypes set hotel='"+ NewName +"' where hotel='"+ name + "'";
			String query4 = "update coupons set hotel='"+ NewName +"' where hotel='"+ name + "'";
			String query5 = "update rooms set hotel='"+ NewName +"' where hotel='"+ name + "'";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			PreparedStatement ps=conn.prepareStatement(query);
			ps.execute();
			PreparedStatement ps2=conn.prepareStatement(query2);
			ps2.execute();
			PreparedStatement ps3=conn.prepareStatement(query3);
			ps3.execute();
			PreparedStatement ps4=conn.prepareStatement(query4);
			ps4.execute();
			PreparedStatement ps5=conn.prepareStatement(query5);
			ps5.execute();
				return true;
			}catch(Exception e) {
				
				return false;
			}
	}
	
	public boolean dbRemoveType(String hotel,String type) {
		try {
		String query1 = "delete from roomTypes where hotel='"+ hotel +"' and type='" + type + "'";
		String query2 = "delete from books where hotel='"+ hotel +"' and type='" + type + "'";
		String query4 = "delete from rooms where hotel='"+ hotel +"' and type='" + type + "'";
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
		Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
		PreparedStatement ps1=conn.prepareStatement(query1);
		ps1.execute();
		PreparedStatement ps2=conn.prepareStatement(query2);
		ps2.execute();
		PreparedStatement ps4=conn.prepareStatement(query4);
		ps4.execute();
		return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public boolean dbCreateRoom(String hotel,String number,String type,String singleBeds,String doubleBeds) {
		try {
			String query = "insert into rooms (number, hotel, type, SingleBedNum, DoubleBedNum) values (" + number + ", '" + hotel +"', '" + type + "', " + singleBeds + ", " + doubleBeds + ")";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			PreparedStatement ps=conn.prepareStatement(query);
			ps.execute();
				return true;
			}catch(Exception e) {
				
				return false;
			}
	}
	
	public boolean dbUpdateRoom(String hotel,String type,String number,String NewNumber,String NewType,String NewSbed,String NewDbed) {
		try {
			String query = "update rooms set number="+ NewNumber +", type='"+ NewType +"',SingleBedNum="+ NewSbed +", SingleBedNum="+ NewDbed +" where hotel='"+ hotel +"' and type='"+ type +"' and number="+ number +"";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			PreparedStatement ps=conn.prepareStatement(query);
			ps.execute();
				return true;
			}catch(Exception e) {
				
				return false;
			}
	}
	
	public boolean dbRemoveRoom(String hotel,String type,String number) {
		try {
			String query = "delete from rooms where hotel='"+ hotel +"' and type='"+ type +"' and number='"+ number +"'";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			PreparedStatement ps=conn.prepareStatement(query);
			ps.execute();
			return true;
			}catch(Exception e) {
				
				return false;
			}
	}
	
	public boolean dbEmployeeConnect(String user, String pass) {
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL="jdbc:mysql://" + IP + "/" + DATABASE;
			Connection conn=DriverManager.getConnection(connectionURL,USER,PASS);
			Statement stm=conn.createStatement();
			ResultSet rs=stm.executeQuery("select * from users where username='"+ user +"' and password='" + pass + "'"  );
			if(rs.next()){
				empPanelFunctions emplPanelFunctions = new empPanelFunctions();
				emplPanelFunctions.employee_login(rs.getString("level"),rs.getString("username"),rs.getString("password"));
				return true;
			}else{
				JOptionPane.showMessageDialog(null, "Could not log in! Please try again!","Fail!", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}catch(Exception e){
			
			return false;
		}
	}
		
}
