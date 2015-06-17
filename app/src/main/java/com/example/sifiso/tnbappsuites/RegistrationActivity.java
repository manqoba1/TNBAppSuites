package com.example.sifiso.tnbappsuites;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
import com.example.sifiso.tnblibrary.util.DataUtil;
import com.example.sifiso.tnblibrary.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RegistrationActivity extends ActionBarActivity {
    private EditText AR_name, AR_cell, AR_password;
    private AutoCompleteTextView AR_email, AR_municipality;
    private Button AR_btnSignUp, AR_btnSignIn;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ctx = getApplicationContext();
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
                if (AR_cell.getText().toString().isEmpty()) {
                    Util.showToast(ctx, "Cell can't be empty");
                    return;
                }
                if (AR_email.getText().toString().isEmpty()) {
                    Util.showToast(ctx, "Email can't be empty");
                    return;
                }
                if (AR_password.getText().toString().isEmpty()) {
                    Util.showToast(ctx, "Password can't be empty");
                    return;
                }
                if (AR_municipality.getText().toString().isEmpty()) {
                    Util.showToast(ctx, "Municipality not selected");
                    return;
                }
                sign_up();
            }
        });
        AR_btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }

    private void sign_up() {
        CommunitymemberDTO dto = new CommunitymemberDTO();
        dto.setName(AR_name.getText().toString());
        dto.setCell(AR_cell.getText().toString());
        dto.setEmail(AR_email.getText().toString());
        dto.setPassword(AR_password.getText().toString());
        dto.setCommunityMemberID(municipalityID);
        DataUtil.registerMember(ctx, dto, new DataUtil.DataUtilInterface() {
            @Override
            public void onResponse(JSONObject r) {

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
                        setMunicipalitySpinner();
                    }

                    @Override
                    public void onError(String error) {

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
        if (municipalityList == null) {
            municipalityList = new ArrayList<>();
        }

        for (MunicipalityDTO m : response.getMunicipalityList()) {
            municipalityList.add(m.getMunicipalityName());
        }
        municipalityAdapter = new ArrayAdapter<String>(ctx, R.layout.xsimple_spinner_item, municipalityList);
        AR_municipality.setAdapter(municipalityAdapter);
        AR_municipality.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                municipalityID = response.getMunicipalityList().get(position).getMunicipalityID();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
        getRegistrationData();
        return true;
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

        return super.onOptionsItemSelected(item);
    }

    List<String> emailAccountList;
    String email;

    public void getEmail() {
        AccountManager am = AccountManager.get(getApplicationContext());
        Account[] accts = am.getAccounts();
        if (accts.length == 0) {
            Toast.makeText(ctx, "No Accounts found. Please create one and try again", Toast.LENGTH_LONG).show();
            finish();
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


    static final String LOG = RegistrationActivity.class.getSimpleName();

}
