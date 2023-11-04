package edu.hope.cs.csci392.imdb.model;

import java.sql.SQLException;

import edu.hope.cs.csci392.imdb.services.ActorService;
import edu.hope.cs.csci392.imdb.services.MovieService;

public class Role {
	private String personID;
	private String titleID;
	private String role;
	private String category;
	private int creditNumber;
	private Movie movie;
	private Actor actor;
		
	public Role (String personID, String titleID, String role, String category, int creditNumber) {
		this.personID = personID;
		this.titleID = titleID;
		this.role = role;
		this.creditNumber = creditNumber;
		movie = null;
		actor = null;
	}

	public String getPersonID() {
		return personID;
	}

	public String getTitleID() {
		return titleID;
	}

	public String getRole() {
		return role;
	}
	
	public String getCategory() {
		return category;
	}
	
	public int getCreditNumber() {
		return creditNumber;
	}
	
	public Actor getActor (ActorService actorService) throws SQLException {
		if (actor == null) {			
			actor = actorService.findActorByID(personID);
		}
		return actor; 
	}
	
	public Movie getMovie (MovieService movieService) throws SQLException {
		if (movie == null) {
			movie = movieService.findMovieByID(titleID);
		}
		return movie; 
	}
}