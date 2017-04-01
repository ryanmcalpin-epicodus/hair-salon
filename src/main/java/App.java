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
      model.put("stylists", Stylist.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylist.all());
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("clients", Client.all());
      model.put("template", "templates/clients.vtl");
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
      model.put("clients", Client.allPerStylist(stylist.getId()));
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
      model.put("clients", Client.allPerStylist(stylist.getId()));
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id/clients/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params("id")));
      model.put("stylist", stylist);
      model.put("template", "templates/stylist-client-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id/clients", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params("id")));
      String name = request.queryParams("name");
      int age = Integer.parseInt(request.queryParams("age"));
      Client newClient = new Client(name, age, stylist.getId());
      newClient.save();
      model.put("stylist", stylist);
      model.put("clients", Client.allPerStylist(stylist.getId()));
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id/clients/:client_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params("id")));
      Client client = Client.find(Integer.parseInt(request.params("client_id")));
      model.put("stylist", stylist);
      model.put("client", client);
      model.put("template", "templates/stylist-client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/:id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.params("id")));
      model.put("client", client);
      model.put("stylists", Stylist.all());
      model.put("template", "templates/client-update.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/clients/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.params("id")));
      Stylist stylist = Stylist.find(Integer.parseInt(request.queryParams("stylist")));
      client.updateStylist(stylist.getId());
      model.put("stylist", stylist);
      model.put("client", client);
      model.put("template", "templates/stylist-client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/:id/remove", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.params("id")));
      String clientName = client.getName();
      client.removeClient();
      model.put("client", clientName);
      model.put("template", "templates/client-removed.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id/remove", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params("id")));
      if (Client.allPerStylist(stylist.getId()).size() > 0) {
        model.put("stylist", stylist);
        model.put("template", "templates/stylist-remove-fail.vtl");
      } else {
        String stylistName = stylist.getName();
        stylist.removeStylist();
        model.put("stylist", stylistName);
        model.put("template", "templates/stylist-removed.vtl");
      }
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
