package com.ecom.ganpati_agency.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.TextView;

import com.ecom.ganpati_agency.Adapter.BankListAdapter;
import com.ecom.ganpati_agency.Api.ApiClient;
import com.ecom.ganpati_agency.Api.ServiceApi;
import com.ecom.ganpati_agency.Model.request.BankRequest;
import com.ecom.ganpati_agency.Model.response.SaveBankDetails;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.MyPreferences;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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

public class BankActivity extends AppCompatActivity {
    public TextView txtBank;
    ProgressBar progress;
    RelativeLayout parentLayout;
    public String bankId;
    LinearLayout imgLayout;
    ImageView image;
    MaterialButton btnSubmit;
    boolean isAllFieldChecked = false;
    EditText edtName, edtAccNumber, edtIfsc;

    Bitmap bitmapImage;
    File file;
    Uri returnUri;

    String regex = "^[A-Z0-9]+$";
    Pattern pattern;
    Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        txtBank = findViewById(R.id.txtBank);
        progress = findViewById(R.id.progress);
        parentLayout = findViewById(R.id.parentLayout);
        imgLayout = findViewById(R.id.imgLayout);
        image = findViewById(R.id.image);
        btnSubmit = findViewById(R.id.btnSubmit);
        edtName = findViewById(R.id.edtName);
        edtAccNumber = findViewById(R.id.edtAccNumber);
        edtIfsc = findViewById(R.id.edtIfsc);

        progress.setVisibility(View.GONE);
        txtBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBankPopup();
            }
        });

        imgLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldChecked = checkAllFields();
                if (isAllFieldChecked) {
                    Log.e("IMAGE URL", returnUri.toString());
                    Log.e("BANK ID", bankId);
                    Log.e("BANK NAME", txtBank.getText().toString());
                    Log.e("ACCOUNT HOLDER NAME", edtName.getText().toString());
                    Log.e("ACCOUNT NUMBER", edtAccNumber.getText().toString());
                    Log.e("IFSC NUMBER", edtIfsc.getText().toString());
                    Log.e("USER TOKEN", MyPreferences.getInstance(getApplicationContext()).getUserToken());
                    saveBankDetails(MyPreferences.getInstance(getApplicationContext()).getUserToken()
                            , bankId
                            , edtName.getText().toString()
                            , edtAccNumber.getText().toString()
                            , edtIfsc.getText().toString()
                            , returnUri);
                }
            }
        });
    }
    private boolean checkAllFields() {
        if (returnUri == null) {
            Snackbar.make(parentLayout, "Please Select Your Passbook Image...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (bankId == null) {
            Snackbar.make(parentLayout, "Please Select Your Bank...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtName.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Account Holder Name...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtAccNumber.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Account Number...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        } else if (edtAccNumber.length() < 9 || edtAccNumber.length() > 18) {
            Snackbar.make(parentLayout, "Please Enter Valid Account Number...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtIfsc.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter IFSC Code...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtIfsc.length() > 0) {
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(edtIfsc.getText().toString().trim());
            if (!matcher.matches()) {
                Snackbar.make(parentLayout, "Please Enter Valid IFSC Code...!!", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(Color.parseColor("#EA2525"))
                        .setTextColor(Color.WHITE)
                        .show();
                return false;
            }
        }

        return true;
    }

    public BottomSheetDialog bankDialog;
    RecyclerView bankRecycler;
    ProgressBar bankProgress;

    public void selectBankPopup() {
        bankDialog = new BottomSheetDialog(BankActivity.this);
        bankDialog.setContentView(R.layout.custom_select_bank_layout);
        bankDialog.getWindow().findViewById(R.id.contantLayout).setBackgroundColor(Color.TRANSPARENT);
        bankDialog.getWindow().setTransitionBackgroundFadeDuration(10000);

        bankProgress = bankDialog.findViewById(R.id.bankProgress);
        bankRecycler = bankDialog.findViewById(R.id.bankRecycler);

        bankRecycler.setLayoutManager(new LinearLayoutManager(BankActivity.this));
        bankProgress.setVisibility(View.GONE);
        getBankList();

        bankDialog.show();
        bankDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void getBankList() {
        bankProgress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<BankRequest> call = api.getBankList();
        call.enqueue(new Callback<BankRequest>() {
            @Override
            public void onResponse(Call<BankRequest> call, Response<BankRequest> response) {
                bankProgress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        BankListAdapter bankListAdapter = new BankListAdapter(getApplicationContext(), BankActivity.this, response.body().getBank());
                        bankRecycler.setAdapter(bankListAdapter);
                    } else {
                        BankListAdapter bankListAdapter = new BankListAdapter(getApplicationContext(), BankActivity.this, response.body().getBank());
                        bankRecycler.setAdapter(bankListAdapter);
                        Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#EA2525"))
                                .setTextColor(Color.WHITE)
                                .show();
                    }
                } else {
                    Log.e("BODY", "Body is null");
                }
            }

            @Override
            public void onFailure(Call<BankRequest> call, Throwable t) {
                bankProgress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

    void imageChooser() {
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
                    imgLayout.setVisibility(View.GONE);
                    image.setVisibility(View.VISIBLE);
                    file = new File(getRealPathFromUri(returnUri));
                    bitmapImage = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), returnUri);
                    image.setImageBitmap(bitmapImage);
                    Log.e("FILE PATH: ", file.toString());
                    Log.e("BITMAP PATH: ", bitmapImage.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getRealPathFromUri(final Uri uri) { // function for file path from uri,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(BankActivity.this, uri)) {
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

                return getDataColumn(BankActivity.this, contentUri, null, null);
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

                return getDataColumn(BankActivity.this, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(BankActivity.this, uri, null, null);
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
    private void saveBankDetails(String token, String back_id, String acc_name, String acc_no, String ifsc, Uri fileUri) {
        progress.setVisibility(View.VISIBLE);
        MultipartBody.Part propertyStartRideImage = null;
        if (fileUri != null) {
            File filePARCEL = new File(getRealPathFromUri(fileUri));
            if (filePARCEL != null) {
                RequestBody propertyImage = RequestBody.create(MediaType.parse("image/*"), filePARCEL.getAbsoluteFile());
                propertyStartRideImage = MultipartBody.Part.createFormData("passbook_img",
                        filePARCEL.getName(), propertyImage);
            } else {
                RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
                propertyStartRideImage = MultipartBody.Part.createFormData("passbook_img", "", attachmentEmpty);
            }
        }

        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<SaveBankDetails> call = api.saveBankDetails(RequestBody.create(MediaType.parse("multipart/form-data"), token),
                RequestBody.create(MediaType.parse("multipart/form-data"), back_id),
                RequestBody.create(MediaType.parse("multipart/form-data"), acc_name),
                RequestBody.create(MediaType.parse("multipart/form-data"), acc_no),
                RequestBody.create(MediaType.parse("multipart/form-data"), ifsc),
                propertyStartRideImage);

        call.enqueue(new Callback<SaveBankDetails>() {
            @Override
            public void onResponse(Call<SaveBankDetails> call, Response<SaveBankDetails> response) {
                progress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#12d06b"))
                                .setTextColor(Color.WHITE)
                                .show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                BankActivity.super.onBackPressed();
                            }
                        }, 1000);
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
            public void onFailure(Call<SaveBankDetails> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Log.d("FAILURE", t.getLocalizedMessage());
            }
        });
    }

}