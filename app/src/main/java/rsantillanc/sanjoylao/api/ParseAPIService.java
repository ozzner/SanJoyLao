package rsantillanc.sanjoylao.api;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;
import rsantillanc.sanjoylao.model.APISignInModel;
import rsantillanc.sanjoylao.model.APIUserCreatedModel;
import rsantillanc.sanjoylao.model.UserModel;

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
    Call<APIUserCreatedModel> signUp(@Body APISignInModel userBody);


    @Headers({
            "X-Parse-Application-Id: RTM3ioKCBgaAJjXmDRr493sb13uYzGrMnePLhzhm",
            "X-Parse-REST-API-Key: 6xr428CvfJT8WMGByPUhfvJWmFaxjozudaPy9bUB",
    })
    @GET("login")
    Call<UserModel> login(@Query("username") String username,@Query("password") String password);


    @Headers({
            "X-Parse-Application-Id: RTM3ioKCBgaAJjXmDRr493sb13uYzGrMnePLhzhm",
            "X-Parse-REST-API-Key: 6xr428CvfJT8WMGByPUhfvJWmFaxjozudaPy9bUB",

    })
    @GET("clases/Plate")
    Call<List<String>> getAllPlates(@Query("include") String idCategory);

}
