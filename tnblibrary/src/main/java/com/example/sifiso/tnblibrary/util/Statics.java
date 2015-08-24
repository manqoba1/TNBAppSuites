package com.example.sifiso.tnblibrary.util;


import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import java.util.regex.Pattern;


public class Statics {


    public static final String WEBSOCKET_URL = "ws://10.50.75.30:8080/ms/";
    public static final String URL = "http://10.50.75.30:8080/ms/";
    public static final String CRASH_REPORTS_URL = URL + "crash?";
    public static final String IMAGE_URL = "http://10.50.75.30:8080/";
    public static final String UPLOAD_URL_REQUEST = "uploadUrl?";
    public static final String
            REQUEST_ENDPOINT = "wsrequest",
            MINI_SASS_ENDPOINT = "wsmini";




    public static void setDroidFontBold(Context ctx, TextView txt) {
        Typeface font = Typeface.createFromAsset(ctx.getAssets(),
                "DroidSerif-Bold");
        txt.setTypeface(font);
    }

    public static void setRobotoFontBoldCondensed(Context ctx, TextView txt) {
        Typeface font = Typeface.createFromAsset(ctx.getAssets(),
                "fonts/Roboto-BoldCondensed.ttf");
        txt.setTypeface(font);
    }

    public static void setRobotoFontRegular(Context ctx, TextView txt) {
        Typeface font = Typeface.createFromAsset(ctx.getAssets(),
                "fonts/Roboto-Regular.ttf");
        txt.setTypeface(font);
    }

    public static void setRobotoFontLight(Context ctx, TextView txt) {
        Typeface font = Typeface.createFromAsset(ctx.getAssets(),
                "fonts/Roboto-Light.ttf");
        txt.setTypeface(font);
    }

    public static void setRobotoFontBold(Context ctx, TextView txt) {
        Typeface font = Typeface.createFromAsset(ctx.getAssets(),
                "fonts/Roboto-Bold.ttf");
        txt.setTypeface(font);
    }

    public static void setRobotoItalic(Context ctx, TextView txt) {
        Typeface font = Typeface.createFromAsset(ctx.getAssets(),
                "fonts/Roboto-Italic.ttf");
        txt.setTypeface(font);
    }

    public static void setRobotoRegular(Context ctx, TextView txt) {
        Typeface font = Typeface.createFromAsset(ctx.getAssets(),
                "fonts/Roboto-Regular.ttf");
        txt.setTypeface(font);
    }
    public static final Pattern rfc2822 = Pattern.compile(
            "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$"
    );
    public static boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }
    public static boolean isSpecial(String name) {
        return name.matches("[!#$%&'*+/=?^_`{|}~-]+");
    }

    public static boolean isLetterAndNumber(String l) {
        String n = ".*[0-9].*";
        String a = ".*[A-Z].*";
        String s = ".*[0-9!#$%&'*+/=?^_`{|}~-].*";

        return l.matches(s) ;

    }

}
