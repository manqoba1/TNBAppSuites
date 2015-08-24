package com.example.sifiso.tnblibrary.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.sifiso.tnblibrary.models.ClerkDTO;
import com.example.sifiso.tnblibrary.models.CommunitymemberDTO;
import com.example.sifiso.tnblibrary.models.IssueimageDTO;
import com.example.sifiso.tnblibrary.models.IssuesDTO;
import com.example.sifiso.tnblibrary.models.MunicipalityDTO;
import com.example.sifiso.tnblibrary.models.ReportedissueDTO;
import com.example.sifiso.tnblibrary.models.RequestDTO;
import com.example.sifiso.tnblibrary.models.ResponseDTO;
import com.example.sifiso.tnblibrary.models.StatusDTO;
import com.example.sifiso.tnblibrary.models.StatusreportedissueDTO;
import com.example.sifiso.tnblibrary.models.TownDTO;
import com.example.sifiso.tnblibrary.models.WardsDTO;
import com.example.sifiso.tnblibrary.toolbox.BaseVolley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sifiso on 2015-05-02.
 */
public class DataUtil {
    static DataUtilInterface dataUtilInterface;

    public interface DataUtilInterface {
        public void onResponse(JSONObject r);

        public void onError(String error);

    }

    public static void login(final Context ctx, String email, String pin, final DataUtilInterface listener) {

        dataUtilInterface = listener;
        RequestDTO req = new RequestDTO();
        req.setEmail(email);
        req.setPassword(pin);


        try {
            BaseVolley.getRemoteData(Constant.LOGIN, req, ctx, new BaseVolley.BohaVolleyListener() {
                @Override
                public void onResponseReceived(JSONObject response) {
                    try {
                        if (response.getInt("success") <= 0) {
                            Util.showErrorToast(ctx, response.getString("message"));
                            dataUtilInterface.onError(response.getString("message"));
                            return;
                        }
                        dataUtilInterface.onResponse(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onVolleyError(VolleyError error) {
                    dataUtilInterface.onError(error.toString());
                }
            });
        } catch (Exception e) {
            dataUtilInterface.onError(e.toString());
        }
    }

    public static void loginClerk(final Context ctx, String email, String pin, final DataUtilInterface listener) {

        dataUtilInterface = listener;
        RequestDTO req = new RequestDTO();
        req.setEmail(email);
        req.setPassword(pin);


        try {
            BaseVolley.getRemoteData(Constant.LOGIN_CLERK, req, ctx, new BaseVolley.BohaVolleyListener() {
                @Override
                public void onResponseReceived(JSONObject response) {
                    try {
                        if (response.getInt("success") <= 0) {
                            Util.showErrorToast(ctx, response.getString("message"));
                            dataUtilInterface.onError(response.getString("message"));
                            return;
                        }
                        dataUtilInterface.onResponse(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onVolleyError(VolleyError error) {
                    dataUtilInterface.onError(error.toString());
                }
            });
        } catch (Exception e) {
            dataUtilInterface.onError(e.toString());
        }
    }

    public static void forgotPassword(final Context ctx, String email, final DataUtilInterface listener) {

        dataUtilInterface = listener;
        RequestDTO req = new RequestDTO();
        req.setEmail(email);

        try {
            BaseVolley.getRemoteData(Constant.FORGOT_PASSWORD, req, ctx, new BaseVolley.BohaVolleyListener() {
                @Override
                public void onResponseReceived(JSONObject response) {
                    try {
                        if (response.getInt("success") <= 0) {
                            Util.showErrorToast(ctx, response.getString("message"));
                            dataUtilInterface.onError(response.getString("message"));
                            return;
                        }
                        dataUtilInterface.onResponse(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onVolleyError(VolleyError error) {
                    dataUtilInterface.onError(error.toString());
                }
            });
        } catch (Exception e) {
            dataUtilInterface.onError(e.toString());
        }
    }

    public static void loadUserData(final Context ctx, int communityMemberID, final DataUtilInterface listener) {

        dataUtilInterface = listener;
        RequestDTO req = new RequestDTO();
        req.setCommunityMemberID(communityMemberID);

        try {
            BaseVolley.getRemoteData(Constant.LOAD_USER_DATA, req, ctx, new BaseVolley.BohaVolleyListener() {
                @Override
                public void onResponseReceived(JSONObject response) {
                    try {
                        if (response.getInt("success") <= 0) {
                            Util.showErrorToast(ctx, response.getString("message"));
                            dataUtilInterface.onError(response.getString("message"));
                            return;
                        }
                        dataUtilInterface.onResponse(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onVolleyError(VolleyError error) {
                    dataUtilInterface.onError(error.toString());
                }
            });
        } catch (Exception e) {
            dataUtilInterface.onError(e.toString());
        }
    }
    public static void loadClerkData(final Context ctx, int municipalityID,int clerkID, final DataUtilInterface listener) {

        dataUtilInterface = listener;
        RequestDTO req = new RequestDTO();
        req.setMunicipalityID(municipalityID);
        req.setClerkID(clerkID);

        try {
            BaseVolley.getRemoteData(Constant.LOAD_CLERK_DATA, req, ctx, new BaseVolley.BohaVolleyListener() {
                @Override
                public void onResponseReceived(JSONObject response) {
                    try {
                        if (response.getInt("success") <= 0) {
                            Util.showErrorToast(ctx, response.getString("message"));
                            dataUtilInterface.onError(response.getString("message"));
                            return;
                        }
                        dataUtilInterface.onResponse(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onVolleyError(VolleyError error) {
                    dataUtilInterface.onError(error.toString());
                }
            });
        } catch (Exception e) {
            dataUtilInterface.onError(e.toString());
        }
    }
    public static void loadRegisterData(final Context ctx, int municipalityID, final DataUtilInterface listener) {

        dataUtilInterface = listener;
        RequestDTO req = new RequestDTO();
        req.setMunicipalityID(municipalityID);

        try {
            BaseVolley.getRemoteData(Constant.LOAD_REGISTER_DATA, req, ctx, new BaseVolley.BohaVolleyListener() {
                @Override
                public void onResponseReceived(JSONObject response) {
                    try {
                        if (response.getInt("success") <= 0) {
                            Util.showErrorToast(ctx, response.getString("message"));
                            dataUtilInterface.onError(response.getString("message"));
                            return;
                        }
                        Log.i(LOG+"or not null", new Gson().toJson(response));
                        dataUtilInterface.onResponse(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onVolleyError(VolleyError error) {
                    dataUtilInterface.onError(error.toString());
                }
            });
        } catch (Exception e) {
            dataUtilInterface.onError(e.toString());
        }
    }

    public static void registerMember(final Context ctx, CommunitymemberDTO patient, final DataUtilInterface listener) {

        dataUtilInterface = listener;
        RequestDTO req = new RequestDTO();
        req.setCommunityMember(patient);
        Log.d(LOG, new Gson().toJson(req));
        try {
            BaseVolley.getRemoteData(Constant.REGISTER_MEMBER, req, ctx, new BaseVolley.BohaVolleyListener() {
                @Override
                public void onResponseReceived(JSONObject response) {
                    try {
                        if (response.getInt("success") <= 0) {
                            Util.showErrorToast(ctx, response.getString("message"));
                            dataUtilInterface.onError(response.getString("message"));
                            return;
                        }
                        dataUtilInterface.onResponse(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onVolleyError(VolleyError error) {
                    dataUtilInterface.onError(error.toString());
                }
            });
        } catch (Exception e) {
            dataUtilInterface.onError(e.toString());
        }
    }

    public static void reportIssue(final Context ctx, ReportedissueDTO reportedIssue, final DataUtilInterface listener) {

        dataUtilInterface = listener;
        RequestDTO req = new RequestDTO();
        req.setReportedissue(reportedIssue);

        try {
            BaseVolley.getRemoteData(Constant.REPORT_ISSUE, req, ctx, new BaseVolley.BohaVolleyListener() {
                @Override
                public void onResponseReceived(JSONObject response) {
                    try {
                        if (response.getInt("success") <= 0) {
                            Util.showErrorToast(ctx, response.getString("message"));
                            dataUtilInterface.onError(response.getString("message"));
                            return;
                        }
                        dataUtilInterface.onResponse(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onVolleyError(VolleyError error) {
                    dataUtilInterface.onError(error.toString());
                }
            });
        } catch (Exception e) {
            dataUtilInterface.onError(e.toString());
        }
    }
    public static void addIssueImages(final Context ctx, IssueimageDTO issueimage, final DataUtilInterface listener) {

        dataUtilInterface = listener;
        RequestDTO req = new RequestDTO();
        req.setIssueimage(issueimage);

        try {
            BaseVolley.getRemoteData(Constant.ADD_IMAGE_REPORTED, req, ctx, new BaseVolley.BohaVolleyListener() {
                @Override
                public void onResponseReceived(JSONObject response) {
                    try {
                        if (response.getInt("success") <= 0) {
                            Util.showErrorToast(ctx, response.getString("message"));
                            dataUtilInterface.onError(response.getString("message"));
                            return;
                        }
                        dataUtilInterface.onResponse(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onVolleyError(VolleyError error) {
                    dataUtilInterface.onError(error.toString());
                }
            });
        } catch (Exception e) {
            dataUtilInterface.onError(e.toString());
        }
    }
    public static void updateStatus(final Context ctx, StatusreportedissueDTO issueimage, final DataUtilInterface listener) {

        dataUtilInterface = listener;
        RequestDTO req = new RequestDTO();
        req.setStatusreportedissue(issueimage);

        try {
            BaseVolley.getRemoteData(Constant.UPDATE_STATUS, req, ctx, new BaseVolley.BohaVolleyListener() {
                @Override
                public void onResponseReceived(JSONObject response) {
                    try {
                        if (response.getInt("success") <= 0) {
                            Util.showErrorToast(ctx, response.getString("message"));
                            dataUtilInterface.onError(response.getString("message"));
                            return;
                        }
                        dataUtilInterface.onResponse(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onVolleyError(VolleyError error) {
                    dataUtilInterface.onError(error.toString());
                }
            });
        } catch (Exception e) {
            dataUtilInterface.onError(e.toString());
        }
    }
    public static CommunitymemberDTO communityMember(JSONArray ar) throws JSONException {
        CommunitymemberDTO dto = new CommunitymemberDTO();
        JSONObject o = ar.getJSONObject(0);
        dto.setCommunityMemberID(o.getInt("communityMemberID"));
        dto.setName(o.getString("name"));
        dto.setCell(o.getString("cell"));
        dto.setEmail(o.getString("email"));
        dto.setMunicipalityID(o.getInt("municipalityID"));

        return dto;
    }
    public static ClerkDTO clerk(JSONArray ar) throws JSONException {
        ClerkDTO dto = new ClerkDTO();
        JSONObject o = ar.getJSONObject(0);
        dto.setClerkID(o.getInt("clerkID"));
        dto.setName(o.getString("name"));
        dto.setSurname(o.getString("surname"));
        dto.setEmail(o.getString("email"));
        dto.setMunicipalityID(o.getInt("municipalityID"));

        return dto;
    }
  /* public static List<ClinicDTO> clinicList(JSONArray ar) throws JSONException {
        List<ClinicDTO> list = new ArrayList<>();
        for (int i = 0; i < ar.length(); i++) {
            JSONObject o = ar.getJSONObject(i);
            ClinicDTO dto = new ClinicDTO();
            dto.setClinicID(o.getInt("clinicID"));
            dto.setPhoneNumber(o.getString("phoneNumber"));
            dto.setEmail(o.getString("email"));
            dto.setLatitude(o.getDouble("latitude"));
            dto.setLongitude(o.getDouble("longitude"));
            dto.setName(o.getString("name"));
            dto.setTownName(o.getString("townName"));
            list.add(dto);
        }
        return list;
    }*/

    public interface JsonifyListener {
        public void onResponseJSon(ResponseDTO resp);

        public void onError(String error);
    }

    static JsonifyListener mListener;
    static JSONObject mObject;
    static Context mCtx;

    public static void getUserData(Context ctx, JSONObject object, JsonifyListener listener) {
        mListener = listener;
        mObject = object;
        mCtx = ctx;
        new ObjectifyUserData().execute(mObject);

    }

    /*AsyncTask method to process the JSon response from server to response pojo*/
    static class ObjectifyUserData extends AsyncTask<JSONObject, Void, ResponseDTO> {

        private List<ReportedissueDTO> reportedIssueList(JSONArray ar) throws JSONException {
            List<ReportedissueDTO> list = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                ReportedissueDTO dto = new ReportedissueDTO();
                JSONObject ob = ar.getJSONObject(i);
                dto.setReportedIssueID(ob.getInt("reportedIssueID"));
                dto.setDateReported(ob.getString("dateReported"));
                dto.setReviews(ob.getString("reviews"));
                dto.setLatitude(ob.getDouble("latitude"));
                dto.setLongitude(ob.getDouble("longitude"));
                dto.setMunicipalityID(ob.getInt("municipalityID"));
                dto.setIssuesID(ob.getInt("issuesID"));
                dto.setDateCreated(ob.getString("dateCreated"));
                dto.setRefNumber(ob.getString("refNumber"));
                dto.setIssueName(ob.getString("issueName"));
                dto.setIcon(ob.getString("icon"));
                dto.setStatusreportedissueList(statusReportedIssueList(ob.getJSONArray("statusReportedIssue")));
                dto.setIssueimageList(issueImageList(ob.getJSONArray("issueImage")));
                list.add(dto);
            }

            return list;
        }

        private List<IssueimageDTO> issueImageList(JSONArray ar) throws JSONException {
            List<IssueimageDTO> list = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject o = ar.getJSONObject(i);
                IssueimageDTO d = new IssueimageDTO();
                d.setIssueImageID(o.getInt("issueImageID"));
                d.setDateTaken(o.getString("dateTaken"));
                d.setImageUrl(o.getString("imageUrl"));
                list.add(d);
            }
            return list;
        }

        private List<StatusreportedissueDTO> statusReportedIssueList(JSONArray ar) throws JSONException {
            List<StatusreportedissueDTO> list = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject ob = ar.getJSONObject(i);
                StatusreportedissueDTO dto = new StatusreportedissueDTO();
                dto.setReportedIssueID(ob.getInt("statusReportedIssueID"));
                dto.setStatusReportedDate(ob.getString("statusReportedDate"));
                dto.setStatusName(ob.getString("statusName"));
                dto.setFlagDone(ob.getInt("flagDone"));

                list.add(dto);
            }
            return list;
        }


        private List<IssuesDTO> issuesList(JSONArray ar) throws JSONException {
            List<IssuesDTO> list = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject ob = ar.getJSONObject(i);
                IssuesDTO dto = new IssuesDTO();
                dto.setIssuesID(ob.getInt("issuesID"));
                dto.setName(ob.getString("name"));
                dto.setIcon(ob.getString("icon"));

                list.add(dto);
            }
            return list;
        }
        private List<StatusDTO> statusList(JSONArray ar) throws JSONException {
            List<StatusDTO> list = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject ob = ar.getJSONObject(i);
                StatusDTO dto = new StatusDTO();
                dto.setStatusID(ob.getInt("statusID"));
                dto.setStatusName(ob.getString("statusName"));

                list.add(dto);
            }
            return list;
        }
        @Override
        protected ResponseDTO doInBackground(JSONObject... params) {
            JSONObject dto = params[0];
            ResponseDTO response = new ResponseDTO();
            try {

                response.setReportedIssueList(reportedIssueList(dto.getJSONArray("reportedIssue")));
                response.setIssuesList(issuesList(dto.getJSONArray("issues")));
//                response.setStatusList(statusList(dto.getJSONArray("status")));
                response.setSuccess(dto.getInt("success"));
                response.setMessage(dto.getString("message"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(ResponseDTO response) {
            if (response.getSuccess() == 0) {
                mListener.onError(response.getMessage());
                return;
            }
            mListener.onResponseJSon(response);
            Log.d(LOG, response.toString());
        }
    }

    public static void getUserRegisterData(Context ctx, JSONObject object, JsonifyListener listener) {
        mListener = listener;
        mObject = object;
        mCtx = ctx;
        new ObjectifyUserRegisterData().execute(mObject);

    }

    /*AsyncTask method to process the JSon response from server to response pojo*/
    static class ObjectifyUserRegisterData extends AsyncTask<JSONObject, Void, ResponseDTO> {

        private List<MunicipalityDTO> municipalityList(JSONArray ar) throws JSONException {
            List<MunicipalityDTO> list = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                MunicipalityDTO dto = new MunicipalityDTO();
                JSONObject ob = ar.getJSONObject(i);
                dto.setMunicipalityID(ob.getInt("municipalityID"));
                dto.setMunicipalityName(ob.getString("municipalityName"));
                dto.setTell(ob.getString("tell"));
                dto.setLatitude(ob.getDouble("latitude"));
                dto.setLongitude(ob.getDouble("longitude"));
                dto.setAddress(ob.getString("address"));
                dto.setEmail(ob.getString("email"));
                dto.setTownList(townList(ob.getJSONArray("town")));
                dto.setWardsList(wardList(ob.getJSONArray("wards")));
                list.add(dto);
            }

            return list;
        }

        private List<TownDTO> townList(JSONArray ar) throws JSONException {
            List<TownDTO> list = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject o = ar.getJSONObject(i);
                TownDTO d = new TownDTO();
                d.setTownID(o.getInt("townID"));
                d.setProvinceID(o.getInt("provinceID"));
                d.setTownName(o.getString("townName"));
                d.setLongitude(o.getDouble("longitude"));
                d.setLatitude(o.getDouble("latitude"));
                list.add(d);
            }
            return list;
        }

        private List<WardsDTO> wardList(JSONArray ar) throws JSONException {
            List<WardsDTO> list = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject ob = ar.getJSONObject(i);
                WardsDTO dto = new WardsDTO();
                dto.setWardsID(ob.getInt("wardsID"));
                dto.setWardName(ob.getString("wardName"));
                list.add(dto);
            }
            return list;
        }


        @Override
        protected ResponseDTO doInBackground(JSONObject... params) {
            JSONObject dto = params[0];
            ResponseDTO response = new ResponseDTO();
            try {

                response.setMunicipalityList(municipalityList(dto.getJSONArray("municipality")));
                response.setSuccess(dto.getInt("success"));
                response.setMessage(dto.getString("message"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(ResponseDTO response) {
            if (response.getSuccess() == 0) {
                mListener.onError(response.getMessage());
                return;
            }
            mListener.onResponseJSon(response);
            Log.d(LOG, response.toString());
        }
    }


    /*private void checkVirgin() {
        //SharedUtil.clearTeam(ctx);
        TeamMemberDTO dto = SharedUtil.getTeamMember(ctx);
        if (dto != null) {
            Log.i(LOG, "++++++++ Not a virgin anymore ...checking GCM registration....");
            String id = SharedUtil.getRegistrationId(getApplicationContext());
            if (id == null) {
                registerGCMDevice();
            }

            Intent intent = new Intent(ctx, MainPagerActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        registerGCMDevice();
    }*/
    private static String LOG = DataUtil.class.getSimpleName();
}
