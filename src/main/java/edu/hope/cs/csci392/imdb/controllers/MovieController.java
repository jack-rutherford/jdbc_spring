package edu.hope.cs.csci392.imdb.controllers;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import edu.hope.cs.csci392.imdb.model.Movie;
import edu.hope.cs.csci392.imdb.model.Principal;
import edu.hope.cs.csci392.imdb.services.CategoryService;
import edu.hope.cs.csci392.imdb.services.GenreService;
import edu.hope.cs.csci392.imdb.services.MovieService;
import edu.hope.cs.csci392.imdb.services.MpaaRatingsService;
import edu.hope.cs.csci392.imdb.services.MovieService.SearchType;

@Controller
public class MovieController {
    public record MovieSearchRequest (
        String movieTitle, int year, String runningTimeComparator, Optional<Integer> runningTime, String mpaaRating, 
        Optional<Float> IMDBRating,
        String genreType,
        String genre1, String genre2, String genre3,
        String peopleType,
        String person1Name, String person1Role,        
        String person2Name, String person2Role,
        String person3Name, String person3Role
    ) {}

    @Autowired private MovieService movieService;
    @Autowired private MpaaRatingsService ratingsService;
    @Autowired private GenreService genreService;
    @Autowired private CategoryService categoryService;

    @GetMapping("movie_search_form")
    public String movieSearchForm(Model model) {        
        List<String> errors = new LinkedList<String> ();

        try {
            model.addAttribute("genres", genreService.findGenres());
        } catch (SQLException e) {
            errors.add("An error occurred downloading the genres: " + e.getMessage());            
        }

        try {
            model.addAttribute("categories", categoryService.findCategories());
        } catch (SQLException e) {
            errors.add("An error occurred downloading the categories: " + e.getMessage());
        }

        try {
            model.addAttribute("mpaaRatings", ratingsService.findMPAARatings());
        } catch (SQLException e) {
            errors.add("An error occurred downloading the mpaa ratings: " + e.getMessage());
        }

        try {
            model.addAttribute("years", movieService.getMovieYears());
        } catch (SQLException e) {
            errors.add("An error occurred downloading the possible movie year: " + e.getMessage());
        }
        
        model.addAttribute("errors", errors);
        
        return "movie_search_form";
    }

    @PostMapping("movie_search")
    public String performMovieSearch(@ModelAttribute MovieSearchRequest q, Model model) {        
        List<Principal> people = new LinkedList<Principal>();        
        List<String> genres = new LinkedList<String>();
        
        List<String> errors = new LinkedList<String>();

        int runningTime = q.runningTime.isPresent() ? q.runningTime.get() : -1;
        float minimumIMDBRating = q.IMDBRating.isPresent() ? q.IMDBRating.get() : -1;
        
        if (!q.person1Name.equals("")) {
            if (q.person1Role.equals("null")) {
                errors.add(String.format("No role specified for %s", q.person1Name));
            }
            else {
                people.add(new Principal(q.person1Name, q.person1Role));
                
            }
        }

        if (!q.person2Name.equals("")) {
            if (q.person2Role.equals("null")) {
                errors.add(String.format("No role specified for %s", q.person2Name));
            }
            else {
                people.add(new Principal(q.person2Name, q.person2Role));
            }
        }

        if (!q.person3Name.equals("")) {
            if (q.person3Role.equals("null")) {
                errors.add(String.format("No role specified for %s", q.person3Name));
            }
            else {
                people.add(new Principal(q.person3Name, q.person3Role));
            }
        }

        if (!q.genre1.equals("null")) {
            genres.add(q.genre1);
        }

        if (!q.genre2.equals("null")) {
            genres.add(q.genre2);
        }

        if (!q.genre3.equals("null")) {
            genres.add(q.genre3);
        }
        
        try {
            List<Movie> movies = movieService.findMovies(
                q.movieTitle, q.year, q.runningTimeComparator, runningTime, q.mpaaRating, minimumIMDBRating, 
                SearchType.valueOf(q.genreType.toUpperCase()), genres,
                SearchType.valueOf(q.peopleType.toUpperCase()), people
            );
            model.addAttribute("movies", movies);
            model.addAttribute("movieOrMovies", movies.size() > 1 ? "movies" : "movie");
        } catch (SQLException e) {
            errors.add("An exception occurred trying to find the movies: " + e.getMessage());
        }

        model.addAttribute("errors", errors);

        return "movie_search_results";
    }
}
