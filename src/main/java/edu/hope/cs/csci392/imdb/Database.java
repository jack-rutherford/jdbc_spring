package edu.hope.cs.csci392.imdb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.hope.cs.csci392.imdb.model.Actor;
import edu.hope.cs.csci392.imdb.model.Movie;
import edu.hope.cs.csci392.imdb.model.Role;

public class Database {
	private static Database instance;

	public enum SearchType {
		ALL, ANY
	}

	public record Principal (String personName, String category) {}

	Actor hanks = new Actor (
		"nm0000158" , "Tom", null, "Hanks", null, "Tom Hanks", 1956, 0
	);
	
	Actor ryan = new Actor (
		"nm0000212", "Meg", null, "Ryan", null, "Meg Ryan", 1961, 0
	);
	
	Movie sleepless = new Movie (
		"tt0108160", "Sleepless in Seattle", 1993, 105, "Comedy"
	);
	
	Movie mail = new Movie (
			"tt0128853", "You've got mail", 1998, 119, "Comedy"
	);
	
	Role hanksSleepless = new Role (
		hanks.getPersonID(), sleepless.getTitleId(), 
		"Samuel 'Sam' Baldwin", "actor", 1
	);

	Role hanksMail = new Role (
		hanks.getPersonID(), mail.getTitleId(), 
		"Joe Fox", "actor", 1
	);
	
	Role ryanSleepless = new Role (
		ryan.getPersonID(), sleepless.getTitleId(),
		"Annie Reed", "actress", 2
	);
	
	Role ryanMail = new Role (
		ryan.getPersonID(), mail.getTitleId(),
		"Kathleen Kelly", "actress", 2
	);
	
	private Database ()
	{
		sleepless.addGenre("Comedy");	
		sleepless.addGenre("Drama");			
		sleepless.addGenre("Romance");

		mail.addGenre("Comedy");	
		mail.addGenre("Drama");			
		mail.addGenre("Romance");

		sleepless.setImdbRating(6.8f);
		sleepless.setNumberOfRatings(164400);

		mail.setImdbRating(6.7f);
		mail.setNumberOfRatings(199888);
	}
	
	public static Database getInstance () {
		if (instance == null) {
			instance = new Database ();
		}
		
		return instance;
	}
	
	/**
	 * Finds the actors that match the given conditions.  Only those 
	 * parameters that have non-empty values are included in the query.  
	 * Those parameters that are included are combined using AND.
	 * @param firstName the first name of the actor(s) to locate
	 * @param lastName the last name of the actor(s) to locate
	 * @param birthYear the year when the actor(s) to locate were born
	 * @param deathYear the year when the actor(s) to locate died
	 * @return a List of Actor objects representing the actor(s) matching the
	 * specified conditions
	 * @throws SQLException if an exception occurs connecting to the DB,
	 * or in processing the results 
	 */
	public List<Actor> findActors (
		String firstName, String lastName,
		int birthYear, int deathYear
	) throws SQLException
	{		
		ArrayList<Actor> actors = new ArrayList<Actor> ();
		actors.add (hanks);
		actors.add (ryan);
		return actors;		
	}
	
	/**
	 *  Adds a new person to the <strong>imdb.People</strong> table. 
	 *  The PersonID attribute for the new person should be in the form 
	 *  nm#######, where ####### is computed as one more than the largest 
	 *  value currently existing in the imdb.People table.  The largest original
	 *  PersonID value in the database is <strong>nm9993693</strong>.
	 *  @param fullName the person's full name, or null if the value shouldn't be set
	 *  @param firstName the person's first name, or null if the value shouldn't be set
	 *  @param middleName the person's middle name, or null if the value shouldn't be set
	 *  @param lastName the person's last name, or null if the value shouldn't be set
	 *  @param suffix the name's suffix (e.g. Jr or II), or null if the value shouldn't be set
	 *  @param birthYear the year the person was born, or -1 if the value in
	 *  the database should be null
	 *  @param deathYear the year the person died, or -1 if the value in the
	 *  database should be null
	 *  @throws SQLException if an exception occurs adding the new person 
	 *  to the database
	 */
	public Actor addPerson ( 
		String fullName, String firstName, String middleName, 
		String lastName, String suffix, int birthYear, int deathYear
	) throws SQLException {
		Actor fakeActor = new Actor("xnm0000000", firstName, middleName, lastName, suffix, fullName, birthYear, deathYear);
		return fakeActor;
	}
	
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
		movies.add (sleepless);
		movies.add (mail);
		return movies;
	}
	
	/**
	 * Finds all the roles played by an actor.
	 * @param actor the actor to search for
	 * @return a List of Role objects representing the roles played by the
	 * specified actor
	 * @throws SQLException if an exception occurs connecting to the DB,
	 * or in processing the results
	 */
	public List<Role> findMoviesForActor (Actor actor) throws SQLException {
		ArrayList<Role> roles = new ArrayList<Role> ();
		
		if (actor.getPersonID().equals(hanks.getPersonID())) {
			roles.add (hanksSleepless);
			roles.add (hanksMail);
		}
		
		if (actor.getPersonID().equals(ryan.getPersonID())) {
			roles.add (ryanSleepless);
			roles.add (ryanMail);
		}				
		return roles;
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
		if (movie.getTitleId() == sleepless.getTitleId()) {
			roles.add (hanksSleepless);
			roles.add (ryanSleepless);
		}
		
		if (movie.getTitleId() == mail.getTitleId()) {
			roles.add (hanksMail);
			roles.add (ryanMail);
		}
		
		return roles;
	}
	
	/**
	 * Finds a given actor based on Person ID
	 * @param personID the ID to search for
	 * @return the actor with the given ID, or null if none can be found
	 * @throws SQLException if an exception occurs connecting to the DB,
	 * or in processing the results
	 */
	public Actor findActorByID (String personID) throws SQLException {
		if (personID.equals(hanks.getPersonID())) {
			return hanks;
		}
		
		if (personID.equals(ryan.getPersonID())) {
			return ryan;
		}
		
		return null;
	}
	
	/**
	 * Finds a given movie based on Title ID
	 * @param titleID the ID to search for
	 * @return the movie with the given ID, or null if none can be found
	 * @throws SQLException if an exception occurs connecting to the DB,
	 * or in processing the results
	 */
	public Movie findMovieByID (String titleID) throws SQLException {
		if (titleID.equals(sleepless.getTitleId())) {
			return sleepless;
		}
		
		if (titleID.equals(mail.getTitleId())) {
			return mail;
		}
		
		return null;
	}
	
	/** Retrieves the list of possible genres from the database
	 * 
	 * @return a list of strings representing the possible genres
	 * @throws SQLException if an exception occurs connecting to the DB,
	 * or in processing the results
	 */
	public List<String> findGenres () throws SQLException {
		ArrayList<String> genres = new ArrayList<String> ();
		genres.add("Comedy");
		genres.add("Drama");
		genres.add("Romance");
		genres.add("War");
		
		return genres;
	}
	
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
	
	/**
	 * Retrieves the distinct list of Categories found in the Principals table.
	 * The list should be sorted so that the Categories which appear most frequently 
	 * are displayed first.   
	 * @throws SQLException if an exception occurs connecting to the DB,
	 * or in processing the results
	 */
	public List<String> findCategories() throws SQLException {
		ArrayList<String> categories = new ArrayList<String>();
		categories.add("actor");
		categories.add("actress");
		return categories;
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
		for (int i = 2023; i >= 2000; i--) {
			possibleYears.add(i);
		}
		return possibleYears;
	}
}
