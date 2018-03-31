package com.mina.spider.collector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.mina.spider.beans.DetailItem;
import com.mina.spider.beans.MovieItem;
import com.mina.spider.beans.ProfileItem;

public class DbHelper {
	
	private final static String TAG = DataCollector.class.getSimpleName();
	private final static String DRIVER = "org.gjt.mm.mysql.Driver";
	private final static String URI = "jdbc:mysql://localhost:3306/pumpkin_movies";
	private final static String USER = "root";
	private final static String PASSWORD = "loneranger";
	private Connection conn = null;
	
	private DbHelper(){
		prepare();
	}
	
	private static class InstanceHolder{
		private static DbHelper instance = new DbHelper();
	}
	
	public static synchronized DbHelper get(){
		
		return InstanceHolder.instance;
		
	}
	
	public synchronized boolean prepare(){
		if(conn==null){
			try{
				Class.forName(DRIVER);
				conn = DriverManager.getConnection(URI, USER, PASSWORD);
				conn.setAutoCommit(false);
			}catch(Exception e){
				e.printStackTrace();
				return false;
			} 
		}
		return conn!=null;
	}
	
	public boolean insertProfileBySession(ProfileItem item){
		Connection conn = getConnection(); 
		if(conn==null)
			return false;
		boolean succeed = false;
		Statement state = null;
		try {
			String sql = "INSERT INTO profile_item(movie_id, introduction, movie_name, detail_link) VALUES ";
			sql += "(" + item.getMovieId() + 
					"," + item.getIntroduction() + 
					"," + item.getMovieName() + 
					"," + item.getDetailLink() + ");";
			
			state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			succeed = state.execute(sql);
			conn.commit();
			state.close();
			return succeed;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean insertProfilesByBatch(List<ProfileItem> items){
		Connection conn = getConnection(); 
		if(conn==null)
			return false;
		PreparedStatement pstate = null;
		try {
			String sql = "INSERT INTO profile_item(movie_id, introduction, movie_name, detail_link) VALUES (?, ?, ?, ?)";
			pstate = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for(ProfileItem item : items){
				pstate.setInt(1, item.getMovieId());
				pstate.setString(2, item.getIntroduction());
				pstate.setString(3, item.getMovieName());
				pstate.setString(4, item.getDetailLink());
				pstate.addBatch();
			}
			int[]results = pstate.executeBatch();
			conn.commit();
			pstate.close();
			return results != null && results.length > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean insertDetailBySession(DetailItem item){
		Connection conn = getConnection(); 
		if(conn==null)
			return false;
		boolean succeed = false;
		Statement state = null;
		try {
			String sql = "INSERT INTO detail_item(movie_id, download_link, link_type) VALUES ";
			sql += "(" + item.getMovieId() + 
					"," + item.getDownloadLink() + 
					"," + item.getLinkType() + ");";
			
			state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			succeed = state.execute(sql);
			conn.commit();
			state.close();
			return succeed;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean insertDetailsByBatch(List<DetailItem> items){
		Connection conn = getConnection(); 
		if(conn==null)
			return false;
		PreparedStatement pstate = null;
		try {
			String sql = "INSERT INTO detail_item(movie_id, download_link, link_type) VALUES (?, ?, ?)";
			pstate = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for(DetailItem item : items){
				pstate.setInt(1, item.getMovieId());
				pstate.setString(2, item.getDownloadLink());
				pstate.setInt(3, item.getLinkType());
				pstate.addBatch();
			}
			int[]results = pstate.executeBatch();
			conn.commit();
			pstate.close();
			return results != null && results.length > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean insertMovieBySession(MovieItem item){
		Connection conn = getConnection(); 
		if(conn==null)
			return false;
		boolean succeed = false;
		Statement state = null;
		try {
			String sql = "INSERT INTO movie_item(movie_id, cover_link, movie_name, date, actors, director, raw_data) VALUES ";
			sql += "(" + item.getMovieId() + 
					"," + item.getCoverLink() + 
					"," + item.getMovieName() +
					"," + item.getDate() + 
					"," + item.getActors() + 
					"," + item.getDirector() + 
					"," + item.getRawdata() + ");";
			
			state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			succeed = state.execute(sql);
			conn.commit();
			state.close();
			return succeed;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean insertMoviesByBatch(List<MovieItem> items){
		Connection conn = getConnection(); 
		if(conn==null)
			return false;
		PreparedStatement pstate = null;
		try {
			String sql = "INSERT INTO movie_item(movie_id, cover_link, movie_name, date, actors, director, raw_data) VALUES (?, ?, ?, ?, ?, ?, ?)";
			pstate = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for(MovieItem item : items){
				pstate.setInt(1, item.getMovieId());
				pstate.setString(2, item.getCoverLink());
				pstate.setString(3, item.getMovieName());
				pstate.setString(4, item.getDate());
				pstate.setString(5, item.getActors());
				pstate.setString(6, item.getDirector());
				pstate.setString(7, item.getRawdata());
				
				pstate.addBatch();
			}
			int[]results = pstate.executeBatch();
			conn.commit();
			pstate.close();
			return results != null && results.length > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public synchronized Connection getConnection(){
		return conn;
	}
	
	public synchronized void close(){
		try {
			if(conn!=null){
				conn.close();
				conn=null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void testJDBC(){
		Connection conn = null;
		Statement ss = null;
		ResultSetMetaData m = null;
		try{
			Class.forName(DRIVER);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(URI, USER, PASSWORD);
			ss = conn.createStatement();
			ResultSet set = ss.executeQuery("SELECT * FROM USER");
			m = set.getMetaData();
			int columns = m.getColumnCount();
			for(int i=1;i<=columns;i++){
			    System.out.println(m.getColumnName(i));
			}
			while(set.next()){
			    for(int i=1;i<=columns;i++){
			    	System.out.println(set.getString(i));
			    }
			    System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
				ss.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
