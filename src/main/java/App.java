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
      if (workDays.equals("")) {
        workDays = "This stylist has no shifts. <a href='/stylists/:id/update'>ADD SHIFTS</a>";
      }
      Stylist newStylist = new Stylist(name, workDays);
      newStylist.save();

      model.put("stylists", Stylist.all());
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
