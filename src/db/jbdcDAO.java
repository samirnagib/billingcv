package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class jbdcDAO {

	//conexão com o banco de dados
	
	Connection conn = null;
	PreparedStatement st = null;
	ResultSet rs = null;

	public boolean validate(String LoginUser, String UserPassword ) throws SQLException {
		try {
			conn = DB.getConnection();
			st = conn.prepareStatement("SELECT userlogin, userpasswd, userLevelAccess FROM users WHERE userlogin = ? AND userpasswd = ? AND userLevelAccess = 1", Statement.RETURN_GENERATED_KEYS); 
			st.setString(1, LoginUser);
			st.setString(2, UserPassword);
			
			System.out.println(st);
			
			rs = st.executeQuery();
			if (rs.next()) {
				return true;
			};
			
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} ;
		return false;

		
	}
	

	
	
	
	
	
	
	
		
	
}
