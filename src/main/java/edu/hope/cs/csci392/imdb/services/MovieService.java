package edu.hope.cs.csci392.imdb.services;

import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.hope.cs.csci392.imdb.ConnectionFactory;
import edu.hope.cs.csci392.imdb.Database;

import edu.hope.cs.csci392.imdb.model.Movie;
import edu.hope.cs.csci392.imdb.model.Principal;
import edu.hope.cs.csci392.imdb.model.Role;

@Service
public class MovieService {
	public enum SearchType {
		ALL, ANY
	}	
    
    @Autowired
    private ConnectionFactory connectionFactory;
   

    /**
	 * Finds the movies that match the given conditions.  All conditions
	 * are combined using AND.
	 * @param movieTitle the title of the movie(s) to be located, or an
	 * empty string if title should not be included as part of the criteria
	 * @param year the year the movie was made, or -1 if year should not be
	 * included as part of the criteria
	 * @param runningTimeComparator a string indicating the type of comparison
	 * to be used for the running time.  This string can be used directly in
	 * the SQL query
	 * @param runningTimeValue the running time of the movie(s) to be located,
	 * or -1 if running time should not be included as part of the criteria
	 * @param mpaaRating the MPAA Rating of the movie to find, or an empty 
	 * string if the MPAA Rating should not be included as part of the criteria
	 * @param minimumIMDBRating the minimum value for the movie's IMDB rating 
	 * to be included in the result, or -1 if IMDBRating should not be included 
	 * as part of the criteria 
	 * @param genres a set of genres used to filter the movies in the results
	 * @param genreSearchType indicates the way the specified genre restrictions 
	 * should be combined; a value of <code>SearchType.all</code> indicates 
	 * that movies must match <strong>all</strong> the genres in <code>genres</code>, 
	 * while a value of <code>SearchType.any</code> indicates that movies 
	 * must match <strong>at least one</strong> of the genres in <code>genres</code>
	 * @param people a set of people who were principals in the movies associated 
	 * with a specific category.  The <code>Principal</code> record contains  
	 * the person's full name, and the category associated with their part in the movie
	 * @param peopleSearchType indicates the way the specified people restrictions 
	 * should be combined; a value of <code>SearchType.all</code> indicates 
	 * that <strong>all</strong> the people in <code>people</code> must have been
	 * a principal in a movie, while a value of <code>SearchType.any</code> indicates that  
	 * <strong>at least one</strong> of the people in <code>people</code> must have 
	 * been a principal in a movie
	 * @return a List of Movie objects representing the movie(s) matching the
	 * specified conditions
	 * @throws SQLException if an exception occurs connecting to the DB,
	 * or in processing the results
	 */
	public List<Movie> findMovies (
		String movieTitle, int year, String runningTimeComparator, 
		int runningTimeValue, String mpaaRating, float minimumIMDBRating,
		SearchType genreSearchType, List<String> genres, SearchType peopleSearchType, List<Principal> people
	) throws SQLException {
		ArrayList<Movie> movies = new ArrayList<Movie> ();
		movies.add (Database.sleepless);
		movies.add (Database.mail);
		return movies;
	}
    
	/**
	 * Retrieves a list consisting of consecutive years between the <strong>earliest</strong>
	 * and <strong>latest</strong> years for which there are movies in the database. 
	 * @return an ArrayList of integers representing the years for which movies have been made,
	 * in descending order by year
	 * @throws SQLException if an error occurs connecting to the database or processing the 
	 * rows that it is returning
	 */
	public List<Integer> getMovieYears() throws SQLException {
		List<Integer> possibleYears = new ArrayList<Integer>();		
		int maxYear = Year.now().getValue();
		int minYear = maxYear - 20;

		for (int i = maxYear; i > minYear; i--) {
			possibleYears.add(i);
		}

		return possibleYears;
	}

    /**
	 * Finds the cast of a given movie
	 * @param movie the movie to search for
	 * @return a List of Role objects representing the roles in the
	 * specified movie
	 * @throws SQLException if an exception occurs connecting to the DB,
	 * or in processing the results
	 */
	public List<Role> findRolesInMovie (Movie movie) throws SQLException {
		ArrayList<Role> roles = new ArrayList<Role> ();
		if (movie.getTitleId() == Database.sleepless.getTitleId()) {
			roles.add (Database.hanksSleepless);
			roles.add (Database.ryanSleepless);
		}
		
		if (movie.getTitleId() == Database.mail.getTitleId()) {
			roles.add (Database.hanksMail);
			roles.add (Database.ryanMail);
		}
		
		return roles;
	}

	/**
	 * Finds a given movie based on Title ID
	 * @param titleID the ID to search for
	 * @return the movie with the given ID, or null if none can be found
	 * @throws SQLException if an exception occurs connecting to the DB,
	 * or in processing the results
	 */
	public Movie findMovieByID (String titleID) throws SQLException {
		if (titleID.equals(Database.sleepless.getTitleId())) {
			return Database.sleepless;
		}
		
		if (titleID.equals(Database.mail.getTitleId())) {
			return Database.mail;
		}
		
		return null;
	}
}