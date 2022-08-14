package com.burgess.burgessgo;

import static com.burgess.burgessgo.Constants.API_PROD_URL;
import static com.burgess.burgessgo.Constants.API_STAGE_URL;
import static com.burgess.burgessgo.Constants.PREF;
import static com.burgess.burgessgo.Constants.*;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.ViewModel;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.burgess.burgessgo.activate_homes.ActivateHomesViewModel;
import com.burgess.burgessgo.add_new_address.AddNewAddressViewModel;
import com.burgess.burgessgo.deactivate_homes.DeactivateHomesViewModel;
import com.burgess.burgessgo.inspection_defects.InspectionDefectsViewModel;
import com.burgess.burgessgo.location_defects.LocationDefectsViewModel;
import com.burgess.burgessgo.my_homes.MyHomesViewModel;
import com.burgess.burgessgo.non_passed_inspections.NonPassedInspectionsViewModel;
import com.burgess.burgessgo.request_home_access.RequestHomeAccessViewModel;
import com.burgess.burgessgo.share_transfer_homes.ShareTransferHomesViewModel;
import com.burgess.burgessgo.upcoming_inspections.UpcomingInspectionsViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import data.models.ActiveLocation;
import data.models.BuilderPersonnel;
import data.models.Community;
import data.models.Home;
import data.models.Inspection;
import data.models.InspectionDefect;
import data.models.NonPassedInspection;
import data.models.Street;

public class GoAPIQueue {
    private static final String TAG = "GO_API";

    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_BEARER = "Bearer ";
    private static final String LOGIN_URL = "Login?userName=%s&password=%s";
    private static final String GET_UPCOMING_INSPECTIONS_URL = "GetUpcomingInspections?builderPersonnelId=%s";
    private static final String GET_NON_PASSED_INSPECTIONS_URL = "GetNonPassedInspections?builderPersonnelId=%s";
    private static final String GET_INSPECTION_DEFECTS_URL = "GetInspectionDefects?inspectionId=%s";
    private static final String GET_ACTIVE_LOCATIONS_BY_SUPER_URL = "GetActiveLocationsBySuper?builderPersonnelId=%s&source=%s";
    private static final String GET_COMMUNITY_LIST_URL = "GetCommunityList?builderId=%s&userId=%s&source=%s";
    private static final String GET_STATE_LIST_URL = "GetStateList?builderId=%s&userId=%s&source=%s";
    private static final String GET_CITY_LIST_URL = "GetCityList?builderId=%s";
    private static final String GET_COUNTY_LIST_URL = "GetCountyList?builderId=%s&userId=%s&source=%s";
    private static final String GET_STREET_LIST_URL = "GetStreetList?builderId=%s&communityId=%s";
    private static final String GET_HOMES_FOR_ACTIVATION_URL = "GetHomesForActivation?builderPersonnelId=%s";
    private static final String GET_HOMES_FOR_DEACTIVATION_URL = "GetHomesForDeactivation?builderPersonnelId=%s";
    private static final String GET_BUILDER_PERSONNEL_URL = "GetBuilderPersonnel?builderId=%s&locationId=%s";
    private static final String GET_INSPECTIONS_AT_LOCATION_URL = "GetInspectionsAtLocation?locationId=%s";
    private static final String GET_OPEN_DEFECTS_AT_LOCATION_URL = "GetOpenDefectsAtLocation?locationId=%s";

    private static GoAPIQueue instance;
    private RequestQueue queue;
    private static Context ctx;
    private boolean isProd;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private GoAPIQueue(Context context) {
        ctx = context;
        queue = getRequestQueue();
        mSharedPreferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        GoLogger.getInstance(ctx);

        isProd = true;
    }

    public static synchronized GoAPIQueue getInstance(Context context) {
        if (instance == null) {
            instance = new GoAPIQueue(context);
        }
        return instance;
    }

