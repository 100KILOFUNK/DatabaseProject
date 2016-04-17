import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
public class DBConnect {
	
	private Connection con;
	private String user;
	private String pass;
	private reservation reser;
	private ArrayList<String> cabins;
	private ArrayList<reservation> res;
	private ArrayList<Winter> winter;
	private ArrayList<Summer> summer;
	private float[] myArray;
	

	public DBConnect(){
		
		user = "dv1454_ht15_33";
		pass = "U2TYTRc&";
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection("jdbc:sqlserver://194.47.129.139:1433;" +
			"databaseName=dv1454_ht15_33;", user, pass);
		}
		catch(Exception ex){
			System.out.println("Failed connecting to DB : dv1454_ht15_33");
		}
	}
	public ArrayList<String> getCabins(){
		cabins=new ArrayList<String>();
		String SQL="SELECT adress FROM CABIN";
		PreparedStatement query=null;
		try{
			query=con.prepareStatement(SQL);
			ResultSet rs=query.executeQuery();
			while(rs.next()){
				cabins.add(rs.getString("adress"));
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
	return cabins;
	}
	public ArrayList<Winter>getWinterEq(int r_id){
		winter=new ArrayList<Winter>();
		String SQL="SELECT * FROM WINTER WHERE r_id=?";
		PreparedStatement query=null;
		try{
			query=con.prepareStatement(SQL);
			query.setInt(1, r_id+1);
			ResultSet rs=query.executeQuery();
			while(rs.next()){
				winter.add(new Winter(r_id+1,rs.getString("type"),rs.getFloat("shoeSize"),rs.getInt("skiSize"),rs.getInt("poleSize"),rs.getInt("headSize"),rs.getFloat("price")));
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return winter;
		
	}
	public ArrayList<Summer>getSummerEq(int r_id){
		summer=new ArrayList<Summer>();
		String SQL="SELECT * FROM SUMMER WHERE r_id=?";
		PreparedStatement query=null;
		try{
			query=con.prepareStatement(SQL);
			query.setInt(1, r_id+1);
			ResultSet rs=query.executeQuery();
			while(rs.next()){
				summer.add(new Summer(r_id+1,rs.getString("type"),rs.getInt("length"),rs.getInt("headSize"),rs.getFloat("price")));
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return summer;
		
	}
	public ArrayList<String> getReservation(){
		cabins=new ArrayList<String>();
		String SQL="SELECT * FROM RESERVATIONS";
		PreparedStatement query=null;
		try{
			query=con.prepareStatement(SQL);
			ResultSet rs=query.executeQuery();
			while(rs.next()){
				cabins.add("Reservation ID: "+rs.getInt("r_id")+" Civic Number : "+rs.getLong("civicNr"));
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		
		return cabins;
		
	}
	public ArrayList<reservation> getReservations(){
		res=new ArrayList<reservation>();
		String SQL="SELECT * FROM RESERVATIONS";
		PreparedStatement query=null;	
		try{
			query=con.prepareStatement(SQL);
			ResultSet rs=query.executeQuery();
			while(rs.next()){
				res.add(new reservation(rs.getInt("r_id"),
						rs.getInt("cabinNr"),
						rs.getLong("civicNr"),
						rs.getInt("groupSize"),
						rs.getFloat("cabinPrice"),
						rs.getFloat("eqPrice"),
						rs.getDate("startDate"),
						rs.getDate("endDate")));
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		
		return res;
		
	}
	public reservation getAReservation(int index){
		String SQL="SELECT * FROM RESERVATIONS WHERE r_id LIKE ?";
		PreparedStatement query=null;	
		try{
			query=con.prepareStatement(SQL);
			query.setInt(1,index+1);
			ResultSet rs=query.executeQuery();
			while(rs.next()){
				reser=new reservation(rs.getInt("r_id"),
						rs.getInt("cabinNr"),
						rs.getLong("civicNr"),
						rs.getInt("groupSize"),
						rs.getFloat("cabinPrice"),
						rs.getFloat("eqPrice"),
						rs.getDate("startDate"),
						rs.getDate("endDate"));
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		
		return reser;
		
	}
	public String getFullName(int index){
		String SQL="exec getName @r_id=?";
		PreparedStatement query=null;
		String name = null;
		try{
			query=con.prepareStatement(SQL);
			query.setInt(1, index+1);
			ResultSet rs=query.executeQuery();
			rs.next();
			name=rs.getString("name");
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return name;
	}
	public int getSeason(int index){
		CallableStatement cs=null;
		int result=0;
		try{
			cs=con.prepareCall("{?= call getHighOrLow(?)}");
			cs.setInt(2, index+1);
			cs.registerOutParameter(1, Types.INTEGER);
			cs.execute();
			result=cs.getInt(1);
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return result;
	}
	public float[] getPrice(int index){
		myArray=new float[2];
		String SQL="SELECT rentHigh,rentLow FROM CABIN WHERE cabinNr LIKE ?;";
		PreparedStatement query=null;
		try{
			query=con.prepareStatement(SQL);
			query.setInt(1, index+1);
			ResultSet rs=query.executeQuery();
			while(rs.next()){
				myArray[0]=rs.getFloat("rentHigh");
				myArray[1]=rs.getFloat("rentLow");
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		
		
		return myArray;
	}
	public void setPrice(float[] myArray,int index){
		String SQL="exec CHANGEPRICE @rentHigh=?,@rentLow=?,@cabinNr=?;";
		PreparedStatement query=null;
		try{
			query=con.prepareStatement(SQL);
			query.setFloat(1, myArray[0]);;
			query.setFloat(2, myArray[1]);
			query.setInt(3, index+1);
			query.executeUpdate();
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
	}
	

}
