package rsantillanc.sanjoylao.api.service;

import com.google.gson.JsonObject;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;
import rsantillanc.sanjoylao.model.APIResultCategoryModel;
import rsantillanc.sanjoylao.model.APIResultPlateModel;
import rsantillanc.sanjoylao.model.APISignInModel;
import rsantillanc.sanjoylao.model.APIUserCreatedModel;
import rsantillanc.sanjoylao.model.OrderModel;
import rsantillanc.sanjoylao.model.OrderTypeModel;
import rsantillanc.sanjoylao.model.PlateModel;
import rsantillanc.sanjoylao.model.PlateSizeModel;
import rsantillanc.sanjoylao.model.SizeModel;
import rsantillanc.sanjoylao.model.StatusModel;
import rsantillanc.sanjoylao.model.UserModel;

/**
 * Created by RenzoD on 03/11/2015.
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
    Call<UserModel> login(@Query("username") String username, @Query("password") String password);


    @Headers({
            "X-Parse-Application-Id: RTM3ioKCBgaAJjXmDRr493sb13uYzGrMnePLhzhm",
            "X-Parse-REST-API-Key: 6xr428CvfJT8WMGByPUhfvJWmFaxjozudaPy9bUB",


    })
    @GET("classes/Plate?include=idCategory")
    Call<APIResultPlateModel> getPlatesWhereClausule(@Query(value = "where", encoded = true) String jsonWhere);


    @Headers({
            "X-Parse-Application-Id: RTM3ioKCBgaAJjXmDRr493sb13uYzGrMnePLhzhm",
            "X-Parse-REST-API-Key: 6xr428CvfJT8WMGByPUhfvJWmFaxjozudaPy9bUB",
    })
    @GET("classes/Category")
    Call<APIResultCategoryModel> getCategories();


    @Headers({
            "X-Parse-Application-Id: RTM3ioKCBgaAJjXmDRr493sb13uYzGrMnePLhzhm",
            "X-Parse-REST-API-Key: 6xr428CvfJT8WMGByPUhfvJWmFaxjozudaPy9bUB",
    })
    @GET("classes/Size")
    Call<SizeModel> getAllSizes();



    @Headers({
            "X-Parse-Application-Id: RTM3ioKCBgaAJjXmDRr493sb13uYzGrMnePLhzhm",
            "X-Parse-REST-API-Key: 6xr428CvfJT8WMGByPUhfvJWmFaxjozudaPy9bUB",
    })
    @GET("classes/PlateSize?include=idSize")
    Call<PlateSizeModel> getAllPlatesSize();



    @Headers({
            "X-Parse-Application-Id: RTM3ioKCBgaAJjXmDRr493sb13uYzGrMnePLhzhm",
            "X-Parse-REST-API-Key: 6xr428CvfJT8WMGByPUhfvJWmFaxjozudaPy9bUB",
    })
    @GET("classes/Plate?include=idCategory")
    Call<PlateModel> getAllPlates();

    @Headers({
            "X-Parse-Application-Id: RTM3ioKCBgaAJjXmDRr493sb13uYzGrMnePLhzhm",
            "X-Parse-REST-API-Key: 6xr428CvfJT8WMGByPUhfvJWmFaxjozudaPy9bUB",
    })
    @GET("classes/OrderType")
    Call<OrderTypeModel> getAllOrderTypes();


    @Headers({
            "X-Parse-Application-Id: RTM3ioKCBgaAJjXmDRr493sb13uYzGrMnePLhzhm",
            "X-Parse-REST-API-Key: 6xr428CvfJT8WMGByPUhfvJWmFaxjozudaPy9bUB",
    })
    @GET("classes/Status")
    Call<StatusModel> getAllStatus();


    @Headers({
            "X-Parse-Application-Id: RTM3ioKCBgaAJjXmDRr493sb13uYzGrMnePLhzhm",
            "X-Parse-REST-API-Key: 6xr428CvfJT8WMGByPUhfvJWmFaxjozudaPy9bUB",
            "Content-Type: application/json"
    })
    @POST("classes/Order")
    Call<JsonObject> createOrder(@Body OrderModel neworder);
}
