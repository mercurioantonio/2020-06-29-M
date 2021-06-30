package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public List<Actor> listAllActors(){
		String sql = "SELECT * FROM actors";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				
				result.add(actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void listAllDirectors(int anno, Map<Integer, Director> idMap){
		String sql = "SELECT d.* "
				+ "FROM directors d, movies m, movies_directors md "
				+ "WHERE m.id=md.movie_id AND d.id=md.director_id AND m.year = ?";
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				idMap.put(director.getId(), director);
			}
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public Integer listActorsDirector (Director d1, Director d2, int anno){
		String sql = "SELECT md1.director_id, r.actor_id, md2.director_id, r2.actor_id "
				+ "FROM  movies_directors md1,  movies m, roles r, actors a, "
				+ "      movies_directors md2,  movies m2, roles r2, actors a2 "
				+ "WHERE md1.director_id=? AND m.id = md1.movie_id AND m.year = ? AND  m.id=r.movie_id AND r.actor_id = a.id AND "
				+ "      md2.director_id=? AND m2.id = md2.movie_id AND m2.year = ? AND  m2.id=r2.movie_id AND r2.actor_id = a2.id "
				+ "      AND a.id=a2.id";
		int nAct = 0;
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, d1.getId());
			st.setInt(2, anno);
			st.setInt(3, d2.getId());
			st.setInt(4, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				nAct++;
			}
			conn.close();
			return nAct;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
