package edu.hope.cs.csci392.imdb.model;

import lombok.experimental.Accessors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Actor {	
	private String personID;
	private String firstName;
	private String middleName;
	private String lastName;
	private String suffix;
	private String fullName;
	private int birthYear;
	private int deathYear;		
}