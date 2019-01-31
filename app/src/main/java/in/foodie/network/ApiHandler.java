package in.foodie.network;


import in.foodie.pojos.RecipeResponse;
import in.foodie.pojos.RecipeSearchResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Api Handler for Retrofit
 */
public interface ApiHandler {

    final String API_KEY ="2465fecae4090d46a81a45184ffae3d4";

    @GET("/api/search?key="+API_KEY)  //Appending Api Key can also be done using interceptors
    Observable<RecipeSearchResponse> search(@Query("q") String query);

    @GET("/api/get?key="+API_KEY)
    Observable<RecipeResponse> get(@Query("rId") String id);
}
