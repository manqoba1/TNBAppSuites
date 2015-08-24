package com.example.sifiso.tnbappsuites;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sifiso.tnblibrary.models.CommunitymemberDTO;
import com.example.sifiso.tnblibrary.models.MunicipalityDTO;
import com.example.sifiso.tnblibrary.models.ResponseDTO;
import com.example.sifiso.tnblibrary.toolbox.WebCheck;
import com.example.sifiso.tnblibrary.toolbox.WebCheckResult;
import com.example.sifiso.tnblibrary.util.DataUtil;
import com.example.sifiso.tnblibrary.util.EmailValidator;
import com.example.sifiso.tnblibrary.util.SharedUtil;
import com.example.sifiso.tnblibrary.util.Statics;
import com.example.sifiso.tnblibrary.util.Util;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RegistrationActivity extends ActionBarActivity {
    private EditText AR_name, AR_cell, AR_password;
    private AutoCompleteTextView AR_email, AR_municipality;
    private Button AR_btnSignUp, AR_btnSignIn;
    private Context ctx;
    EmailValidator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ctx = getApplicationContext();
        validator = new EmailValidator();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            Util.setCustomActionBar(ctx, getSupportActionBar(), "Register User", ctx.getResources().getDrawable(R.mipmap.ic_launcher));
        } catch (Exception e) {
            Log.d(LOG, "{0}", e);
        }
        setField();
        getEmail();
    }

    private void setField() {
        AR_name = (EditText) findViewById(R.id.AR_name);
        AR_cell = (EditText) findViewById(R.id.AR_cell);
        AR_password = (EditText) findViewById(R.id.AR_password);

        AR_email = (AutoCompleteTextView) findViewById(R.id.AR_email);
        AR_municipality = (AutoCompleteTextView) findViewById(R.id.AR_municipality);

        AR_btnSignUp = (Button) findViewById(R.id.AR_btnSignUp);
        AR_btnSignIn = (Button) findViewById(R.id.AR_btnSignIn);
        AR_btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AR_name.getText().toString().isEmpty()) {
                    Util.showToast(ctx, "Name can't be empty");
                    return;
                }
                if (Statics.isLetterAndNumber(AR_name.getText().toString())) {
                    Util.showToast(ctx, "Name should be letters only");
                    return;
                }
                if (AR_cell.getText().toString().isEmpty() && AR_cell.getText().toString().length() != 10) {
                    Util.showToast(ctx, "Cell can't be empty");
                    return;
                }
                if (AR_email.getText().toString().isEmpty()) {
                    Util.showToast(ctx, "Email can't be empty");
                    return;
                }
                if (!Statics.rfc2822.matcher(AR_email.getText().toString()).matches()) {
                    Util.showToast(ctx, "Invalid email format");
                    return;
                }
                if (AR_password.getText().toString().isEmpty()) {
                    Util.showToast(ctx, "Password can't be empty");
                    return;
                }
                if (municipalityID == null) {
                    Util.showToast(ctx, "Municipality not specified");
                    return;
                }

                WebCheckResult w = WebCheck.checkNetworkAvailability(ctx);
                if (w.isWifiConnected() || w.isMobileConnected()) {
                    sign_up();
                } else {
                    showSettingDialog();
                }
            }
        });
        AR_btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    CommunitymemberDTO communityMember;

    private void sign_up() {
        CommunitymemberDTO dto = new CommunitymemberDTO();
        dto.setName(AR_name.getText().toString());
        dto.setCell(AR_cell.getText().toString());
        dto.setEmail(AR_email.getText().toString());
        dto.setPassword(AR_password.getText().toString());
        dto.setMunicipalityID(municipalityID);
        DataUtil.registerMember(ctx, dto, new DataUtil.DataUtilInterface() {
            @Override
            public void onResponse(JSONObject r) {
                try {
                    if (r.getInt("success") <= 0) {
                        Util.showErrorToast(ctx, r.getString("message"));
                        return;
                    }
                    communityMember = DataUtil.communityMember(r.getJSONArray("communityMember"));
                    SharedUtil.saveCommunityMember(ctx, communityMember);
                    Intent i = new Intent(RegistrationActivity.this, MainPagerActivity.class);
                    startActivity(i);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private ResponseDTO response;

    private void getRegistrationData() {
        DataUtil.loadRegisterData(ctx, 1, new DataUtil.DataUtilInterface() {
            @Override
            public void onResponse(JSONObject r) {

                DataUtil.getUserRegisterData(ctx, r, new DataUtil.JsonifyListener() {
                    @Override
                    public void onResponseJSon(ResponseDTO resp) {
                        if (resp.getSuccess() == 0) {
                            Util.showErrorToast(ctx, resp.getMessage());
                            return;
                        }
                        response = resp;
                        Log.i(LOG + "or not null", new Gson().toJson(response));
                        setMunicipalitySpinner();
                    }

                    @Override
                    public void onError(String error) {
                        Util.showErrorToast(ctx, error);
                    }
                });
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private List<String> municipalityList;
    ArrayAdapter<String> municipalityAdapter;
    private Integer municipalityID;

    private void setMunicipalitySpinner() {

        municipalityList = new ArrayList<>();


        for (MunicipalityDTO m : response.getMunicipalityList()) {
            municipalityList.add(m.getMunicipalityName());
        }
        municipalityAdapter = new ArrayAdapter<String>(ctx, R.layout.xsimple_spinner_item, municipalityList);
        AR_municipality.setAdapter(municipalityAdapter);
        AR_municipality.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                indexID = searchRiver(parent.getItemAtPosition(position).toString());
                municipalityID = response.getMunicipalityList().get(indexID).getMunicipalityID();
                Log.e(LOG, "Municipality ID : " + municipalityID);
            }
        });
    }

    boolean isFound;
    Integer indexID;

    private Integer searchRiver(String text) {

        int index = 0;

        for (int i = 0; i < response.getMunicipalityList().size(); i++) {
            MunicipalityDTO searchRiver = response.getMunicipalityList().get(i);
            if (searchRiver.getMunicipalityName().contains(text)) {
                isFound = true;
                break;
            }
            index++;
        }
        if (isFound) {
            //STF_list.setSelection(index);
            Log.e(LOG, text + " Found River " + response.getMunicipalityList().get(index).getMunicipalityName() + " " + response.getMunicipalityList().get(index).getMunicipalityID());
        } else {
            // Util.showToast(ctx, ctx.getString(R.string.river_not_found) + " " + SLT_editSearch.getText().toString());
        }
        //hideKeyboard();
        return index;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
        WebCheckResult w = WebCheck.checkNetworkAvailability(ctx);
        if (w.isWifiConnected() || w.isMobileConnected()) {
            getRegistrationData();
        } else {
            showSettingDialog();
        }
        return true;
    }

    public void showSettingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);

        builder.setTitle("No connectivity");
        builder.setMessage("You network connectivity might be off, if not so please contact you your network provider");
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    List<String> emailAccountList;
    String email;

    public void getEmail() {
        AccountManager am = AccountManager.get(getApplicationContext());
        Account[] accts = am.getAccounts();
        if (accts.length == 0) {
            Toast.makeText(ctx, "No Accounts found. Please create one and try again", Toast.LENGTH_LONG).show();
            // finish();
            return;
        }

        emailAccountList = new ArrayList<String>();
        if (accts != null) {
            for (int i = 0; i < accts.length; i++) {
                emailAccountList.add(accts[i].name);
            }
            ArrayAdapter adapter = new ArrayAdapter<String>(ctx, R.layout.xsimple_spinner_item, emailAccountList);

            AR_email.setAdapter(adapter);
            AR_email.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    email = emailAccountList.get(position);
                    AR_email.setText(email);
                }
            });
            // esEmail.setText(email);

        }

    }

    @Override
    protected void onDestroy() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        //  TimerUtil.killFlashTimer();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onPause();
    }

    static final String LOG = RegistrationActivity.class.getSimpleName();

}
