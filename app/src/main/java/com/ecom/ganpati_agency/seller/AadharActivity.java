package com.ecom.ganpati_agency.seller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.ecom.ganpati_agency.Api.ApiClient;
import com.ecom.ganpati_agency.Api.ServiceApi;
import com.ecom.ganpati_agency.Model.response.SaveAadhaarDetails;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.MyPreferences;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AadharActivity extends AppCompatActivity {
    Bitmap bitmapImage;
    File file;
    Uri returnUri, returnUri1;

    RelativeLayout parentLayout;
    LinearLayout imgLayoutFront, imgLayoutBack;
    ImageView imageFront, imageBack;
    MaterialButton btnSubmit;
    ProgressBar aadhaarProgress;
    boolean isAllFieldsChecked = false;
    EditText edtAadhaar;
    String regex = "^[2-9]{1}[0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$";
    Pattern pattern;
    Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        imgLayoutFront = findViewById(R.id.imgLayoutFront);
        imgLayoutBack = findViewById(R.id.imgLayoutBack);
        imageFront = findViewById(R.id.imageFront);
        imageBack = findViewById(R.id.imageBack);
        aadhaarProgress = findViewById(R.id.aadhaarProgress);
        edtAadhaar = findViewById(R.id.edtAadhaar);
        parentLayout = findViewById(R.id.parentLayout);
        btnSubmit = findViewById(R.id.btnSubmit);

        aadhaarProgress.setVisibility(View.GONE);

        imgLayoutFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser(100);
            }
        });

        imgLayoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser(101);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = checkAllFields();
                if (isAllFieldsChecked) {
                    Log.e("FRONT IMAGE", returnUri.toString());
                    Log.e("BACK IMAGE", returnUri1.toString());
                    Log.e("AADHAAR NUMBER", edtAadhaar.getText().toString());
                    Log.e("USER TOKEN", MyPreferences.getInstance(getApplicationContext()).getUserToken());
                    saveAadhaarDetails(MyPreferences.getInstance(getApplicationContext()).getUserToken()
                            ,edtAadhaar.getText().toString()
                            ,returnUri
                            ,returnUri1);
                }
            }
        });
    }
    public boolean checkAllFields() {

        if (returnUri == null) {
            Snackbar.make(parentLayout, "Please Select Aadhaar Card Front Image...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (returnUri1 == null) {
            Snackbar.make(parentLayout, "Please Select Aadhaar Card Back Image...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtAadhaar.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Your Aadhaar Number...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        } else if (edtAadhaar.length() > 0) {
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(edtAadhaar.getText().toString().trim());
            if (!matcher.matches()) {
                Snackbar.make(parentLayout, "Please Enter Valid Aadhaar Number...!!", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(Color.parseColor("#EA2525"))
                        .setTextColor(Color.WHITE)
                        .show();
                return false;
            }
        }

        return true;

    }

    void imageChooser(int code) {
        ImagePicker.Companion.with(this)
                /*.saveDir(new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), ""+getResources().getString(R.string.app_name)))*/
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(300)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(400, 550)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start(code);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                returnUri = data.getData();
                if (returnUri != null) {
                    try {
                        imgLayoutFront.setVisibility(View.GONE);
                        imageFront.setVisibility(View.VISIBLE);
                        file = new File(getRealPathFromUri(returnUri));
                        bitmapImage = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), returnUri);
                        imageFront.setImageBitmap(bitmapImage);
                        Log.e("FILE PATH: ", file.toString());
                        Log.e("BITMAP PATH: ", bitmapImage.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            if (resultCode == Activity.RESULT_OK) {
                returnUri1 = data.getData();
                if (returnUri1 != null) {
                    try {
                        imgLayoutBack.setVisibility(View.GONE);
                        imageBack.setVisibility(View.VISIBLE);
                        file = new File(getRealPathFromUri(returnUri1));
                        bitmapImage = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), returnUri1);
                        imageBack.setImageBitmap(bitmapImage);
                        Log.e("FILE PATH: ", file.toString());
                        Log.e("BITMAP PATH: ", bitmapImage.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public String getRealPathFromUri(final Uri uri) { // function for file path from uri,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(AadharActivity.this, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(AadharActivity.this, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(AadharActivity.this, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(AadharActivity.this, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Drive.
     */
    private static boolean isGoogleDriveUri(Uri uri) {
        return "com.google.android.apps.docs.storage".equals(uri.getAuthority()) ||
                "com.google.android.apps.docs.storage.legacy".equals(uri.getAuthority());
    }

    private static boolean isWhatsAppUri(Uri uri) {
        return "com.whatsapp.provider.media".equals(uri.getAuthority());
    }


    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                String value = cursor.getString(index);
                if (value.startsWith("content://") || !value.startsWith("/") && !value.startsWith("file://")) {
                    return null;
                }

                return value;
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    //    Update User Profile
    private void saveAadhaarDetails(String token, String aadhaar_card, Uri fileUri, Uri fileUri1) {
        aadhaarProgress.setVisibility(View.VISIBLE);
        MultipartBody.Part propertyStartRideImage = null;
        MultipartBody.Part propertyStartRideImage1 = null;
        if (fileUri != null) {
            File filePARCEL = new File(getRealPathFromUri(fileUri));
            if (filePARCEL != null) {
                RequestBody propertyImage = RequestBody.create(MediaType.parse("image/*"), filePARCEL.getAbsoluteFile());
                propertyStartRideImage = MultipartBody.Part.createFormData("front_aimg",
                        filePARCEL.getName(), propertyImage);
            } else {
                RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
                propertyStartRideImage = MultipartBody.Part.createFormData("front_aimg", "", attachmentEmpty);
            }
        }
        if (fileUri1 != null) {
            File filePARCEL = new File(getRealPathFromUri(fileUri1));
            if (filePARCEL != null) {
                RequestBody propertyImage = RequestBody.create(MediaType.parse("image/*"), filePARCEL.getAbsoluteFile());
                propertyStartRideImage1 = MultipartBody.Part.createFormData("back_aimg",
                        filePARCEL.getName(), propertyImage);
            } else {
                RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
                propertyStartRideImage1 = MultipartBody.Part.createFormData("back_aimg", "", attachmentEmpty);
            }
        }

        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<SaveAadhaarDetails> call = api.saveAadhaarDetails(RequestBody.create(MediaType.parse("multipart/form-data"), token),
                RequestBody.create(MediaType.parse("multipart/form-data"), aadhaar_card),
                propertyStartRideImage,
                propertyStartRideImage1);

        call.enqueue(new Callback<SaveAadhaarDetails>() {
            @Override
            public void onResponse(Call<SaveAadhaarDetails> call, Response<SaveAadhaarDetails> response) {
                aadhaarProgress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#12d06b"))
                                .setTextColor(Color.WHITE)
                                .show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                AadharActivity.super.onBackPressed();
                            }
                        },1000);
                    } else {
                        Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                                .setTextColor(Color.WHITE)
                                .show();
                    }
                } else {
                    Log.d("BODY", "Body is null");
                }
            }

            @Override
            public void onFailure(Call<SaveAadhaarDetails> call, Throwable t) {
                aadhaarProgress.setVisibility(View.GONE);
                Log.d("FAILURE", t.getLocalizedMessage());
            }
        });
    }


}