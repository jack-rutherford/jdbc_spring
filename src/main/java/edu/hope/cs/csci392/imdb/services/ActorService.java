package edu.hope.cs.csci392.imdb.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
		// if (personID.equals(Database.hanks.getPersonID())) {
		// 	return Database.hanks;
		// }
		
		// if (personID.equals(Database.ryan.getPersonID())) {
		// 	return Database.ryan;
		// }
		String sql = "select * from imdb.People where PersonID = ?";
		try(
			Connection conn = connectionFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql)
		)
		{
			stmt.setString(1, personID);
			ResultSet results = stmt.executeQuery();
			if(results.next()){
				Actor actor = new Actor();
				actor
					.setFirstName(results.getString("FirstName"))
					.setLastName(results.getString("LastName"))
					.setMiddleName(results.getString("MiddleName"))
					.setFullName(results.getString("FullName"))
					.setSuffix(results.getString("Suffix"))
					.setBirthYear(results.getInt("BirthYear"))
					.setDeathYear(results.getInt("DeathYear"));
				return actor;
			}
			return null;
		}
	}
	
    /**
	 * Finds the actors that match the given conditions.  Only those 
	 * parameters that have non-empty values are included in the query.  
	 * Those parameters that are included are combined using AND.
	 * @param firstName the first name of the actor(s) to locate, or the empty string 
	 * if firstName should not be included in the query
	 * @param lastName the last name of the actor(s) to locate, or the empty string
	 * if lastName should not be included in the query
	 * @param birthYear the year when the actor(s) to locate were born, or -1 if
	 * birthYear should not be included in the query
	 * @param deathYear the year when the actor(s) to locate died, or -1 if
	 * deathYear should not be included in the query
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
		StringBuilder sql = new StringBuilder(1024);
		sql.append("""
			select distinct People.* from imdb.People
			join imdb.Principals on People.PersonID = Principals.PersonID
		""");
		String connector = " where ";

		if (firstName != null && !firstName.isEmpty()) {
			sql.append(connector).append("FirstName = ?");
			connector = " and ";
		}

		if (lastName != null && !lastName.isEmpty()) {
			sql.append(connector).append("LastName = ?");
			connector = " and ";
		}

		if (birthYear != -1) {
			sql.append(connector).append("BirthYear = ?");
			connector = " and ";
		}

		if (deathYear != -1) {
			sql.append(connector).append("DeathYear = ?");
		}

		sql.append(connector);
		sql.append("IsActor = 1");
		connector = " and ";
		sql.append(" order by LastName");
		String sqlString = sql.toString();
		try(
			Connection conn = connectionFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sqlString)
		)
		{
			if(firstName != null && !firstName.isEmpty()){
				stmt.setString(1, firstName);
			}
			if(lastName != null && !lastName.isEmpty()){
				stmt.setString(2, lastName);
			}
			if(birthYear != -1){
				stmt.setInt(3, birthYear);
			}
			if(deathYear != -1){
				stmt.setInt(4, deathYear);
			}
			ResultSet results = stmt.executeQuery();
			ArrayList<Actor> actors = new ArrayList<Actor> ();
			while(results.next()){
				Actor actor = new Actor();
				actor
					.setFirstName(results.getString("FirstName"))
					.setLastName(results.getString("LastName"))
					.setMiddleName(results.getString("MiddleName"))
					.setFullName(results.getString("FullName"))
					.setSuffix(results.getString("Suffix"))
					.setBirthYear(results.getInt("BirthYear"))
					.setDeathYear(results.getInt("DeathYear"));
				actors.add(actor);
			}
			return actors;					
		}
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
