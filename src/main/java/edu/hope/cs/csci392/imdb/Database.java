package edu.hope.cs.csci392.imdb;

import edu.hope.cs.csci392.imdb.model.Actor;
import edu.hope.cs.csci392.imdb.model.Movie;
import edu.hope.cs.csci392.imdb.model.Role;

public class Database {

	public static Actor hanks = new Actor (
		"nm0000158" , "Tom", null, "Hanks", null, "Tom Hanks", 1956, 0
	);
	
	public static Actor ryan = new Actor (
		"nm0000212", "Meg", null, "Ryan", null, "Meg Ryan", 1961, 0
	);
	
	public static Movie sleepless = new Movie (
		"tt0108160", "Sleepless in Seattle", 1993, 105, "Comedy"
	);
	
	public static Movie mail = new Movie (
			"tt0128853", "You've got mail", 1998, 119, "Comedy"
	);
		
	public static Role ryanSleepless = new Role (
		ryan.getPersonID(), sleepless.getTitleId(),
		"Annie Reed", "actress", 2
	);
	
	public static Role ryanMail = new Role (
		ryan.getPersonID(), mail.getTitleId(),
		"Kathleen Kelly", "actress", 2
	);

	public static Role hanksSleepless = new Role (
		hanks.getPersonID(), sleepless.getTitleId(), 
		"Samuel 'Sam' Baldwin", "actor", 1
	);

	public static Role hanksMail = new Role (
		hanks.getPersonID(), mail.getTitleId(), 
		"Joe Fox", "actor", 1
	);
				
	static {
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
}
