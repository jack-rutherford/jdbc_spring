package edu.hope.cs.csci392.imdb.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.hope.cs.csci392.imdb.ConnectionFactory;
import edu.hope.cs.csci392.imdb.Database;
import edu.hope.cs.csci392.imdb.model.Actor;
import edu.hope.cs.csci392.imdb.model.Role;

@Service
public class ActorService {
    @Autowired
    private ConnectionFactory connectionFactory;

    /**
	 * Finds a given actor based on Person ID
	 * @param personID the ID to search for
	 * @return the actor with the given ID, or null if none can be found
	 * @throws SQLException if an exception occurs connecting to the DB,
	 * or in processing the results
	 */
	public Actor findActorByID (String personID) throws SQLException {
		if (personID.equals(Database.hanks.getPersonID())) {
			return Database.hanks;
		}
		
		if (personID.equals(Database.ryan.getPersonID())) {
			return Database.ryan;
		}
		
		return null;
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
		actors.add (Database.hanks);
		actors.add (Database.ryan);
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
	 * Finds all the roles played by an actor.
	 * @param actor the actor to search for
	 * @return a List of Role objects representing the roles played by the
	 * specified actor
	 * @throws SQLException if an exception occurs connecting to the DB,
	 * or in processing the results
	 */
	public List<Role> findMoviesForActor (Actor actor) throws SQLException {
		ArrayList<Role> roles = new ArrayList<Role> ();
		
		if (actor.getPersonID().equals(Database.hanks.getPersonID())) {
			roles.add (Database.hanksSleepless);
			roles.add (Database.hanksMail);
		}
		
		if (actor.getPersonID().equals(Database.ryan.getPersonID())) {
			roles.add (Database.ryanSleepless);
			roles.add (Database.ryanMail);
		}				
		return roles;
	}
}
