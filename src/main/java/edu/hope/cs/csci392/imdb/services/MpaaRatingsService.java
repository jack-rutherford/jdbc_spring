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
public class MpaaRatingsService {
    @Autowired
    private ConnectionFactory connectionFactory;

    	/**
	 * Retrieves the list of MPAA ratings from the database.
	 * Only those Ratings that
	 * <ul> 
	 *   <li><strong>don't start with TV</strong></li>
	 *   <li>aren't the strings	<em>Unrated</em> and <em>Not Rated</em></li>
	 *   <li>have <strong>at least 1000 movies</strong> that have been made since 1970</li>
	 * </ul>
	 * should be included in the list.
	 * <p>
	 * The list should be sorted by the SortOrder field found in the MPAARatings
	 * table.
	 * </p>
	 * @throws SQLException if an exception occurs connecting to the DB,
	 * or in processing the results 
	 */
	public List<String> findMPAARatings() throws SQLException {
		ArrayList<String> ratings = new ArrayList<String>();
		try(
			Connection conn = connectionFactory.getConnection();
			Statement stmt = conn.createStatement();
		)
		{ 
			String sql = """
				SELECT Rating, COUNT(TitleID) [Movies per Rating] FROM imdb.MPAARatings
				JOIN imdb.Movies on MPAARatings.Rating = Movies.MPAARating
				where Rating not like 'TV%'
				and not Rating = 'Unrated'
				and not Rating = 'Not Rated' 
				and YearReleased > 1970
				and SortOrder is not null
				group by Rating, SortOrder
				having COUNT(TitleID) > 1000
				order by SortOrder
			""";
			ResultSet results = stmt.executeQuery(sql);
			while(results.next()){
				ratings.add(results.getString("Rating"));
			}
		}
		// ratings.add("G");
		// ratings.add("PG");
		return ratings;
	}
}
