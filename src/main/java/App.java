import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.List;
import java.util.ArrayList;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylist.all());
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/stylist-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String workDays = "";
      if (request.queryParams("monday") != null) {
        workDays += "Monday ";
      }
      if (request.queryParams("tuesday") != null) {
        workDays += "Tuesday ";
      }
      if (request.queryParams("wednesday") != null) {
        workDays += "Wednesday ";
      }
      if (request.queryParams("thursday") != null) {
        workDays += "Thursday ";
      }
      if (request.queryParams("friday") != null) {
        workDays += "Friday ";
      }
      if (request.queryParams("saturday") != null) {
        workDays += "Saturday ";
      }
      if (request.queryParams("sunday") != null) {
        workDays += "Sunday ";
      }
      Stylist newStylist = new Stylist(name, workDays);
      newStylist.save();

      if (workDays.equals("")) {
        newStylist.updateShifts("This stylist has no shifts. <a href='/stylists/" + newStylist.getId() + "/update'>ADD SHIFTS</a>");
      } else {
        workDays += "<br><a href='/stylists/" + newStylist.getId() + "/update'>EDIT SHIFTS</a>";
        newStylist.updateShifts(workDays);
      }

      model.put("stylists", Stylist.all());
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params("id")));
      model.put("stylist", stylist);
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params("id")));
      model.put("stylist", stylist);
      model.put("template", "templates/stylist-shifts-update.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params("id")));
      String workDays = "";
      if (request.queryParams("monday") != null) {
        workDays += "Monday ";
      }
      if (request.queryParams("tuesday") != null) {
        workDays += "Tuesday ";
      }
      if (request.queryParams("wednesday") != null) {
        workDays += "Wednesday ";
      }
      if (request.queryParams("thursday") != null) {
        workDays += "Thursday ";
      }
      if (request.queryParams("friday") != null) {
        workDays += "Friday ";
      }
      if (request.queryParams("saturday") != null) {
        workDays += "Saturday ";
      }
      if (request.queryParams("sunday") != null) {
        workDays += "Sunday ";
      }
      if (workDays.equals("")) {
        workDays = "This stylist has no shifts. <a href='/stylists/" + stylist.getId() + "/update'>ADD SHIFTS</a>";
      } else {
        workDays += "<br><a href='/stylists/" + stylist.getId() + "/update'>EDIT SHIFTS</a>";
      }
      stylist.updateShifts(workDays);
      stylist = Stylist.find(stylist.getId());
      model.put("stylist", stylist);
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
