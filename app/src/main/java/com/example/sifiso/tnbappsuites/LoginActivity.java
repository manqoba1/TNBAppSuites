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
import android.widget.TextView;
import android.widget.Toast;

import com.example.sifiso.tnblibrary.models.CommunitymemberDTO;
import com.example.sifiso.tnblibrary.toolbox.WebCheck;
import com.example.sifiso.tnblibrary.toolbox.WebCheckResult;
import com.example.sifiso.tnblibrary.util.DataUtil;
import com.example.sifiso.tnblibrary.util.EmailValidator;
import com.example.sifiso.tnblibrary.util.SharedUtil;
import com.example.sifiso.tnblibrary.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends ActionBarActivity {
    private AutoCompleteTextView AL_email;
    private EditText AL_password;
    private Button AL_btnSignIn, AL_btnForgot;
    private TextView AL_register;
    private Context ctx;
    String email;
    private CommunitymemberDTO communityMember;
    EmailValidator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ctx = getApplicationContext();
        validator = new EmailValidator();

        try {
            Util.setCustomActionBar(ctx, getSupportActionBar(), "Login Screen", ctx.getResources().getDrawable(R.mipmap.ic_launcher));
        } catch (Exception e) {
            Log.d(LOG, "{0}", e);
        }
        setField();
        getEmail();
    }

    private void setField() {
        AL_email = (AutoCompleteTextView) findViewById(R.id.AL_email);
        AL_password = (EditText) findViewById(R.id.AL_password);
        AL_btnSignIn = (Button) findViewById(R.id.AL_btnSignIn);
        AL_btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AL_email.getText().toString().isEmpty()) {
                    Util.showToast(ctx, "Email can't be empty");
                    return;
                }
                if (AL_password.getText().toString().isEmpty()) {
                    Util.showToast(ctx, "Password can't be empty");
                    return;
                }
                if (validator.validate(AL_email.getText().toString())) {
                    Util.showToast(ctx, "Invalid email format");
                    return;
                }
                WebCheckResult w = WebCheck.checkNetworkAvailability(ctx);
                if (w.isWifiConnected() || w.isMobileConnected()) {
                    login();
                } else {
                    showSettingDialog();
                }
            }
        });
        AL_btnForgot = (Button) findViewById(R.id.AL_btnForgot);
        AL_btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
        AL_register = (TextView) findViewById(R.id.AL_register);
        AL_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);

            }
        });
        checkVirgin();
        if (SharedUtil.getEmail(ctx) != null) {
            AL_email.setText(SharedUtil.getEmail(ctx));
        }
    }

    public void showSettingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

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

    private void checkVirgin() {
        //SharedUtil.clearTeam(ctx);
        CommunitymemberDTO dto = SharedUtil.getCommunityMember(ctx);
        if (dto != null) {
            Log.i(LOG, "++++++++ Not a virgin anymore ...checking GCM registration....");
            String id = SharedUtil.getRegistrationId(getApplicationContext());
            if (id == null) {
                // registerGCMDevice();
            }

            Intent intent = new Intent(LoginActivity.this, MainPagerActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        // registerGCMDevice();
    }

    private void login() {

        DataUtil.login(ctx, AL_email.getText().toString(), AL_password.getText().toString(), new DataUtil.DataUtilInterface() {
            @Override
            public void onResponse(JSONObject r) {
                try {
                    if (r.getInt("success") == 0) {
                        Util.showErrorToast(ctx, r.getString("message"));
                        return;
                    }
                    communityMember = DataUtil.communityMember(r.getJSONArray("communityMember"));
                    SharedUtil.saveCommunityMember(ctx, communityMember);
                    Intent i = new Intent(LoginActivity.this, MainPagerActivity.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void getEmail() {
        AccountManager am = AccountManager.get(getApplicationContext());
        Account[] accts = am.getAccounts();
        if (accts.length == 0) {
            Toast.makeText(ctx, "No Accounts found. Please create one and try again", Toast.LENGTH_LONG).show();
            //finish();
            return;
        }

        emailAccountList = new ArrayList<String>();
        if (accts != null) {
            for (int i = 0; i < accts.length; i++) {
                emailAccountList.add(accts[i].name);
            }
            ArrayAdapter adapter = new ArrayAdapter<String>(ctx, R.layout.xsimple_spinner_item, emailAccountList);

            AL_email.setAdapter(adapter);
            AL_email.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    email = emailAccountList.get(position);
                    AL_email.setText(email);
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

    static final String LOG = LoginActivity.class.getSimpleName();

}
