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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import edu.hope.cs.csci392.imdb.model.Actor;
import edu.hope.cs.csci392.imdb.services.ActorService;

@Controller
public class ActorController {
    public record ActorSearchRequest(String firstName, String lastName, Optional<Integer> birthYear, Optional<Integer> deathYear) {}

    public record AddActorRequest( 
        String fullName,
        String firstName,
        String middleName,
        String lastName,
        String suffix,
        Optional<Integer> birthYear,
        Optional<Integer> deathYear
    ) {}

    @Autowired
    private ActorService actorService;
    
    @GetMapping("actor/{actorId}")
    public String showActorDetails(@PathVariable String actorId, Model model) {
        List<String> errors = new LinkedList<String>();

        try {
            Actor actor = actorService.findActorByID(actorId);
            model.addAttribute("actor", actor);

            boolean hasMiddleName = actor.getMiddleName() != null && !actor.getMiddleName().equals("");
            model.addAttribute("hasMiddleName", hasMiddleName);

            boolean hasSuffix = actor.getSuffix() != null && !actor.getSuffix().equals("");
            model.addAttribute("hasSuffix", hasSuffix);

        }
        catch (SQLException e) {
            errors.add("Exception occurred find actor with ID " + actorId + "; " + e.getMessage());
        }

        model.addAttribute("errors", errors);
        return "actor_details";
    }

    @GetMapping("actor_search_form")
    public String actorSearchForm() {
        return "actor_search_form";
    }

    @PostMapping("actor_search")
    public String search(@ModelAttribute ActorSearchRequest q, Model model) {
        int birthYear = q.birthYear.isPresent() ? q.birthYear.get() : -1;
        int deathYear = q.deathYear.isPresent() ? q.deathYear.get() : -1;

        List<String> errors = new LinkedList<String>();

        try {
            List<Actor> actors = actorService.findActors(q.firstName(), q.lastName(), birthYear, deathYear);
            model.addAttribute("actors", actors);
            model.addAttribute("actorOrActors", actors.size() > 1 ? "actors" : "actor");
        } catch (SQLException e) {
            errors.add("An error occurred eecuting Database.findActors: " + e.getMessage());
        }

        model.addAttribute("errors", errors);
        return "actor_search_results";
    }

    @GetMapping("add_actor_form")
    public String addActorForm() {
        return "add_actor_form";
    }

    @PostMapping("add_actor")
    public String addActor(@ModelAttribute AddActorRequest actor, Model model) {
        String fullName = actor.fullName.equals("") ? null : actor.fullName;
        String firstName = actor.firstName.equals("") ? null : actor.firstName;
        String middleName = actor.middleName.equals("") ? null : actor.middleName;
        String lastName = actor.lastName.equals("") ? null : actor.lastName;
        String suffix = actor.suffix.equals("") ? null : actor.suffix;
        int birthYear = actor.birthYear.isPresent() ? actor.birthYear.get() : -1;
        int deathYear = actor.deathYear.isPresent() ? actor.deathYear.get() : -1;

        List<String> errors = new LinkedList<String> ();

        try {
            Actor newActor = actorService.addPerson(fullName, firstName, middleName, lastName, suffix, birthYear, deathYear);
            model.addAttribute("entityName", "actor");
            model.addAttribute("instanceName", newActor.getFullName());
        } catch (SQLException e) {
            errors.add("An error occurred trying to add the actor to the database: " + e.getMessage());
        }

        model.addAttribute("errors", errors);
        

        return "add_actor_confirmation";
    }

    @GetMapping("add_actor_confirmation")
    public String addActorConfirmation(Model model) {
        return "add_actor_confirmation";
    }
}
