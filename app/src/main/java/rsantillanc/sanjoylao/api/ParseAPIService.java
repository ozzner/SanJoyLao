package rsantillanc.sanjoylao.api;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;
import rsantillanc.sanjoylao.model.BasicAutentication;
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
    Call<UserModel> signingUp(@Body BasicAutentication JsonBody);
}
