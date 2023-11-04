package edu.hope.cs.csci392.imdb.model;

import java.util.List;
import java.util.ArrayList;

public class Movie {
	private String titleID;
	private String title;	
	private int yearReleased;
	private int runningTimeInMinutes;
	private String primaryGenre;
	private float imdbRating;
	private int numberOfRatings;
	private List<String> genres;
	
	public Movie (
		String titleID, String title, int yearReleased, 
		int lengthInMinutes, String primaryGenre
	) {
		this.titleID = titleID;
		this.title = title;
		this.yearReleased = yearReleased;
		this.runningTimeInMinutes = lengthInMinutes;
		this.primaryGenre = primaryGenre;
		this.genres = new ArrayList<String>();
	}

	public String getTitleId() {
		return titleID;
	}

	public String getTitle() {
		return title;
	}

	public int getYearReleased() {
		return yearReleased;
	}

	public int getLengthInMinutes() {
		return runningTimeInMinutes;
	}

	public String getPrimaryGenre() {
		return primaryGenre;
	}

	public float getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(float rating) {
		this.imdbRating = rating;
	}

	public int getNumberOfRatings() {
		return numberOfRatings;
	}

	public String getNumberOfRatingsAsString() {
		if (numberOfRatings < 1000) {
			return String.valueOf(numberOfRatings);
		}
		else if (numberOfRatings < 1000000) {
			int hundredThousands = numberOfRatings / 100000;
			int tenThousands = numberOfRatings / 10000;
			return String.valueOf(hundredThousands) + "." + String.valueOf(tenThousands) + "K";
		}
		else {
			int millions = numberOfRatings / 1000000;
			int hundredThousands = numberOfRatings / 100000;
			return String.valueOf(millions) + "." + String.valueOf(hundredThousands) + "M";
		}
	}

	public void setNumberOfRatings(int numRatings) {
		this.numberOfRatings = numRatings;
	}
	
	public List<String> getGenres() {
		return genres;
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

		buffer.append(minutes);
		buffer.append(" ");
		buffer.append(minutes > 1 ? "minutes" : "minute");

		return buffer.toString();
	}

	public void addGenre(String genre) {
		this.genres.add(genre);
	}
}