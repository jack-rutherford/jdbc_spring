package edu.hope.cs.csci392.imdb.model;

public class Actor {
	
	private String personID;
	private String firstName;
	private String middleName;
	private String lastName;
	private String suffix;
	private String fullName;
	private int birthYear;
	private int deathYear;
	
	public Actor (
		String personID,
		String firstName, String middleName, String lastName, String suffix, String fullName,
		int birthYear, int deathYear
	) {
		this.personID = personID;
		this.fullName = fullName;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;	
		this.suffix = suffix;
		this.birthYear = birthYear;
		this.deathYear = deathYear;
	}

	public String getStageFirstName() {
		return firstName;
	}

	public String getStageLastName() {
		return lastName;
	}

	public String getPersonID() {
		return personID;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getSuffix() {
		return suffix;
	}

	public String getFullName() {
		return fullName;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public int getDeathYear() {
		return deathYear;
	}
}