package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import io.ebean.Ebean;
import utils.AppUtil;
import java.util.Date;
import java.util.Optional;
import java.util.List;
import play.mvc.Http.Request;
import models.User;
import play.libs.Json;

public class UserController extends Controller {

    public Result createUser(Request request) {
        User newUser = Json.fromJson(request.body().asJson(), User.class);
        if (newUser == null || newUser.getName() == null || newUser.getEmail() == null) {
            return badRequest(AppUtil.createMessageNode("Invalid JSON request. Both 'name' and 'email' fields are required."));
        }
        User existingUser = User.find.query().where().eq("email", newUser.getEmail()).findOne();
        if (existingUser != null) {
            return badRequest(AppUtil.createMessageNode("Email is already in use."));
        }
        newUser.setCreatedAt(new Date());
        newUser.save();
        return ok(AppUtil.createMessageNode("User created successfully."));
    }

    public Result login(String email) {
        User user = Ebean.find(User.class).where().eq("email", email).findOne();
        if (user != null) {
            return ok(Json.toJson(user));
        } else {
            return badRequest(AppUtil.createMessageNode("Invalid email address."));
        }
    }
}
