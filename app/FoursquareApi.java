import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoursquareApi {
    @GET("venues/search")
    Call<FoursquareResponse> searchHotels(
            @Query("ll") String location,  // Latitude and Longitude
            @Query("query") String query,  // Search term (e.g., "hotel")
            @Query("client_id") String clientId,
            @Query("client_secret") String clientSecret,
            @Query("v") String version  // API version (date)
    );
}
