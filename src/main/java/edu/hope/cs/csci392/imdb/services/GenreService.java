package edu.hope.cs.csci392.imdb.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.hope.cs.csci392.imdb.ConnectionFactory;

@Service
public class GenreService {
    @Autowired
    private ConnectionFactory connectionFactory;

    	/** Retrieves the list of possible genres from the database
	 * 
	 * @return a list of strings representing the possible genres
	 * @throws SQLException if an exception occurs connecting to the DB,
	 * or in processing the results
	 */
	public List<String> findGenres () throws SQLException {
		ArrayList<String> genres = new ArrayList<String> ();
		try(
			Connection conn = connectionFactory.getConnection();
			Statement stmt = conn.createStatement();
		)
		{ 
			String sql = "select * from imdb.Genres order by Genre";
			ResultSet results = stmt.executeQuery(sql);
			while(results.next()){
				genres.add(results.getString("Genre"));
			}
		}
		return genres;
	}
}
