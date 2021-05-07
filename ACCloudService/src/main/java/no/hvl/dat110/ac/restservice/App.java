package no.hvl.dat110.ac.restservice;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;
import static spark.Spark.post;
import static spark.Spark.delete;

import com.google.gson.Gson;

public class App {
	
	static AccessLog accesslog = null;
	static AccessCode accesscode = null;
	
	public static void main(String[] args) {

		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(8000); // Endret fordi port 8080 gir problemer paa min pc
		}

		// objects for data stored in the service
		
		accesslog = new AccessLog();
		accesscode  = new AccessCode();
		
		after((req, res) -> {
  		  res.type("application/json");
  		});
		
		// for basic testing purposes
		get("/accessdevice/hello", (req, res) -> {
			
		 	Gson gson = new Gson();
		 	
		 	return gson.toJson("IoT Access Control Device");
		});
		
		// TODO: implement the routes required for the access control service
		// as per the HTTP/REST operations described in the project description

		post("/accessdevice/log", (req, res) ->
				new Gson().toJson(accesslog.get(accesslog.add(req.body()))));

		get("/accessdevice/log", (req, res) -> accesslog.toJson());

		get("/accessdevice/log/:id", (req, res) -> {
			int id = Integer.parseInt(req.params("id"));
			return new Gson().toJson(accesslog.get(id));
		});

		put("/accessdevice/code", (req, res) -> {
			Gson gson = new Gson();
			AccessCode code = gson.fromJson(req.body(), AccessCode.class);
			accesscode.setAccesscode(code.getAccesscode());
			return req.body();
		});

		get("/accessdevice/code", (req, res) ->
				new Gson().toJson(accesscode));

		delete("/accessdevice/log", (req, res) -> {
			accesslog.clear();
			return accesslog.toJson();
		});
    }
    
}
