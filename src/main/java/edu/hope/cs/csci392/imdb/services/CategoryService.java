package edu.hope.cs.csci392.imdb.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.hope.cs.csci392.imdb.ConnectionFactory;

@Service
public class CategoryService {
    @Autowired private ConnectionFactory connectionFactory;

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
}
