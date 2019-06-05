package com.bernal.jonatan.whip.Servers;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.bernal.jonatan.whip.Presenters.AdoptionRequestPresenter;
import com.bernal.jonatan.whip.Views.UserLoggedIn;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdoptionRequestServer {

    private UserLoggedIn u1 = UserLoggedIn.getUsuariLogejat("", "", "");
    private String api = u1.getAPI_KEY();
    private RequestQueue requestQueue;


    public void sendInfo(final AdoptionRequestPresenter adoptionRequestPresenter, String URL, String cosText, String adoptionPostID, String photo_url, String usernameFromPost) {
        requestQueue = Volley.newRequestQueue((Context) adoptionRequestPresenter.getView());


        JSONObject adoptionReq = new JSONObject();

        if (cosText.equals("")) {
            adoptionRequestPresenter.notifyEmptyDesc();
        } else {
            //JASON
            try {
                adoptionReq.put("adoptionPostId", adoptionPostID);
                adoptionReq.put("text", cosText);
                adoptionReq.put("photo_url", photo_url);
                adoptionReq.put("userIdFromPost", usernameFromPost);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Guardar los datos del formulario en BACK.
            JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                    JsonRequest.Method.POST,
                    URL,
                    adoptionReq,

                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            adoptionRequestPresenter.notifyCreate();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/json");
                    params.put("Authorization", api); //valor de V ha de ser el de la var global
                    return params;
                }
            };
            requestQueue.add(objectJsonrequest);

        }
    }
}