    public static synchronized GoAPIQueue getInstance() {
        if (instance == null) {
            throw new IllegalStateException(GoAPIQueue.class.getSimpleName() + " is not initialized, call getInstance(Context context) first");
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (queue == null) {
            Cache cache = new DiskBasedCache(ctx.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            queue = new RequestQueue(cache, network);
            queue.start();
        }
        return queue;
    }

    public boolean isProd() { return isProd; }

    public JsonObjectRequest loginUser(String userName, String password, final ServerCallback callback) {
        String url = isProd ? API_PROD_URL : API_STAGE_URL;
        url += String.format(LOGIN_URL, userName, password);
        GoLogger.log('I', TAG, "Logging in user " + userName);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null, response -> {
            mEditor.putString(PREF_AUTH_TOKEN, response.optString("AuthorizationToken"));
            mEditor.putInt(PREF_SECURITY_USER_ID, response.optInt("SecurityUserId", -1));
            mEditor.putInt(PREF_BUILDER_ID, response.optInt("BuilderId", -1));
            mEditor.putInt(PREF_BUILDER_PERSONNEL_ID, response.optInt("BuilderPersonnelId", -1));
            mEditor.apply();
            callback.onSuccess("Success");
        }, error -> {
            if (error instanceof NoConnectionError) {
                GoLogger.log('E', TAG, "Lost connection in loginUser");
                callback.onFailure("Lost connection during login!");
            } else if (error instanceof TimeoutError) {
                GoLogger.log('E', TAG, "Request timed out in loginUser");
                callback.onFailure("Connection timed out during login!");
            } else if (error instanceof AuthFailureError) {
                GoLogger.log('E', TAG, "Authentication error in loginUser");
                callback.onFailure("Authentication error! Check username and password and try again!");
            } else {
                String errorMessage = new String(error.networkResponse.data);
                GoLogger.log('E', TAG, "ERROR in login user: " + errorMessage);
                callback.onFailure("Error during login!");
            }
        });
        return request;
    }
    public JsonArrayRequest getUpcomingInspections(UpcomingInspectionsViewModel vm, int builderPersonnelId, final ServerCallback callback) {
        String url = isProd ? API_PROD_URL : API_STAGE_URL;
        url += String.format(GET_UPCOMING_INSPECTIONS_URL, builderPersonnelId);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int lcv = 0; lcv < response.length(); lcv++) {
                try {
                    JSONObject obj = response.getJSONObject(lcv);
                    Inspection i = new Inspection();
                    i.setInspectionId(obj.optInt("InspectionId"));
                    i.setInspectionDate(OffsetDateTime.parse(obj.optString("InspectionDate")));
                    i.setInspectionStatus(obj.optString("InspectionStatus"));
                    i.setCommunityName(obj.optString("CommunityName"));
                    i.setStreetNumber(obj.optString("StreetNumber"));
                    i.setStreetName(obj.optString("StreetName"));
                    i.setAddressToDisplay(obj.optString("AddressToDisplay"));
                    i.setAddressToDisplay2(obj.optString("AddressToDisplay2"));
                    i.setTypeName(obj.optString("TypeName"));
                    i.setTypeNameDisplay(obj.optString("TypeNameDisplay"));
                    i.setInspectionNumber(obj.optInt("InspectionNumber"));
                    i.setInspectorName(obj.optString("InspectorName"));
                    i.setPhone(obj.optString("Phone"));
                    i.setInspectionMissed(obj.getInt("InspectionMissed"));
                    i.setCommunityId(obj.optInt("CommunityID"));
                    i.setStreetId(obj.optInt("StreetID"));
                    i.setCity(obj.optString("City"));
                    vm.insertInspection(i);
                } catch (JSONException e) {
                    GoLogger.log('E', TAG, "ERROR in getUpcomingInspections: " + e.getMessage());
                    callback.onFailure("Error in parsing upcoming inspection data, please notify support.");
                }
            }
            callback.onSuccess("Success");
        }, error -> {
            if (error instanceof NoConnectionError) {
                GoLogger.log('E', TAG, "Lost connection in getUpcomingInspections");
                callback.onFailure("No connection!");
            } else if (error instanceof TimeoutError) {
                GoLogger.log('E', TAG, "Request timed out in getUpcomingInspections");
                callback.onFailure("Request timed out!");
            } else {
                String errorMessage = new String(error.networkResponse.data);
                GoLogger.log('E', TAG, "ERROR in getUpcomingInspections: " + errorMessage);
                callback.onFailure("Error! Please contact support");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(AUTH_HEADER, AUTH_BEARER + mSharedPreferences.getString(PREF_AUTH_TOKEN, "NULL"));
                return params;
            }
        };
        return request;
    }
    public JsonArrayRequest getNonPassedInspections(NonPassedInspectionsViewModel vm, int builderPersonnelId, final ServerCallback callback) {
        String url = isProd ? API_PROD_URL : API_STAGE_URL;
        url += String.format(GET_NON_PASSED_INSPECTIONS_URL, builderPersonnelId);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int lcv = 0; lcv < response.length(); lcv++) {
                try {
                    JSONObject obj = response.getJSONObject(lcv);
                    NonPassedInspection i = new NonPassedInspection();
                    i.setInspectionId(obj.optInt("InspectionId"));
                    i.setInspectionDate(OffsetDateTime.parse(obj.optString("InspectionDate")));
                    i.setInspectionStatus(obj.optString("InspectionStatus"));
                    i.setCommunityName(obj.optString("CommunityName"));
                    i.setStreetNumber(obj.optString("StreetNumber"));
                    i.setStreetName(obj.optString("StreetName"));
                    i.setTypeName(obj.optString("TypeName"));
                    i.setInspectionNumber(obj.optInt("InspectionNumber"));
                    i.setInspectionAddress(obj.optString("InspectionAddress"));
                    i.setItems(obj.optInt("Items"));
                    i.setDaysOld(obj.optInt("DaysOld"));
                    i.setInspectedBy(obj.optString("InspectedBy"));
                    vm.insertInspection(i);
                } catch (JSONException e) {
                    GoLogger.log('E', TAG, "ERROR in getNonPassedInspections: " + e.getMessage());
                    callback.onFailure("Error in parsing non passed inspection data, please notify support");
                }
            }
            callback.onSuccess("Success");
        }, error -> {
            if (error instanceof NoConnectionError) {
                GoLogger.log('E', TAG, "Lost connection in getNonPassedInspections");
                callback.onFailure("No connection!");
            } else if (error instanceof TimeoutError) {
                GoLogger.log('E', TAG, "Request timed out in getNonPassedInspections");
                callback.onFailure("Request timed out!");
            } else {
                String errorMessage = new String(error.networkResponse.data);
                GoLogger.log('E', TAG, "ERROR in getNonPassedInspections: " + errorMessage);
                callback.onFailure("Error! Please contact support");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(AUTH_HEADER, AUTH_BEARER + mSharedPreferences.getString(PREF_AUTH_TOKEN, "NULL"));
                return params;
            }
        };
        return request;
    }
    public JsonArrayRequest getInspectionDefects(InspectionDefectsViewModel vm, int inspectionId, final ServerCallback callback) {
        String url = isProd ? API_PROD_URL : API_STAGE_URL;
        url += String.format(GET_INSPECTION_DEFECTS_URL, inspectionId);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int lcv = 0; lcv < response.length(); lcv++) {
                try {
                    JSONObject obj = response.getJSONObject(lcv);
                    InspectionDefect i = new InspectionDefect();
                    i.setRowId(obj.optInt("RowID"));
                    i.setLocationId(obj.optInt("LocationID"));
                    i.setInspectionId(obj.optInt("InspectionId"));
                    i.setInspectionNumber(obj.optInt("InspectionNumber"));
                    i.setInspectionDate(OffsetDateTime.parse(obj.optString("InspectionDate")));
                    i.setDefectCategoryDisplayName(obj.optString("DefectCategoryDisplayName"));
                    i.setDefectItemDescription(obj.optString("DefectItemDescription"));
                    i.setDeviationText(obj.optString("DeviationText"));
                    i.setColumnHeader1(obj.optString("ColumnHeader1"));
                    i.setColumnHeader2(obj.optString("ColumnHeader2"));
                    i.setSourceId(obj.optInt("SourceID"));
                    i.setOrderBy(obj.optInt("OrderBy"));
                    vm.insertInspectionDefect(i);
                } catch (JSONException e) {
                    GoLogger.log('E', TAG, "ERROR in getInspectionDefects: " + e.getMessage());
                    callback.onFailure("Error in parsing inspection defect data, please notify support");
                }
            }
            callback.onSuccess("Success");
        }, error -> {
            if (error instanceof NoConnectionError) {
                GoLogger.log('E', TAG, "Lost connection in getInspectionDefects");
                callback.onFailure("No connection!");
            } else if (error instanceof TimeoutError) {
                GoLogger.log('E', TAG, "Request timed out in getInspectionDefects");
                callback.onFailure("Request timed out!");
            } else {
                String errorMessage = new String(error.networkResponse.data);
                GoLogger.log('E', TAG, "ERROR in getInspectionDefects: " + errorMessage);
                callback.onFailure("Error! Please contact support");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(AUTH_HEADER, AUTH_BEARER + mSharedPreferences.getString(PREF_AUTH_TOKEN, "NULL"));
                return params;
            }
        };
        return request;
    }
    public JsonArrayRequest getActiveLocationsBySuper(MyHomesViewModel vm, int builderPersonnelId, String source, final ServerCallback callback) {
        String url = isProd ? API_PROD_URL : API_STAGE_URL;
        url += String.format(GET_ACTIVE_LOCATIONS_BY_SUPER_URL, builderPersonnelId, source);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int lcv = 0; lcv < response.length(); lcv++) {
                try {
                    JSONObject obj = response.getJSONObject(lcv);
                    ActiveLocation i = new ActiveLocation();
                    i.setLocationId(obj.optInt("LocationID"));
                    i.setCommunity(obj.optString("Community"));
                    i.setAddress(obj.optString("Address"));
                    i.setInspectionCount(obj.optInt("InspectionCount"));
                    i.setInspectionCountRemaining(obj.optInt("InspectionCountRemaining"));
                    i.setStreetName(obj.optString("StreetName"));
                    i.setMAXBuilderPersonnelIDRequestingAccess(obj.optInt("MAXBuilderPersonnelIDRequestingAccess"));
                    vm.insertActiveLocation(i);
                } catch (JSONException e) {
                    GoLogger.log('E', TAG, "ERROR in getInspectionDefects: " + e.getMessage());
                    callback.onFailure("Error in parsing active location data, please notify support");
                }
            }
            callback.onSuccess("Success");
        }, error -> {
            if (error instanceof NoConnectionError) {
                GoLogger.log('E', TAG, "Lost connection in getActiveLocationsBySuper");
                callback.onFailure("No connection!");
            } else if (error instanceof TimeoutError) {
                GoLogger.log('E', TAG, "Request timed out in getActiveLocationsBySuper");
                callback.onFailure("Request timed out!");
            } else {
                String errorMessage = new String(error.networkResponse.data);
                GoLogger.log('E', TAG, "ERROR in getActiveLocationsBySuper: " + errorMessage);
                callback.onFailure("Error! Please contact support");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(AUTH_HEADER, AUTH_BEARER + mSharedPreferences.getString(PREF_AUTH_TOKEN, "NULL"));
                return params;
            }
        };
        return request;
    }
    public JsonArrayRequest getCommunityList(AddNewAddressViewModel vm, int builderId, Integer userId, String source, final ServerCallback callback) {
        String url = isProd ? API_PROD_URL : API_STAGE_URL;
        url += String.format(GET_COMMUNITY_LIST_URL, builderId, userId, source);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int lcv = 0; lcv < response.length(); lcv++) {
                try {
                    JSONObject obj = response.getJSONObject(lcv);
                    Community community = new Community();
                    community.setCommunityId(obj.optInt("CommunityID"));
                    community.setAreaId(obj.optInt("AreaID"));
                    community.setCommunityName(obj.optString("CommunityName"));
                    community.setCityId1(obj.optInt("CityID1"));
                    community.setCityName1(obj.optString("CityName1"));
                    community.setCityId2(obj.optInt("CityID2"));
                    community.setCityName2(obj.optString("CityName2"));
                    community.setState(obj.optString("State"));
                    community.setAdditional(obj.optString("Additional"));
                    community.setAreaName(obj.optString("AreaName"));
                    vm.insertCommunity(community);
                } catch (JSONException e) {
                    GoLogger.log('E', TAG, "ERROR in getCommunityList: " + e.getMessage());
                    callback.onFailure("Error in parsing community data, please notify support");
                }
            }
            callback.onSuccess("Success");
        }, error -> {
            if (error instanceof NoConnectionError) {
                GoLogger.log('E', TAG, "Lost connection in getCommunityList");
                callback.onFailure("No connection!");
            } else if (error instanceof TimeoutError) {
                GoLogger.log('E', TAG, "Request timed out in getCommunityList");
                callback.onFailure("Request timed out!");
            } else {
                String errorMessage = new String(error.networkResponse.data);
                GoLogger.log('E', TAG, "ERROR in getCommunityList: " + errorMessage);
                callback.onFailure("Error! Please contact support");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(AUTH_HEADER, AUTH_BEARER + mSharedPreferences.getString(PREF_AUTH_TOKEN, "NULL"));
                return params;
            }
        };
        return request;
    }
    public JsonArrayRequest getStateList(AddNewAddressViewModel vm, int builderId, Integer userId, String source, final ServerCallback callback) {
        String url = isProd ? API_PROD_URL : API_STAGE_URL;
        url += String.format(GET_STATE_LIST_URL, builderId, userId, source);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int lcv = 0; lcv < response.length(); lcv++) {
                try {
                    JSONObject obj = response.getJSONObject(lcv);
                    vm.insertState(obj.optString("InspectionState"));
                } catch (JSONException e) {
                    GoLogger.log('E', TAG, "ERROR in getStateList: " + e.getMessage());
                    callback.onFailure("Error in parsing state data, please notify support");
                }
            }
            callback.onSuccess("Success");
        }, error -> {
            if (error instanceof NoConnectionError) {
                GoLogger.log('E', TAG, "Lost connection in getStateList");
                callback.onFailure("No connection!");
            } else if (error instanceof TimeoutError) {
                GoLogger.log('E', TAG, "Request timed out in getStateList");
                callback.onFailure("Request timed out!");
            } else {
                String errorMessage = new String(error.networkResponse.data);
                GoLogger.log('E', TAG, "ERROR in getStateList: " + errorMessage);
                callback.onFailure("Error! Please contact support");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(AUTH_HEADER, AUTH_BEARER + mSharedPreferences.getString(PREF_AUTH_TOKEN, "NULL"));
                return params;
            }
        };
        return request;
    }
    public JsonArrayRequest getCityList(AddNewAddressViewModel vm, int builderId, final ServerCallback callback) {
        String url = isProd ? API_PROD_URL : API_STAGE_URL;
        url += String.format(GET_CITY_LIST_URL, builderId);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int lcv = 0; lcv < response.length(); lcv++) {
                try {
                    JSONObject obj = response.getJSONObject(lcv);
                    vm.insertCity(obj.optString("City"));
                } catch (JSONException e) {
                    GoLogger.log('E', TAG, "ERROR in getCityList: " + e.getMessage());
                    callback.onFailure("Error in parsing city data, please notify support");
                }
            }
            callback.onSuccess("Success");
        }, error -> {
            if (error instanceof NoConnectionError) {
                GoLogger.log('E', TAG, "Lost connection in getCityList");
                callback.onFailure("No connection!");
            } else if (error instanceof TimeoutError) {
                GoLogger.log('E', TAG, "Request timed out in getCityList");
                callback.onFailure("Request timed out!");
            } else {
                String errorMessage = new String(error.networkResponse.data);
                GoLogger.log('E', TAG, "ERROR in getCityList: " + errorMessage);
                callback.onFailure("Error! Please contact support");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(AUTH_HEADER, AUTH_BEARER + mSharedPreferences.getString(PREF_AUTH_TOKEN, "NULL"));
                return params;
            }
        };
        return request;
    }
    public JsonArrayRequest getCountyList(AddNewAddressViewModel vm, int builderId, Integer userId, String source, final ServerCallback callback) {
        String url = isProd ? API_PROD_URL : API_STAGE_URL;
        url += String.format(GET_COUNTY_LIST_URL, builderId, userId, source);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int lcv = 0; lcv < response.length(); lcv++) {
                try {
                    JSONObject obj = response.getJSONObject(lcv);
                    vm.insertCounty(obj.optString("CountyName"));
                } catch (JSONException e) {
                    GoLogger.log('E', TAG, "ERROR in getCountyList: " + e.getMessage());
                    callback.onFailure("Error in parsing county data, please notify support");
                }
            }
            callback.onSuccess("Success");
        }, error -> {
            if (error instanceof NoConnectionError) {
                GoLogger.log('E', TAG, "Lost connection in getCountyList");
                callback.onFailure("No connection!");
            } else if (error instanceof TimeoutError) {
                GoLogger.log('E', TAG, "Request timed out in getCountyList");
                callback.onFailure("Request timed out!");
            } else {
                String errorMessage = new String(error.networkResponse.data);
                GoLogger.log('E', TAG, "ERROR in getCountyList: " + errorMessage);
                callback.onFailure("Error! Please contact support");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(AUTH_HEADER, AUTH_BEARER + mSharedPreferences.getString(PREF_AUTH_TOKEN, "NULL"));
                return params;
            }
        };
        return request;
    }
    public JsonArrayRequest getStreetList(AddNewAddressViewModel vm, int builderId, int communityId, final ServerCallback callback) {
        String url = isProd ? API_PROD_URL : API_STAGE_URL;
        url += String.format(GET_STREET_LIST_URL, builderId, communityId);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int lcv = 0; lcv < response.length(); lcv++) {
                try {
                    JSONObject obj = response.getJSONObject(lcv);
                    Street street = new Street();
                    street.setStreetNameIdMax(obj.optInt("StreetNameIDMax"));
                    street.setStreetName(obj.optString("StreetName"));
                    street.setCommunityId(obj.optInt("CommunityID"));
                    street.setCityId(obj.optInt("CityID"));
                    vm.insertStreet(street);
                } catch (JSONException e) {
                    GoLogger.log('E', TAG, "ERROR in getStreetList: " + e.getMessage());
                    callback.onFailure("Error in parsing street data, please notify support");
                }
            }
            callback.onSuccess("Success");
        }, error -> {
            if (error instanceof NoConnectionError) {
                GoLogger.log('E', TAG, "Lost connection in getStreetList");
                callback.onFailure("No connection!");
            } else if (error instanceof TimeoutError) {
                GoLogger.log('E', TAG, "Request timed out in getStreetList");
                callback.onFailure("Request timed out!");
            } else {
                String errorMessage = new String(error.networkResponse.data);
                GoLogger.log('E', TAG, "ERROR in getStreetList: " + errorMessage);
                callback.onFailure("Error! Please contact support");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(AUTH_HEADER, AUTH_BEARER + mSharedPreferences.getString(PREF_AUTH_TOKEN, "NULL"));
                return params;
            }
        };
        return request;
    }
    public JsonArrayRequest getInactiveHomes(ActivateHomesViewModel vm, int builderPersonnelId, final ServerCallback callback) {
        String url = isProd ? API_PROD_URL : API_STAGE_URL;
        url += String.format(GET_HOMES_FOR_ACTIVATION_URL, builderPersonnelId);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int lcv = 0; lcv < response.length(); lcv++) {
                try {
                    JSONObject obj = response.getJSONObject(lcv);
                    Home i = new Home();
                    i.setLocationId(obj.optInt("LocationID"));
                    i.setCommunity(obj.optString("Community"));
                    i.setAddress(obj.optString("Address"));
                    vm.insertInactiveHome(i);
                } catch (JSONException e) {
                    GoLogger.log('E', TAG, "ERROR in getInactiveHomes: " + e.getMessage());
                    callback.onFailure("Error in parsing inactive home data, please notify support");
                }
            }
            callback.onSuccess("Success");
        }, error -> {
            if (error instanceof NoConnectionError) {
                GoLogger.log('E', TAG, "Lost connection in getInactiveHomes");
                callback.onFailure("No connection!");
            } else if (error instanceof TimeoutError) {
                GoLogger.log('E', TAG, "Request timed out in getInactiveHomes");
                callback.onFailure("Request timed out!");
            } else {
                String errorMessage = new String(error.networkResponse.data);
                GoLogger.log('E', TAG, "ERROR in getInactiveHomes: " + errorMessage);
                callback.onFailure("Error! Please contact support");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(AUTH_HEADER, AUTH_BEARER + mSharedPreferences.getString(PREF_AUTH_TOKEN, "NULL"));
                return params;
            }
        };
        return request;
    }
    public JsonArrayRequest getActiveHomes(ViewModel vm, int builderPersonnelId, final ServerCallback callback) {
        String url = isProd ? API_PROD_URL : API_STAGE_URL;
        url += String.format(GET_HOMES_FOR_DEACTIVATION_URL, builderPersonnelId);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int lcv = 0; lcv < response.length(); lcv++) {
                try {
                    JSONObject obj = response.getJSONObject(lcv);
                    Home i = new Home();
                    i.setLocationId(obj.optInt("LocationID"));
                    i.setCommunity(obj.optString("Community"));
                    i.setAddress(obj.optString("Address"));
                    if (vm instanceof DeactivateHomesViewModel) {
                        ((DeactivateHomesViewModel) vm).insertActiveHome(i);
                    } else if (vm instanceof ShareTransferHomesViewModel) {
                        ((ShareTransferHomesViewModel) vm).insertActiveHome(i);
                    } else if (vm instanceof RequestHomeAccessViewModel) {
                        ((RequestHomeAccessViewModel) vm).insertActiveHome(i);
                    }
                } catch (JSONException e) {
                    GoLogger.log('E', TAG, "ERROR in getActiveHomes: " + e.getMessage());
                    callback.onFailure("Error in parsing active home data, please notify support");
                }
            }
            callback.onSuccess("Success");
        }, error -> {
            if (error instanceof NoConnectionError) {
                GoLogger.log('E', TAG, "Lost connection in getActiveHomes");
                callback.onFailure("No connection!");
            } else if (error instanceof TimeoutError) {
                GoLogger.log('E', TAG, "Request timed out in getActiveHomes");
                callback.onFailure("Request timed out!");
            } else {
                String errorMessage = new String(error.networkResponse.data);
                GoLogger.log('E', TAG, "ERROR in getActiveHomes: " + errorMessage);
                callback.onFailure("Error! Please contact support");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(AUTH_HEADER, AUTH_BEARER + mSharedPreferences.getString(PREF_AUTH_TOKEN, "NULL"));
                return params;
            }
        };
        return request;
    }
    public JsonArrayRequest getBuilderPersonnel(ViewModel vm, int builderId, int locationId, final ServerCallback callback) {
        String url = isProd ? API_PROD_URL : API_STAGE_URL;
        url += String.format(GET_BUILDER_PERSONNEL_URL, builderId, locationId);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int lcv = 0; lcv < response.length(); lcv++) {
                try {
                    JSONObject obj = response.getJSONObject(lcv);
                    BuilderPersonnel i = new BuilderPersonnel();
                    i.setBuilderPersonnelId(obj.optInt("BuilderPersonnelID"));
                    i.setPersonnelName(obj.optString("PersonnelName"));
                    i.setInitials(obj.optString("Initials"));
                    i.setAddress1(obj.optString("Address1"));
                    i.setAddress2(obj.optString("Address2"));
                    if (vm instanceof ShareTransferHomesViewModel) {
                        ((ShareTransferHomesViewModel) vm).insertBuilderPersonnel(i);
                    } else if (vm instanceof RequestHomeAccessViewModel) {
                        ((RequestHomeAccessViewModel) vm).insertBuilderPersonnel(i);
                    }
                } catch (JSONException e) {
                    GoLogger.log('E', TAG, "ERROR in getBuilderPersonnel: " + e.getMessage());
                    callback.onFailure("Error in parsing builder personnel data, please notify support");
                }
            }
            callback.onSuccess("Success");
        }, error -> {
            if (error instanceof NoConnectionError) {
                GoLogger.log('E', TAG, "Lost connection in getBuilderPersonnel");
                callback.onFailure("No connection!");
            } else if (error instanceof TimeoutError) {
                GoLogger.log('E', TAG, "Request timed out in getBuilderPersonnel");
                callback.onFailure("Request timed out!");
            } else {
                String errorMessage = new String(error.networkResponse.data);
                GoLogger.log('E', TAG, "ERROR in getBuilderPersonnel: " + errorMessage);
                callback.onFailure("Error! Please contact support");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(AUTH_HEADER, AUTH_BEARER + mSharedPreferences.getString("AuthorizationToken", "NULL"));
                return params;
            }
        };
        return request;
    }
    public JsonArrayRequest getInspectionsAtLocation(MyHomesViewModel vm, int locationId, final ServerCallback callback) {
        String url = isProd ? API_PROD_URL : API_STAGE_URL;
        url += String.format(GET_INSPECTIONS_AT_LOCATION_URL, locationId);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int lcv = 0; lcv < response.length(); lcv++) {
                try {
                    JSONObject obj = response.getJSONObject(lcv);
                    Inspection i = new Inspection();
                    i.setInspectionId(obj.optInt("InspectionId"));
                    i.setInspectionDate(OffsetDateTime.parse(obj.optString("InspectionDate")));
                    i.setTypeName(obj.optString("InspectionType"));
                    i.setResolution(obj.optString("Resolution"));
                    vm.insertInspection(i);
                } catch (JSONException e) {
                    GoLogger.log('E', TAG, "ERROR in getInspectionsAtLocation: " + e.getMessage());
                    callback.onFailure("Error in parsing inspection data, please notify support");
                }
            }
            callback.onSuccess("Success");
        }, error -> {
            if (error instanceof NoConnectionError) {
                GoLogger.log('E', TAG, "Lost connection in getInspectionsAtLocation");
                callback.onFailure("No connection!");
            } else if (error instanceof TimeoutError) {
                GoLogger.log('E', TAG, "Request timed out in getInspectionsAtLocation");
                callback.onFailure("Request timed out!");
            } else {
                String errorMessage = new String(error.networkResponse.data);
                GoLogger.log('E', TAG, "ERROR in getInspectionsAtLocation: " + errorMessage);
                callback.onFailure("Error! Please contact support");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(AUTH_HEADER, AUTH_BEARER + mSharedPreferences.getString(PREF_AUTH_TOKEN, "NULL"));
                return params;
            }
        };
        return request;
    }
    public JsonArrayRequest getDefectsAtLocation(LocationDefectsViewModel vm, int locationId, final ServerCallback callback) {
        String url = isProd ? API_PROD_URL : API_STAGE_URL;
        url += String.format(GET_OPEN_DEFECTS_AT_LOCATION_URL, locationId);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int lcv = 0; lcv < response.length(); lcv++) {
                try {
                    JSONObject obj = response.getJSONObject(lcv);
                    InspectionDefect i = new InspectionDefect();
                    i.setRowId(obj.optInt("RowID"));
                    i.setDefectCategoryDisplayName(obj.optString("DefectCategoryDisplayName"));
                    i.setDefectItemDescription(obj.optString("DefectItemDescription"));
                    i.setDeviationText(obj.optString("DeviationText"));
                    i.setColumnHeader1(obj.optString("ColumnHeader1"));
                    i.setColumnHeader2(obj.optString("ColumnHeader2"));
                    vm.insertInspectionDefect(i);
                } catch (JSONException e) {
                    GoLogger.log('E', TAG, "ERROR in getDefectsAtLocation: " + e.getMessage());
                    callback.onFailure("Error in parsing defect data, please notify support");
                }
            }
        }, error -> {
            if (error instanceof NoConnectionError) {
                GoLogger.log('E', TAG, "Lost connection in getDefectsAtLocation");
                callback.onFailure("No connection!");
            } else if (error instanceof TimeoutError) {
                GoLogger.log('E', TAG, "Request timed out in getDefectsAtLocation");
                callback.onFailure("Request timed out!");
            } else {
                String errorMessage = new String(error.networkResponse.data);
                GoLogger.log('E', TAG, "ERROR in getDefectsAtLocation: " + errorMessage);
                callback.onFailure("Error! Please contact support");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(AUTH_HEADER, AUTH_BEARER + mSharedPreferences.getString(PREF_AUTH_TOKEN, "NULL"));
                return params;
            }
        };
        return request;
    }
}
