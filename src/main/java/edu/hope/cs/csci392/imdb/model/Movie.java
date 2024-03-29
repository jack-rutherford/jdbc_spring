package edu.hope.cs.csci392.imdb.model;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Data
@Accessors(chain = true)
public class Movie {
	private String titleId;
	private String title;	
	private int yearReleased;
	private int runningTimeInMinutes;
	private String primaryGenre;
	private String mpaaRating;
	private float imdbRating;
	private int numberOfRatings;
	private List<String> genres;
	
	public Movie () {
		genres = new ArrayList<String> (4);
	}

	public Movie (
		String titleID, String title, int yearReleased, 
		int lengthInMinutes, String primaryGenre, String mpaaRating
	) {
		this();
		this.titleId = titleID;
		this.title = title;
		this.yearReleased = yearReleased;
		this.runningTimeInMinutes = lengthInMinutes;
		this.primaryGenre = primaryGenre;
		this.mpaaRating = mpaaRating;		
	}
	
	public String getNumberOfRatingsAsString() {
		if (numberOfRatings < 1000) {
			return String.valueOf(numberOfRatings);
		}
		else if (numberOfRatings < 1000000) {
			int thousands = numberOfRatings / 1000;
			int hundreds = (numberOfRatings % 1000) / 100;
			return String.valueOf(thousands) + "." + String.valueOf(hundreds) + "K";
		}
		else {
			int millions = numberOfRatings / 1000000;
			int hundredThousands = (numberOfRatings % 1000000) / 100000;
			return String.valueOf(millions) + "." + String.valueOf(hundredThousands) + "M";
		}
	}
	
	public String getGenresAsCommaSeparatedString () {
		StringBuffer buffer = new StringBuffer();
		if (primaryGenre == null) return "";

		buffer.append(primaryGenre);
		for (int i = 1; i < genres.size(); i++) {
			buffer.append(", ");
			buffer.append(genres.get(i));
		}

		return buffer.toString();
	}

	public String getRunningTimeHoursAndMinutes () {
		StringBuffer buffer = new StringBuffer();

		int hours = runningTimeInMinutes / 60;
		int minutes = runningTimeInMinutes % 60;

		if (hours > 0) {
			buffer.append(hours);
			buffer.append(" ");
			buffer.append(hours > 1 ? "hours" : "hour");

			if (minutes > 0) {
				buffer.append (" ");
			}
		}

		if (minutes > 0) {
			buffer.append(minutes);
			buffer.append(" ");
			buffer.append(minutes > 1 ? "minutes" : "minute");
		}
		return buffer.toString();
	}

	public void addGenre(String genre) {
		this.genres.add(genre);
	}
}