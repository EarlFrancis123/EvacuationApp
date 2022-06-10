package com.evacuationapp.finalevacuationapp;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPlacesFragmentEvacuee#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPlacesFragmentEvacuee extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddPlacesFragmentEvacuee() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPlacesFragmentEvacuee.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPlacesFragmentEvacuee newInstance(String param1, String param2) {
        AddPlacesFragmentEvacuee fragment = new AddPlacesFragmentEvacuee();
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
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;
    DatabaseReference databaseReference5;
    DatabaseReference databaseReference4= FirebaseDatabase.getInstance().getReference();
    FirebaseDatabase firebaseDatabase2;
    EditText firstName, lastName, middleName, contactInfo,age,address,barangay,headOfFamily,edStreetAddress,edState;
    Button btnSave;
    ImageView imgPlace;
    String[] items =  {"Male","Fe Male"};
    String[] items2 =  {"Minor","Adult","Senior Citizen"};
    String[] items3 =  {"Philippines"};
    String[] items5 =  {"Have", "None"};
    AutoCompleteTextView gender,ageautocomplete,edCountry,evacuationName, disability;
    ArrayAdapter<String> adapterItems;
    ArrayAdapter<String> adapterItems2;
    ArrayAdapter<String> adapterItems3;
    ArrayAdapter<String> adapterItems4;
    ArrayAdapter<String> adapterItems5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_places_evacuee, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firstName = v.findViewById(R.id.edEvacueeFirstName);
        lastName = v.findViewById(R.id.edEvacueeLastName);
        middleName = v.findViewById(R.id.edEvacueeMiddleName);
        contactInfo = v.findViewById(R.id.edEvacueeContactNumber);
        disability = v.findViewById(R.id.auto_complete_txt_disability);
        age = v.findViewById(R.id.edEvacueeAge);
        edStreetAddress = v.findViewById(R.id.edStreet);
        edState = v.findViewById(R.id.edState);
        edCountry = v.findViewById(R.id.auto_complete_txt_country);
        evacuationName = v.findViewById(R.id.auto_complete_txt_evacuationName);
        firebaseDatabase2 = FirebaseDatabase.getInstance();
        btnSave = v.findViewById(R.id.btnSave);
        imgPlace = v.findViewById(R.id.imgPlace);
        gender = v.findViewById(R.id.auto_complete_txt_gender);
        ageautocomplete= v.findViewById(R.id.auto_complete_txt_age);

        adapterItems = new ArrayAdapter<String>(getContext().getApplicationContext(),R.layout.list_item,items);
        adapterItems2 = new ArrayAdapter<String>(getContext().getApplicationContext(),R.layout.list_item,items2);
        adapterItems3 = new ArrayAdapter<String>(getContext().getApplicationContext(),R.layout.list_item,items3);
        adapterItems5 = new ArrayAdapter<String>(getContext().getApplicationContext(),R.layout.list_item,items5);
        gender.setAdapter(adapterItems);
        ageautocomplete.setAdapter(adapterItems2);
        edCountry.setAdapter(adapterItems3);
        disability.setAdapter(adapterItems5);
        databaseReference3=firebaseDatabase.getReference().child("evacuation");
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList array= new ArrayList<>();
                for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {
                    String value2 = String.valueOf(dataSnapshot2.child("evacuationName").getValue());
                    array.add(value2);
                }
                adapterItems4 = new ArrayAdapter<String>(getContext().getApplicationContext(),R.layout.list_item,array);
                evacuationName.setAdapter(adapterItems4);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        evacuationName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });
        disability.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });
        gender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });
        ageautocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });
        edCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });


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
                EvacueeGettersSetters places1 = new EvacueeGettersSetters();
                Places places2 = new Places();
                //  List<Places> placesList=new ArrayList<>();
                if (TextUtils.isEmpty(firstName.getText().toString())) {
                    firstName.setError("This field is required");
                    Toast.makeText(getActivity(), "Please Input Value", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(lastName.getText().toString())) {
                    lastName.setError("This field is required");
                    Toast.makeText(getActivity(), "Please Input Value", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(middleName.getText().toString())) {
                    middleName.setError("This field is required");
                    Toast.makeText(getActivity(), "Please Input Value", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(contactInfo.getText().toString())) {
                    contactInfo.setError("This field is required");
                    Toast.makeText(getActivity(), "Please Input Value", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(gender.getText().toString())) {
                    gender.setError("This field is required");
                    Toast.makeText(getActivity(), "Please Input Value", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(age.getText().toString())) {
                    age.setError("This field is required");
                    Toast.makeText(getActivity(), "Please Input Value", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(ageautocomplete.getText().toString())) {
                    ageautocomplete.setError("This field is required");
                    Toast.makeText(getActivity(), "Please Input Value", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(edStreetAddress.getText().toString())) {
                    edStreetAddress.setError("This field is required");
                    Toast.makeText(getActivity(), "Please Input Value", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(edState.getText().toString())) {
                    edState.setError("This field is required");
                    Toast.makeText(getActivity(), "Please Input Value", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(edCountry.getText().toString())) {
                    edCountry.setError("This field is required");
                    Toast.makeText(getActivity(), "Please Input Value", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(evacuationName.getText().toString())) {
                    evacuationName.setError("This field is required");
                    Toast.makeText(getActivity(), "Please Input Value", Toast.LENGTH_SHORT).show();
                }
                else {


                    try {

                        Query countQuery = databaseReference4.child("Capacity").child(evacuationName.getText().toString());
                        countQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {


                                String value2 = String.valueOf(snapshot.child("evacuationCapacity").getValue());
                                String value3 = String.valueOf(snapshot.child("totalEvacuee").getValue());

                                if (Integer.parseInt(value3) < Integer.parseInt(value2)) {

                                    places1.setFirstName(firstName.getText().toString());
                                    places1.setLastName(lastName.getText().toString());
                                    places1.setMiddleName(middleName.getText().toString());
                                    places1.setContactInfo(contactInfo.getText().toString());
                                    places1.setGender(gender.getText().toString());
                                    places1.setAge(age.getText().toString());
                                    places1.setAgeautocomplete(ageautocomplete.getText().toString());
                                    places1.setStreetAddress(edStreetAddress.getText().toString());
                                    places1.setState(edState.getText().toString());
                                    places1.setCountry(edCountry.getText().toString());
                                    places1.setDisability(disability.getText().toString());
                                    places1.setEvacuationName(evacuationName.getText().toString());
                                    places1.setLatitude(getLatLongFromAddress(requireContext(), places1.getStreetAddress() + "," +
                                            places1.getState() + "," +
                                            places1.getCountry() + ",").latitude);
                                    places1.setLongitude(getLatLongFromAddress(requireContext(), places1.getStreetAddress() + "," +
                                            places1.getState() + "," +
                                            places1.getCountry() + ",").longitude);
                                    databaseReference5 = FirebaseDatabase.getInstance().getReference("Capacity").child(evacuationName.getText().toString()).child("totalEvacuee");
                                    databaseReference5.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            long totalevacuee =(long) dataSnapshot.getValue();
                                            databaseReference5.setValue(totalevacuee + 1);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    places1.setImage(encodeImage);
                                    databaseReference = firebaseDatabase.getReference().child("evacuee");
                                    databaseReference.push().setValue(places1);
                                    Toast.makeText(getActivity(), "Dara Added Successfully", Toast.LENGTH_SHORT).show();
                                    firstName.setText("");
                                    lastName.setText("");
                                    middleName.setText("");
                                    contactInfo.setText("");
                                    gender.setText("");
                                    age.setText("");
                                    ageautocomplete.setText("");
                                    edStreetAddress.setText("");
                                    edState.setText("");
                                    edCountry.setText("");
                                    evacuationName.setText("");


                                    imgPlace.setImageResource(android.R.drawable.ic_menu_gallery);


                                }else {
                                    Toast.makeText(getContext().getApplicationContext(), "Evacuation Full", Toast.LENGTH_SHORT).show();
                                }
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } catch (Exception e){
                        Toast.makeText(getContext().getApplicationContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                    }




                }

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