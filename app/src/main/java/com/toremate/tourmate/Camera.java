package com.toremate.tourmate;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Camera extends AppCompatActivity implements View.OnClickListener {
    Button btnCamera,btnCreateMoment;
    ImageView btnImageView;
    EditText etMoment;
    TextView tvError;
    Uri uri;
    String uid;
    public Bitmap bitmap;
    public static final int IMAGE_REQUEST=1;
    FirebaseAuth firebaseAuth;
    StorageReference mRef;
    public static final String FB_STORAGE_PATH="image/";
    public static final String FB_DATABASE_PATH="image";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initialization();

    }

    private void initialization() {
        firebaseAuth=FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
        mRef = FirebaseStorage.getInstance().getReference();
        btnImageView= (ImageView) findViewById(R.id.btnImageView);
        btnCamera= (Button) findViewById(R.id.btnCamera);
        btnCreateMoment= (Button) findViewById(R.id.btnSaveMoment);
        etMoment= (EditText) findViewById(R.id.etMoment);
        tvError= (TextView) findViewById(R.id.tvError);
        btnCamera.setOnClickListener(this);
        btnCreateMoment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCamera:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, IMAGE_REQUEST);
                break;
            case R.id.btnSaveMoment:
                if (uri != null) {
                    final ProgressDialog dialog = new ProgressDialog(this);
                    dialog.setTitle("uploading image");
                    dialog.show();
                    StorageReference reference = mRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(uri));
                    reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            dialog.dismiss();
                            Toast.makeText(Camera.this, "upload completed", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(Camera.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Plz Wait...." + (int) progress+"%");
                        }
                    });
                } else {
                    Toast.makeText(this, "Select image plz", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = (Bitmap) data.getExtras().get("data");
        btnImageView.setImageBitmap(this.bitmap);
        uri = data.getData();
    }
    public String getImageExt(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
