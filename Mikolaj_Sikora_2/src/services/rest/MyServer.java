package services.rest;

import javax.json.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Path("/server")
public class MyServer {

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void newAirQualityQuery(@FormParam("latitude") String latitude,
                        @FormParam("longitude") String longitude,
                        @Context HttpServletResponse servletResponse,
                        @Context HttpServletRequest servletRequest) throws IOException {
        HttpSession session = servletRequest.getSession();

        double myGegrLat = Double.parseDouble(latitude);
        double myGegrLon = Double.parseDouble(longitude);
        URL geoCodeURL = new URL("http://geocode.xyz/"+myGegrLat+","+myGegrLon+"?geoit=json");
        HttpURLConnection con = (HttpURLConnection) geoCodeURL.openConnection();
        con.setRequestMethod("GET");
        if(con.getResponseCode()==200){
            JsonReaderFactory readerFactory = Json.createReaderFactory(Collections.emptyMap());
            JsonReader jsonReader = readerFactory.createReader(con.getInputStream());
            JsonObject jsonObject = jsonReader.readObject();
            session.setAttribute("address",jsonObject.getString("city")+", "+jsonObject.getString("staddress")+", "+Integer.parseInt(jsonObject.getString("stnumber")));
        }

        URL allStationsURL = new URL("http://api.gios.gov.pl/pjp-api/rest/station/findAll");
        con = (HttpURLConnection) allStationsURL.openConnection();
        con.setRequestMethod("GET");

        if(con.getResponseCode()==200){
            JsonReaderFactory readerFactory = Json.createReaderFactory(Collections.emptyMap());
            JsonReader jsonReader = readerFactory.createReader(con.getInputStream());
            JsonArray jsonArray = jsonReader.readArray();

            double closestGegrLat = 0;
            double closestGegrLon = 0;
            int closestId = -1;
            String closestName = "";

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jb = jsonArray.getJsonObject(i);
                int id = jb.getInt("id");
                double gegrLat= Double.parseDouble(jb.getString("gegrLat"));
                double gegrLon= Double.parseDouble(jb.getString("gegrLon"));
                String name = jb.getString("stationName");
                if(distanceSquared(myGegrLat,myGegrLon,gegrLat,gegrLon)<distanceSquared(myGegrLat,myGegrLon,closestGegrLat,closestGegrLon)){
                    closestGegrLat = gegrLat;
                    closestGegrLon = gegrLon;
                    closestId = id;
                    closestName = name;
                }
            }
            session.setAttribute("stationAddress",closestName);

            URL myStationURL = new URL("http://api.gios.gov.pl/pjp-api/rest/station/sensors/"+closestId);
            con = (HttpURLConnection) myStationURL.openConnection();
            con.setRequestMethod("GET");

            if(con.getResponseCode()==200) {
                readerFactory = Json.createReaderFactory(Collections.emptyMap());
                jsonReader = readerFactory.createReader(con.getInputStream());
                jsonArray = jsonReader.readArray();
                List<Integer> sensorIds = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonObject = jsonArray.getJsonObject(i);
                    sensorIds.add(jsonObject.getInt("id"));
                }

                Map<String,Double> actualValues = new HashMap<>();
                Map <String,Double> maxValues = new HashMap<>();
                Map <String,Double> minValues = new HashMap<>();

                for(Integer sensorId: sensorIds){
                    URL sensorURL = new URL("http://api.gios.gov.pl/pjp-api/rest/data/getData/"+sensorId);
                    con = (HttpURLConnection) sensorURL.openConnection();
                    con.setRequestMethod("GET");

                    if(con.getResponseCode()==200){
                        readerFactory = Json.createReaderFactory(Collections.emptyMap());
                        jsonReader = readerFactory.createReader(con.getInputStream());
                        JsonObject jsonObject = jsonReader.readObject();

                        boolean first = true;

                        String key = jsonObject.getString("key");
                        jsonArray = jsonObject.getJsonArray("values");
                        double maxValue = -1;
                        double minValue = 100000000;
                        for (int i = 0; i < jsonArray.size(); i++) {
                            if(i>=24) break;
                            JsonObject jo = jsonArray.getJsonObject(i);
                            JsonValue value = jo.get("value");

                            if(value.getValueType() != JsonValue.ValueType.NULL){
                                double doubleValue = jo.getJsonNumber("value").doubleValue();
                                if(first){
                                    actualValues.put(key,doubleValue);
                                    first = false;
                                } else{
                                    if(doubleValue>maxValue){
                                        maxValue = doubleValue;
                                    }
                                    if(doubleValue<minValue){
                                        minValue = doubleValue;
                                    }
                                }
                            }
                        }
                        maxValues.put(key,maxValue);
                        minValues.put(key,minValue);
                    }
                }
                for(String parameter: actualValues.keySet()){
                    session.setAttribute(parameter.replace(".","")+"actual",actualValues.get(parameter));
                }
                for(String parameter: maxValues.keySet()){
                    session.setAttribute(parameter.replace(".","")+"max",maxValues.get(parameter));
                }
                for(String parameter: minValues.keySet()){
                    session.setAttribute(parameter.replace(".","")+"min",minValues.get(parameter));
                }

                URL indexClosestURL = new URL("http://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/"+closestId);
                con = (HttpURLConnection) indexClosestURL.openConnection();
                con.setRequestMethod("GET");

                Map<String,String> indexes = new HashMap<>();

                if(con.getResponseCode()==200){
                    readerFactory = Json.createReaderFactory(Collections.emptyMap());
                    jsonReader = readerFactory.createReader(con.getInputStream());
                    JsonObject jsonObject = jsonReader.readObject();
                    for(String parameter: actualValues.keySet()){
                        JsonObject paramObject = jsonObject.getJsonObject(parameter.replace(".","").toLowerCase()+"IndexLevel");
                        indexes.put(parameter,paramObject.getString("indexLevelName"));
                    }
                }
                System.out.println(indexes);
                for(String parameter: indexes.keySet()){
                    session.setAttribute(parameter.replace(".","")+"index",indexes.get(parameter));
                }
            }
        }
        servletResponse.sendRedirect("../result.jsp");
    }

    private static double distanceSquared(double myGegrLat, double myGegrLon, double gegrLat, double gegrLon){
        return (myGegrLat-gegrLat)*(myGegrLat-gegrLat)+(myGegrLon-gegrLon)*(myGegrLon-gegrLon);
    }
}
