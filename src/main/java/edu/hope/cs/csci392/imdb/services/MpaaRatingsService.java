package edu.hope.cs.csci392.imdb.services;

import java.sql.SQLException;
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
		ratings.add("G");
		ratings.add("PG");
		return ratings;
	}
}
