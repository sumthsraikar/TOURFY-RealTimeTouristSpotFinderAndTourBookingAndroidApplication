import java.util.List;

public class FoursquareResponse {
    public Response response;

    public class Response {
        public List<Venue> venues;
    }

    public class Venue {
        public String name;
        public Location location;

        public class Location {
            public double lat;
            public double lng;
        }
    }
}
