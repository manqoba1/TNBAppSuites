package com.example.clerkapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sifiso.tnblibrary.models.ClerkDTO;
import com.example.sifiso.tnblibrary.models.RequestDTO;
import com.example.sifiso.tnblibrary.util.DataUtil;
import com.example.sifiso.tnblibrary.util.SharedUtil;
import com.example.sifiso.tnblibrary.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class SinginActivityFragment extends Fragment {

    Spinner spinnerEmail;
    TextView txtApp;
    AutoCompleteTextView txtEmail;
    EditText ePin;
    Button btnSave;
    Context ctx;
    String email;
    ImageView banner;
    ProgressBar progressBar;
    SinginActivityFragmentListener mCallbacks;

    public interface SinginActivityFragmentListener {
        public void onSignedIn(ClerkDTO clerk);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof SinginActivityFragmentListener)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (SinginActivityFragmentListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    SinginActivityFragmentListener sDummyCallbacks = new SinginActivityFragmentListener() {

        @Override
        public void onSignedIn(ClerkDTO clerk) {

        }
    };

    public SinginActivityFragment() {
    }

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_singin, container, false);
        ctx = getActivity().getApplicationContext();
        setFields();
        getEmail();
        return v;
    }

    private void setFields() {
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        ePin = (EditText) v.findViewById(R.id.SI_pin);
        txtEmail = (AutoCompleteTextView) v.findViewById(R.id.SI_txtEmail);
        txtApp = (TextView) v.findViewById(R.id.SI_app);
        btnSave = (Button) v.findViewById(R.id.btnRed);
        txtApp.setText("Notice Board Clerk App");
        btnSave.setText("Sign In");
        btnSave.setEnabled(true);
        btnSave.setTextColor(getResources().getColor(R.color.white));


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG, "hello/");
                Util.flashOnce(btnSave, 100, new Util.UtilAnimationListener() {
                    @Override
                    public void onAnimationEnded() {
                        sendSignIn();
                    }
                });
            }
        });
    }

    public void getEmail() {
        AccountManager am = AccountManager.get(getActivity().getApplicationContext());
        Account[] accts = am.getAccounts();
        if (accts.length == 0) {
            Util.showErrorToast(ctx, "No email accounts found");
            //getActivity().finish();
            return;
        }
        if (accts != null) {
            for (int i = 0; i < accts.length; i++) {
                tarList.add(accts[i].name);

            }
            setSpinner();
        }

    }

    private void setSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx, R.layout.xsimple_spinner_item, tarList);
        txtEmail.setAdapter(adapter);
        txtEmail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    email = null;
                    return;
                }
                email = tarList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    ArrayList<String> tarList = new ArrayList<String>();
    ClerkDTO clerk;
    static String LOG = SinginActivityFragment.class.getSimpleName();

    private void sendSignIn() {
        if (ePin.getText().toString().isEmpty()) {
            Util.showErrorToast(ctx, "Enter PIN");
            return;
        }
        if (txtEmail.getText().toString().isEmpty()) {
            Util.showErrorToast(ctx, "Enter EMAIL");
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        DataUtil.loginClerk(ctx, (email == null ? txtEmail.getText().toString().trim() : email), ePin.getText().toString(), new DataUtil.DataUtilInterface() {
            @Override
            public void onResponse(JSONObject r) {
                progressBar.setVisibility(View.GONE);
                try {
                    if (r.getInt("success") == 0) {
                        Util.showErrorToast(ctx, r.getString("message"));
                        return;
                    }
                    clerk = DataUtil.clerk(r.getJSONArray("clerk"));
                    mCallbacks.onSignedIn(clerk);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                progressBar.setVisibility(View.GONE);
                Util.showErrorToast(ctx, error);
            }
        });
    }
}
