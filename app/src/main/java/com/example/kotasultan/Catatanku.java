package com.example.kotasultan;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import java.util.Comparator;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class Catatanku extends Fragment {

    public static final String EXTRA_MESSAGE1 = "Nama";
    public static final String EXTRA_MESSAGE2 = "Nilai";
    public static final String EXTRA_MESSAGE3 = "Tanggal";
    public static final String EXTRA_MESSAGE4 = "Koin";

    private TextView mCurrencyPengeluaran,mCurrencyPemasukan,mCurrentKoin;
    private EditText editPengeluaran;
    private  EditText editValuePengeluaran;
    private  EditText editDatePengeluaran;
    private  EditText editPemasukan;
    private  EditText editValuePemasukan;
    private  EditText editDatePemasukan;
    public static Number currKoin;

    public static SortedMap<Currency, Locale> currencyLocaleMap;
    TextView t;
    Geocoder geocoder;
    private static final Map< String, Locale > COUNTRY_TO_LOCALE_MAP = new HashMap< String, Locale >();
    static {
        Locale[] locales = Locale.getAvailableLocales();
        for (Locale l: locales) {
            COUNTRY_TO_LOCALE_MAP.put(l.getCountry(), l);
        }
    }
    public static Locale getLocaleFromCountry(String country) {
        return COUNTRY_TO_LOCALE_MAP.get(country);
    }

    String Currencysymbol = "";


    public Catatanku() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_catatanku, container, false);

        mCurrentKoin = view.findViewById(R.id.koin_val);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").document(MainActivity.Uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot user = task.getResult();
                            if (user.contains("koin")){
                                currKoin = (Number) user.get("koin");
                                mCurrentKoin.setText(currKoin.toString());
                            }else{
                                Log.d("catatanku", "coin not found");
                            }
                        }else{
                            Log.d("catatanku", "failure getting document", task.getException());
                        }
                    }
                });

        Button buttonCurrency = (Button) view.findViewById(R.id.button_currency);
        buttonCurrency.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TextView t1 = (TextView) getView().findViewById(R.id.currency);
                TextView t2 = (TextView) getView().findViewById(R.id.currency2);

                GPSTracker gpsTracker = new GPSTracker(getActivity());
                geocoder = new Geocoder(getActivity(), getLocaleFromCountry(""));
                LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
                boolean gps_enabled = false;
                boolean network_enabled = false;
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                if(!gps_enabled && !network_enabled){
                new AlertDialog.Builder(getActivity())
                            .setTitle("GPS Not Found!").show();   ;  // GPS not found
                } else {
                    double lat_calibrate = -6.90389;
                    double lng_calibrate = 107.61861;
                    double lat = gpsTracker.getLatitude() + lat_calibrate;
                    double lng = gpsTracker.getLongitude() + lng_calibrate;


                    Log.e("Lat long ", lng + "lat long check" + lat);
                    Log.d("Lat long ", lng + "lat long check" + lat);

                    currencyLocaleMap = new TreeMap<Currency, Locale>(new Comparator<Currency>() {
                        public int compare(Currency c1, Currency c2) {
                            return c1.getCurrencyCode().compareTo(c2.getCurrencyCode());
                        }
                    });

                    for (Locale locale : Locale.getAvailableLocales()) {
                        try {
                            Currency currency = Currency.getInstance(locale);
                            currencyLocaleMap.put(currency, locale);

                            Log.d("locale util", currency + " locale1 " + locale.getCountry());

                        } catch (Exception e) {

                            Log.d("locale util", "e" + e);
                        }
                    }

                    try {

                        List<Address> addresses = geocoder.getFromLocation(lat, lng, 2);
                        Address obj = addresses.get(0);
                        Currencysymbol = getCurrencyCode(obj.getCountryCode());

                        Log.e("getCountryCode", "Exception address " + obj.getCountryCode());
                        Log.e("Currencysymbol", "Exception address " + Currencysymbol);

                    } catch (Exception e) {
                        Log.e("Exception address", "Exception address" + e);
                        Log.e("Currencysymbol", "Exception address" + Currencysymbol);
                    }
                    Log.d("Ada", Currencysymbol);
                }
                if(Currencysymbol != null) {
                    t1.setText(Currencysymbol);
                    t2.setText(Currencysymbol);
                }
            }

            public String getCurrencyCode(String countryCode) {

                String s = "";
                for (Locale locale: Locale.getAvailableLocales()) {
                    try {

                        if (locale.getCountry().equals(countryCode)) {

                            Currency currency = Currency.getInstance(locale);
                            currencyLocaleMap.put(currency, locale);
                            Log.d("locale util", currency + " locale1 " + locale.getCountry() + "s " + s);
                            s = getCurrencySymbol(currency + "");
                        }
                    } catch (Exception e) {
                        Log.d("locale util", "e" + e);
                    }
                }
                return s;
            }

            public String getCurrencySymbol(String currencyCode) {
                Currency currency = Currency.getInstance(currencyCode);
                System.out.println(currencyCode + ":-" + currency.getSymbol(currencyLocaleMap.get(currency)));
                return currency.getSymbol(currencyLocaleMap.get(currency));
            }
        });

        Button buttonPengeluaran = (Button) view.findViewById(R.id.button_pengeluaran);
        buttonPengeluaran.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editPengeluaran = (EditText) getView().findViewById(R.id.editPengeluaran);
                editValuePengeluaran = (EditText) getView().findViewById(R.id.editValuePengeluaran);
                editDatePengeluaran = (EditText) getView().findViewById(R.id.editDatePengeluaran);
                mCurrencyPengeluaran =(TextView) getView().findViewById(R.id.currency);

                Map<String,Object> pengeluaran = new HashMap<>();
                pengeluaran.put("nama",editPengeluaran.getText().toString());
                pengeluaran.put("nilai",editValuePengeluaran.getText().toString());
                pengeluaran.put("tanggal",editDatePengeluaran.getText().toString());
                pengeluaran.put("currency",mCurrencyPengeluaran.getText().toString());
                pengeluaran.put("jenis","pengeluaran");
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("History").document(MainActivity.Uid).collection("data")
                        .add(pengeluaran)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("catatanku","success");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("catatanku","failed",e);
                    }
                });


                Intent intent = new Intent(getActivity(), DisplayInputActivity.class);

                String message1 = editPengeluaran.getText().toString();
                String message2 = editValuePengeluaran.getText().toString();
                String message3 = editDatePengeluaran.getText().toString();
                long nilai = Long.parseLong(message2);
                long coinVal = convertToCoin(nilai);
                String message4 = Long.toString(coinVal);
                long koin = Long.parseLong(mCurrentKoin.getText().toString()) + coinVal;
                Map<String,Object> userKoin = new HashMap<>();
                Number value = (Number) koin;
                userKoin.put("koin",value);
                db.collection("Users").document(MainActivity.Uid)
                        .set(userKoin)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("catatanku","success");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("catatanku","failed",e);
                            }
                        });

                intent.putExtra(EXTRA_MESSAGE1, message1);
                intent.putExtra(EXTRA_MESSAGE2, message2);
                intent.putExtra(EXTRA_MESSAGE3, message3);
                intent.putExtra(EXTRA_MESSAGE4, message4);

                startActivity(intent);
            }

            public long convertToCoin(long number){
                int max = 1000;

                if (number / 100 > max){
                    return max;
                }else{
                    return number / 100;
                }
            }
        });

        Button buttonPemasukan = (Button) view.findViewById(R.id.button_pemasukan);
        buttonPemasukan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editPemasukan = (EditText) getView().findViewById(R.id.editPemasukan);
                editValuePemasukan = (EditText) getView().findViewById(R.id.editValuePemasukan);
                editDatePemasukan = (EditText) getView().findViewById(R.id.editDatePemasukan);
                mCurrencyPemasukan = (TextView) getView().findViewById(R.id.currency2);

                Map<String,Object> pengeluaran = new HashMap<>();
                pengeluaran.put("nama",editPemasukan.getText().toString());
                pengeluaran.put("nilai",editValuePemasukan.getText().toString());
                pengeluaran.put("tanggal",editDatePemasukan.getText().toString());
                pengeluaran.put("currency",mCurrencyPemasukan.getText().toString());
                pengeluaran.put("jenis","pemasukan");
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("History").document(MainActivity.Uid).collection("data")
                        .add(pengeluaran)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("catatanku","success");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("catatanku","failed",e);
                    }
                });

                Intent intent = new Intent(getActivity(), DisplayInputActivity.class);

                String message1 = editPemasukan.getText().toString();
                String message2 = editValuePemasukan.getText().toString();
                String message3 = editDatePemasukan.getText().toString();
                long nilai = Long.parseLong(message2);
                long coinVal = convertToCoin(nilai);
                String message4 = Long.toString(coinVal);
                long koin = Long.parseLong(mCurrentKoin.getText().toString()) + coinVal;
                Map<String,Object> userKoin = new HashMap<>();
                Number value = (Number) koin;
                userKoin.put("koin",value);
                db.collection("Users").document(MainActivity.Uid)
                        .set(userKoin)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("catatanku","success");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("catatanku","failed",e);
                            }
                        });

                intent.putExtra(EXTRA_MESSAGE1, message1);
                intent.putExtra(EXTRA_MESSAGE2, message2);
                intent.putExtra(EXTRA_MESSAGE3, message3);
                intent.putExtra(EXTRA_MESSAGE4, message4);

                startActivity(intent);
            }

            public long convertToCoin(long number){
                int max = 1000;

                if (number / 100 > max){
                    return max;
                }else{
                    return number / 100;
                }
            }
        });

        return view;
    }
}
