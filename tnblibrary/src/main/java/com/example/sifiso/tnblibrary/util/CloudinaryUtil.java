package com.example.sifiso.tnblibrary.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.Utils;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by sifiso on 2015-06-11.
 */
public class CloudinaryUtil {
    public interface CloudinaryUtilListner {
        public void onSuccessUpload(Map uploadResult);

        public void onError();

        public void onProgress(Integer upload);
    }

    static Context mCtx;
    static CloudinaryUtilListner mListner;
    static InputStream mInputStream;
    static File mFile;
    static String LOG = CloudinaryUtil.class.getSimpleName();

    private static Cloudinary cloudinaryConnection(Context ctx) {
        return new Cloudinary(Utils.cloudinaryUrlFromContext(ctx));
    }

    public static void uploadImagesToCDN(Context ctx, File fileName, CloudinaryUtilListner listner) {
        mListner = listner;
        mFile = fileName;
        mCtx = ctx;
        new UploadToCDN().execute();
    }

    private static class UploadToCDN extends AsyncTask<Void, Integer, Map> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d(LOG, values.length+" vfdfsdfsf");
            mListner.onProgress(values[0]);
        }

        @Override
        protected Map doInBackground(Void... params) {
            Cloudinary cloudinary = cloudinaryConnection(mCtx);

            Map uploadResult = null;
            if (cloudinary != null) {
                try {
                    Map options = ObjectUtils.asMap(
                            "colors", true,
                            "metadata", true
                    );
                    uploadResult = cloudinary.uploader().upload(mFile, options);
                    return uploadResult;
                } catch (IOException e) {
                    mListner.onError();
                    Log.e(LOG, "{0}", e);
                    //return uploadResult;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Map uploadResult) {
            super.onPostExecute(uploadResult);
            if (uploadResult == null) {
                new UploadToCDN().execute();
                return;
            }
            mListner.onSuccessUpload(uploadResult);
        }
    }
}
