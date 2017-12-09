package com.example.mauri_r95.mimascota10.Vista;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mauri_r95.mimascota10.FirebaseReference;
import com.example.mauri_r95.mimascota10.Modelo.Mascota;
import com.example.mauri_r95.mimascota10.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Mauri_R95 on 07-12-2017.
 */

public class AgregarTagActivity extends AppCompatActivity {

    private NfcAdapter adapter;
    private TextView idTag;


    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escanear_tag);

        adapter = NfcAdapter.getDefaultAdapter(this);
        idTag = (TextView)findViewById(R.id.textTag);

        if(adapter == null){
            finish();
        }else if(!adapter.isEnabled()){
            Toast.makeText(getApplicationContext(), "NFC apagado",Toast.LENGTH_SHORT).show();
            startActivity(
                    new Intent(Settings.ACTION_NFC_SETTINGS));
        }else{
            Toast.makeText(getApplicationContext(), "NFC disponible",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if(intent.hasExtra(NfcAdapter.EXTRA_TAG)) {
            //Toast.makeText(this, "intent NFC recibido", Toast.LENGTH_SHORT).show();
            String tagid = bytesToHex(tag.getId());
            //idTag.setText("TagID: " + bytesToHex(tag.getId()));

            Bundle extras = getIntent().getExtras();
            String activity = extras.getString("activity");
            String cat_get = extras.getString("cat_get");
            Mascota mascota = extras.getParcelable("mascota");
            String mas_key = extras.getString("key");
            String imagen_nom = extras.getString("imagen_nom");

            Intent intent1 = new Intent(this, CargatTagActivity.class);
            intent1.putExtra("1", "segundo");
            intent1.putExtra("cat_get", cat_get);
            intent1.putExtra("key", mas_key);
            intent1.putExtra("activity", activity);
            intent1.putExtra("mascota", mascota);
            intent1.putExtra("imagen_nom", imagen_nom);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


            intent1.putExtra("tag", tagid);
            intent1.putExtra("scan_add", "agregar");
            startActivity(intent1);
        }
        super.onNewIntent(intent);
    }

    public static String bytesToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            buf.append(byteToHex(data[i]).toUpperCase());
            //buf.append(" ");
        }
        return (buf.toString());
    }

    /**
     * metodo para convertir un byte a hexa string
     *
     * @param  data  el byte a convertir
     * @return el byte convertido en string
     */
    public static String byteToHex(byte data) {
        StringBuffer buf = new StringBuffer();
        buf.append(toHexChar((data >>> 4) & 0x0F));
        buf.append(toHexChar(data & 0x0F));
        return buf.toString();
    }

    /**
     * metodo para convertir un int a un hexa char
     *
     * @param  i  el int a convertir
     * @return char del i convertido
     */
    public static char toHexChar(int i) {
        if ((0 <= i) && (i <= 9)) {
            return (char) ('0' + i);
        } else {
            return (char) ('a' + (i - 10));
        }
    }

    @Override
    protected void onResume() {
       // Toast.makeText(this, "OnResume", Toast.LENGTH_SHORT).show();
        enableForegroundDispatchSystem();
        super.onResume();


    }

    @Override
    protected void onPause() {
        disableForegroundDispatchSystem();
        super.onPause();
    }

    public void enableForegroundDispatchSystem(){

        Intent intent = new Intent(this, AgregarTagActivity.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        IntentFilter[] intentFilters = new IntentFilter[]{};
        adapter.enableForegroundDispatch(this,pendingIntent,intentFilters,null);

    }
    public void disableForegroundDispatchSystem(){
        adapter.disableForegroundDispatch(this);
    }
}

