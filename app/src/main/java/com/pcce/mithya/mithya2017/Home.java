package com.pcce.mithya.mithya2017;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.mvc.imagepicker.ImagePicker;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import pl.aprilapps.easyphotopicker.EasyImage;

import static com.pcce.mithya.mithya2017.ScalingUltilities.ScalingLogic.FIT;

public class Home extends AppCompatActivity {
    public static DrawerLayout mDrawerLayout;
    public static Toolbar toolbar;
    public static NavigationView mNavigationView;
    public static FragmentManager mFragmentManager;
    public static FragmentTransaction mFragmentTransaction;
    public static Context ctx;
    public static TextView toolTitle;
    private FloatingActionButton imageadd;
    public static int count = 0;
    private ArrayList<Image> images = new ArrayList<>();
    private FirebaseStorage mFirebaseStorage;
    private DatabaseReference mDatabase;
    private DatabaseReference image;
    private Uri mUri;
    Uri filePath;
    private static final int ANIM_DURATION_TOOLBAR = 300;

    private int REQUEST_CODE_PICKER = 2000;
    int PICK_IMAGE_REQUEST = 111;
    StorageReference storageRef;

    Bitmap bitmapFINAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ctx = this;
        toolTitle = (TextView) findViewById(R.id.toolTitle);
        toolTitle.setTypeface(Main.myCustomFont);
        toolTitle.setText("Mithya 2017");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.navview);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mFirebaseStorage = FirebaseStorage.getInstance();
        imageadd = (FloatingActionButton) findViewById(R.id.imageupload);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://mithya-2017.appspot.com");

        subscribeToPushService();

        imageadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImagePicker.pickImage(Home.this, "Select your image:");


            }
        });


        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new HomeFragment()).commit();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        toolTitle.setText("Mithya 2017");
                        startIntroAnimation();
                        fragmentTransaction.replace(R.id.containerView, new HomeFragment()).commit();
                        return true;
                    case R.id.nav_events:
                        toolTitle.setText("Events - Mithya 2017");
                        startIntroAnimation();
                        fragmentTransaction.replace(R.id.containerView, new EventsFragment()).commit();
                        return true;
                    case R.id.nav_schedule:
                        startActivity(new Intent(Home.this, ScheduleActivity.class));

                        return true;
                    case R.id.nav_sos:
                        toolTitle.setText("Show Of Strength - Mithya 2017");
                        startIntroAnimation();
                        fragmentTransaction.replace(R.id.containerView, new StrengthFragment()).commit();
                        return true;
                    case R.id.nav_scores:
                        toolTitle.setText("Scores - Mithya 2017");
                        startIntroAnimation();
                        fragmentTransaction.replace(R.id.containerView, new ScoresFragment()).commit();
                        return true;
                    case R.id.nav_team:
                        toolTitle.setText("Team - Mithya 2017");
                        startIntroAnimation();
                        fragmentTransaction.replace(R.id.containerView, new TeamFragment()).commit();
                        return true;
                    case R.id.nav_fb:
                        toolTitle.setText("Facebook - Mithya 2017");
                        startIntroAnimation();
                        fragmentTransaction.replace(R.id.containerView, new FacebookFragment()).commit();
                        return true;
                    case R.id.nav_gallery:
                        toolTitle.setText("Gallery - Mithya 2017");
                        fragmentTransaction.replace(R.id.containerView, new GalleryFragment()).commit();
                        return true;
                    case R.id.nav_devs:
                        toolTitle.setText("Developers - Mithya 2017");
                        startIntroAnimation();
                        fragmentTransaction.replace(R.id.containerView, new DeveloperFragment()).commit();
                        return true;
                    default:
                        Toast.makeText(Home.this, "Error", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
        mNavigationView.setItemIconTintList(null);
        toolbar.setVisibility(View.GONE);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();
        startIntroAnimation();

        navFont();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void navFont() {
        Menu m = mNavigationView.getMenu();

        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);
            SpannableStringBuilder s = new SpannableStringBuilder(mi.getTitle());
            s.setSpan(new CustomTypefaceSpan("", Main.myCustomFont), 0, s.length(),
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            mi.setTitle(s);

        }
    }

    private void subscribeToPushService() {
        FirebaseMessaging.getInstance().subscribeToTopic("global");

        Log.d("AndroidBash", "Notifications Enabled");
        Toast.makeText(Home.this, "Notifications Enabled", Toast.LENGTH_SHORT).show();

        String token = FirebaseInstanceId.getInstance().getToken();

        // Log and toast
        //  Log.d("AndroidBash", token);
        //   Toast.makeText(Home.this, token, Toast.LENGTH_SHORT).show();
    }


    private void startIntroAnimation() {
        // btnCreate.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));

        int actionbarSize = Utils.dpToPx(56);
        toolbar.setTranslationY(-actionbarSize);
        //  ivLogo.setTranslationY(-actionbarSize);
        // inboxMenuItem.getActionView().setTranslationY(-actionbarSize);

        toolbar.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        bitmapFINAL = ImagePicker.getImageFromResult(getApplicationContext(), requestCode, resultCode, data);
        if (bitmapFINAL != null) {


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter Caption");

            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String caption = input.getText().toString();
                    upload(caption);
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
    }



    private void upload(final String caption) {

            //   pd.show();
            final ProgressDialog d = ProgressDialog.show(Home.this, "Loading", "Uploading Please Wait");
            d.show();

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmapFINAL.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] data = baos.toByteArray();


            StorageReference childRef = storageRef.child("imageupload").child(Calendar.getInstance().getTimeInMillis() + "");

            //uploading the image
            UploadTask uploadTask = childRef.putBytes(data);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //  pd.dismiss();
                    String a = taskSnapshot.getDownloadUrl().toString();
                    Log.d("abc", a);
                    image = mDatabase.child("gallery");
                    String userId = image.push().getKey();
                    ImageUpload imageUpload = new com.pcce.mithya.mithya2017.ImageUpload(a, caption);
                    image.child(userId).setValue(imageUpload);


                    Toast.makeText(Home.this, a, Toast.LENGTH_SHORT).show();
                    d.dismiss();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //pd.dismiss();
                    Toast.makeText(Home.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                }
            });

    }


}









