package rsantillanc.sanjoylao.api;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;
import rsantillanc.sanjoylao.model.UserCreatedModel;
import rsantillanc.sanjoylao.model.UserModel;
import rsantillanc.sanjoylao.model.UserSignInModel;

/**
 * Created by rsantillanc on 20/10/2015.
 */

public interface ParseAPIService {
    @Headers({
            "X-Parse-Application-Id: RTM3ioKCBgaAJjXmDRr493sb13uYzGrMnePLhzhm",
            "X-Parse-REST-API-Key: 6xr428CvfJT8WMGByPUhfvJWmFaxjozudaPy9bUB",
            "Content-Type: application/json"
    })
    @POST("users")
    Call<UserCreatedModel> signUp(@Body UserSignInModel userBody);


    @Headers({
            "X-Parse-Application-Id: RTM3ioKCBgaAJjXmDRr493sb13uYzGrMnePLhzhm",
            "X-Parse-REST-API-Key: 6xr428CvfJT8WMGByPUhfvJWmFaxjozudaPy9bUB",
    })
    @GET("login")
    Call<UserModel> login(@Query("username") String username,@Query("password") String password);


}
