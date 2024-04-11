package com.ecom.ganpati_agency.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.ecom.ganpati_agency.Api.ApiClient;
import com.ecom.ganpati_agency.Api.ServiceApi;
import com.ecom.ganpati_agency.Model.request.UpdateProfileRequest;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.Constant;
import com.ecom.ganpati_agency.utils.InternetValidation;
import com.ecom.ganpati_agency.utils.MyPreferences;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    CardView openCamera, cardBack;
    CircleImageView imgProfile;
    Bitmap bitmapImage;
    File file;
    Uri returnUri;
    ProgressBar progress;
    RelativeLayout parentLayout;
    EditText edtName, edtEmail, edtNumber, edtAddress;
    boolean isAllFieldsChecked = false;
    MaterialButton btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        openCamera = findViewById(R.id.openCamera);
        imgProfile = findViewById(R.id.imgProfile);
        progress = findViewById(R.id.progress);
        parentLayout = findViewById(R.id.parentLayout);
        cardBack = findViewById(R.id.cardBack);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtNumber = findViewById(R.id.edtNumber);
        edtAddress = findViewById(R.id.edtAddress);
        btnSave = findViewById(R.id.btnSave);
        progress.setVisibility(View.GONE);

        MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
        edtName.setText(preferences.getUserName());
        edtEmail.setText(preferences.getUserEmail());
        edtNumber.setText(preferences.getUserNumber());
        edtAddress.setText(preferences.getAddress());
        if (!preferences.getUserImage().equals("")) {
            Glide.with(getApplicationContext())
                    .load(Constant.IMAGE_URL + preferences.getUserImage())
                    .into(imgProfile);
        } else {
            Glide.with(getApplicationContext())
                    .load(R.drawable.noprofile)
                    .into(imgProfile);
        }

        cardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProfileActivity.super.onBackPressed();
            }
        });

        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetValidation.validation(EditProfileActivity.this)) {
                    imageChooser();
                } else {
                    InternetValidation.getNoConnectionDialog(EditProfileActivity.this);
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = checkAllFields();
                if (isAllFieldsChecked) {
                    Log.e("TOKEN", MyPreferences.getInstance(getApplicationContext()).getUserToken());
                    Log.e("NAME", edtName.getText().toString().trim());
                    Log.e("ADDRESS", edtAddress.getText().toString().trim());
                    Log.e("EMAIL", edtEmail.getText().toString().trim());
                    if (file != null) {
                        Log.e("FILE", file.toString());
                        Log.e("URI", returnUri.toString());
                        Log.e("BITMAP", bitmapImage.toString());
                        if (InternetValidation.validation(EditProfileActivity.this)) {
                            updateProfile(MyPreferences.getInstance(getApplicationContext()).getUserToken()
                                    , edtName.getText().toString().trim()
                                    , edtAddress.getText().toString().trim()
                                    , edtEmail.getText().toString().trim()
                                    , returnUri);
                        } else {
                            InternetValidation.getNoConnectionDialog(EditProfileActivity.this);
                        }
                    } else {
                        Log.e("FILE", "FILE NULL");
                        if (InternetValidation.validation(EditProfileActivity.this)) {
                            updateProfile(MyPreferences.getInstance(getApplicationContext()).getUserToken()
                                    , edtName.getText().toString().trim()
                                    , edtAddress.getText().toString().trim()
                                    , edtEmail.getText().toString().trim()
                                    , null);
                        } else {
                            InternetValidation.getNoConnectionDialog(EditProfileActivity.this);
                        }
                    }
                }
            }
        });
    }

    public boolean checkAllFields() {
        if (edtName.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Name", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtEmail.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Email Address", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        } else if (!edtEmail.getText().toString().contains("@")) {
            Snackbar.make(parentLayout, "Please Enter Valid Email Address", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtNumber.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Number", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        } else if (edtNumber.length() < 10) {
            Snackbar.make(parentLayout, "Please Enter Valid Number", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtAddress.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Address", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        return true;
    }

    private void imageChooser() {
            ImagePicker.Companion.with(this)
                    /*.saveDir(new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), ""+getResources().getString(R.string.app_name)))*/
                    .crop()                    //Crop image(Optional), Check Customization for more option
                    .compress(300)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(400, 550)    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        }

        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK) {
                returnUri = data.getData();
                if (returnUri != null) {
                    try {
                        file = new File(getRealPathFromUri(returnUri));
                        bitmapImage = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), returnUri);
                        imgProfile.setImageBitmap(bitmapImage);
                        Log.e("FILE PATH: ", file.toString());
                        Log.e("BITMAP PATH: ", bitmapImage.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public String getRealPathFromUri(final Uri uri) { // function for file path from uri,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(EditProfileActivity.this, uri)) {
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

                    return getDataColumn(EditProfileActivity.this, contentUri, null, null);
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

                    return getDataColumn(EditProfileActivity.this, contentUri, selection, selectionArgs);
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {
                // Return the remote address
                if (isGooglePhotosUri(uri))
                    return uri.getLastPathSegment();

                return getDataColumn(EditProfileActivity.this, uri, null, null);
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
    private void updateProfile(String token, String name, String address, String email, Uri fileUri) {
        progress.setVisibility(View.VISIBLE);
        MultipartBody.Part propertyStartRideImage = null;
        if (fileUri != null) {
            //creating a file
            File filePARCEL = new File(getRealPathFromUri(fileUri));

            if (filePARCEL != null) {
                RequestBody propertyImage = RequestBody.create(MediaType.parse("image/*"), filePARCEL.getAbsoluteFile());
                propertyStartRideImage = MultipartBody.Part.createFormData("image",
                        filePARCEL.getName(), propertyImage);
            } else {
                RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
                propertyStartRideImage = MultipartBody.Part.createFormData("image", "", attachmentEmpty);
            }
        }

        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<UpdateProfileRequest> call = api.
                updateProfile(RequestBody.create(MediaType.parse("multipart/form-data"), token),
                RequestBody.create(MediaType.parse("multipart/form-data"), name),
                RequestBody.create(MediaType.parse("multipart/form-data"), address),
                RequestBody.create(MediaType.parse("multipart/form-data"), email),
                propertyStartRideImage
        );

        call.enqueue(new Callback<UpdateProfileRequest>() {
            @Override
            public void onResponse(Call<UpdateProfileRequest> call, Response<UpdateProfileRequest> response) {
                progress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#12d06b"))
                                .setTextColor(Color.WHITE)
                                .show();

                        MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
                        preferences.saveUpdateDetails(response.body().getProfUpdateres().getName()
                                , response.body().getProfUpdateres().getAddress()
                                , response.body().getProfUpdateres().getEmail()
                                , response.body().getProfUpdateres().getImagePath());
                        Log.e("NAME", preferences.getUserName());
                        Log.e("ADDRESS", preferences.getAddress());
                        Log.e("EMAIL", preferences.getUserEmail());
                        Log.e("IMAGE URL", preferences.getUserImage());
                        startActivity(new Intent(EditProfileActivity.this, DashboardActivity.class));
                        finishAffinity();
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
            public void onFailure(Call<UpdateProfileRequest> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Log.d("FAILURE", t.getLocalizedMessage());
            }
        });
    }
}