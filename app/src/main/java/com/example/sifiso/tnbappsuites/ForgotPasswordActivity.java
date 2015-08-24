package com.example.sifiso.tnbappsuites;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.sifiso.tnblibrary.models.RequestDTO;
import com.example.sifiso.tnblibrary.util.DataUtil;
import com.example.sifiso.tnblibrary.util.EmailValidator;
import com.example.sifiso.tnblibrary.util.Statics;
import com.example.sifiso.tnblibrary.util.Util;

import org.json.JSONException;
import org.json.JSONObject;


public class ForgotPasswordActivity extends ActionBarActivity {
    private TextView message;
    private AutoCompleteTextView AL_email;
    private Button AL_btnSignIn;
    private Context ctx;
    EmailValidator validator;
    String LOG = ForgotPasswordActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ctx = getApplicationContext();
        validator = new EmailValidator();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            Util.setCustomActionBar(ctx, getSupportActionBar(), "Forgot Password", ctx.getResources().getDrawable(R.mipmap.ic_launcher));
        } catch (Exception e) {
            Log.d(LOG, "{0}", e);
        }
        setFields();
    }

    private void setFields() {
        message = (TextView) findViewById(R.id.message);
        AL_email = (AutoCompleteTextView) findViewById(R.id.AL_email);
        AL_btnSignIn = (Button) findViewById(R.id.AL_btnSignIn);
        AL_btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validator.validate(AL_email.getText().toString())) {
                    Util.showToast(ctx, "Invalid email format");
                    return;
                }
                getForgottenPassword();
            }
        });
    }

    private void getForgottenPassword() {
        DataUtil.forgotPassword(ctx, AL_email.getText().toString(), new DataUtil.DataUtilInterface() {
            @Override
            public void onResponse(JSONObject r) {
                try {
                    if (r.getInt("success") == 0) {
                        Util.showErrorToast(ctx, r.getString("message"));
                        return;
                    }

                    Statics.setRobotoFontBoldCondensed(ctx, message);
                    message.setVisibility(View.VISIBLE);
                    Util.animateSlideRight(message, 200);
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
        getMenuInflater().inflate(R.menu.menu_forgot_password, menu);
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

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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
}
