package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import io.ebean.Ebean;
import utils.AppUtil;
import java.util.Optional;
import java.util.List;

import play.mvc.Http.Request;

import models.User;

import play.libs.Json;

public class UserController extends Controller {

    public Result createUser(Request request) {
        // Parse the JSON request body into a User object
        User newUser = Json.fromJson(request.body().asJson(), User.class);

        if (newUser == null || newUser.getName() == null || newUser.getEmail() == null) {
            return badRequest(AppUtil.createMessageNode("Invalid JSON request. Both 'name' and 'email' fields are required."));
        }

        // Check if the email is already in use
        User existingUser = User.find.query().where().eq("email", newUser.getEmail()).findOne();

        if (existingUser != null) {
            return badRequest(AppUtil.createMessageNode("Email is already in use."));
        }

        // Save the new user
        newUser.save();

        return ok("User created successfully.");
    }

    public Result login(String email) {
        // Check if the email exists in the database
        User user = Ebean.find(User.class).where().eq("email", email).findOne();

        if (user != null) {
            // Email exists, return user data as JSON
            return ok(Json.toJson(user));
        } else {
            // Email doesn't exist, return an error message
            return badRequest(AppUtil.createMessageNode("Invalid email address."));
        }
    }
}
