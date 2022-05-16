package com.evacuationapp.finalevacuationapp;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPLacesFragmentCalamity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPLacesFragmentCalamity extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddPLacesFragmentCalamity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPLacesFragmentCalamity.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPLacesFragmentCalamity newInstance(String param1, String param2) {
        AddPLacesFragmentCalamity fragment = new AddPLacesFragmentCalamity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
    }
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    EditText edCalamityName, edCalamityDetails;
    Button btnSave;
    ImageView imgPlace;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_p_laces_calamity, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        edCalamityName = v.findViewById(R.id.edCalamityName);
        edCalamityDetails = v.findViewById(R.id.edCalamityDetails);

        btnSave = v.findViewById(R.id.btnSave);
        imgPlace = v.findViewById(R.id.imgPlace);

        ActivityCompat.requestPermissions(
                getActivity(), new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}
                , 1

        );
        imgPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if (!checkCameraPermission()) {

                // } else {
                takeImage();
                // }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Places places=new Places();
                //  List<Places> placesList=new ArrayList<>();
                places.setCalamityName(edCalamityName.getText().toString());
                places.setCalamityDetails(edCalamityDetails.getText().toString());
                places.setImage(encodeImage);



                databaseReference=firebaseDatabase.getReference("calamity").child(edCalamityName.getText().toString());
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.setValue(places);
                        Toast.makeText(getActivity(), "Dara Added Successfully", Toast.LENGTH_SHORT).show();
                        edCalamityName.setText("");
                        edCalamityDetails.setText("");
                        imgPlace.setImageResource(android.R.drawable.ic_menu_gallery);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return v;
    }
    LatLng getLatLongFromAddress(Context context, String strAddress) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> address;
        LatLng latLng = null;
        try {
            address = geocoder.getFromLocationName(strAddress, 2);
            if (address == null) {
                return null;
            }

            Address loc = address.get(0);
            latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
        } catch (Exception e) {
        }
        return latLng;
    }



    public Bitmap getResizedBitmap(Bitmap image) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = 120;
            height = (int) (width / bitmapRatio);
        } else {
            height = 120;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public boolean checkCameraPermission() {
        int result1 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result3 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

        return result1 == PackageManager.PERMISSION_GRANTED &&
                result2 == PackageManager.PERMISSION_GRANTED &&
                result3 == PackageManager.PERMISSION_GRANTED;
    }


    void takeImage() {
        Toast.makeText(requireContext(),"ïmage click",Toast.LENGTH_SHORT).show();
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(getActivity(), this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode == RESULT_OK) {
            Uri resulturi = result.getUri();
            String path = FileUtils.getPath(getContext(), resulturi);
            compressImage(path);
        }
    }


    void compressImage(String path) {
        Luban.compress(getActivity(), new File(path))
                .setMaxSize(50)
                .launch(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        Bitmap bitmap = getResizedBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                        ByteArrayOutputStream b = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
                        byte[] byteArray = b.toByteArray();
                        encodeImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        Picasso.with(getContext()).load(file).into(imgPlace);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    String  encodeImage;
}