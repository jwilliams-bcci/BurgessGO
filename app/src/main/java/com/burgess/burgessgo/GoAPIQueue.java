package com.burgess.burgessgo;

import static com.burgess.burgessgo.Constants.API_PROD_URL;
import static com.burgess.burgessgo.Constants.API_STAGE_URL;
import static com.burgess.burgessgo.Constants.PREF;
import static com.burgess.burgessgo.Constants.*;

import android.content.Context;
import android.content.SharedPreferences;

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
import com.burgess.burgessgo.upcoming_inspections.UpcomingInspectionsViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import data.models.Inspection;

public class GoAPIQueue {
    private static final String TAG = "API";

    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_BEARER = "Bearer ";
    private static final String LOGIN_URL = "Login?userName=%s&password=%s";
    private static final String GET_UPCOMING_INSPECTIONS_URL = "GetUpcomingInspections?builderPersonnelId=%s";

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
                    Inspection inspection = new Inspection();
                    inspection.setInspectionId(obj.optInt("InspectionId"));
                    inspection.setInspectionDate(OffsetDateTime.parse(obj.optString("InspectionDate")));
                    inspection.setInspectionStatus(obj.optString("InspectionStatus"));
                    inspection.setCommunityName(obj.optString("CommunityName"));
                    inspection.setStreetNumber(obj.optString("StreetNumber"));
                    inspection.setStreetName(obj.optString("StreetName"));
                    inspection.setAddressToDisplay(obj.optString("AddressToDisplay"));
                    inspection.setAddressToDisplay2(obj.optString("AddressToDisplay2"));
                    inspection.setTypeName(obj.optString("TypeName"));
                    inspection.setTypeNameDisplay(obj.optString("TypeNameDisplay"));
                    inspection.setInspectionNumber(obj.optInt("InspectionNumber"));
                    inspection.setInspectorName(obj.optString("InspectorName"));
                    inspection.setPhone(obj.optString("Phone"));
                    inspection.setInspectionMissed(obj.getInt("InspectionMissed"));
                    inspection.setCommunityId(obj.optInt("CommunityID"));
                    inspection.setStreetId(obj.optInt("StreetID"));
                    inspection.setCity(obj.optString("City"));
                    vm.insertInspection(inspection);
                } catch (JSONException e) {
                    GoLogger.log('E', TAG, "ERROR in getUpcomingInspections: " + e.getMessage());
                    callback.onFailure("Error in parsing inspection data, please notify support.");
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
                params.put(AUTH_HEADER, AUTH_BEARER + mSharedPreferences.getString("AuthorizationToken", "NULL"));
                return params;
            }
        };
        return request;
    }
}
