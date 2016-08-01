package com.example.dodirivaldi.pulsa;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String[] spinOperator;
    private String[] spinHarga;
    private String[] spinServer;
    EditText operator;
    EditText harga;
    EditText server;
    EditText hasil;
    Spinner s;
    EditText tujuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        oper();
        har();
        ser();

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void oper(){
        this.spinOperator = new String[]{
                "Telkomsel", "Indosat", "Three", "AXIS", "XL", "Esia", "BOLT", "SMART", "PLN"
        };

        operator = (EditText)findViewById(R.id.operatortext);
        s = (Spinner) findViewById(R.id.spinoper);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner, spinOperator);
        s.setAdapter(adapter);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String  mselection=s.getSelectedItem().toString();
                if (mselection.equals("Telkomsel"))
                { operator.setText("S"); }

                else if (mselection.equals("Indosat")) { operator.setText("I"); }

                else if (mselection.equals("Three"))
                {
                    operator.setText("T");
                }

                else if (mselection.equals("AXIS"))
                {
                    operator.setText("AXIS");
                }

                else if (mselection.equals("XL"))
                {
                    operator.setText("Xr");
                }

                else if (mselection.equals("SMART"))
                {
                    operator.setText("SMART");
                }

                else if (mselection.equals("BOLT"))
                {
                    operator.setText("BOLT");
                }

                else if (mselection.equals("PLN"))
                {
                    operator.setText("PLN");
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                //
            }
        });

    }

    public void har(){
        final EditText hasil = (EditText)findViewById(R.id.hasilText);
        harga = (EditText)findViewById(R.id.hargaText);

        this.spinHarga = new String[]{
                "5rb", "10rb", "20rb", "25rb", "50rb", "100rb"
        };
        final Spinner h = (Spinner) findViewById(R.id.spinharga);
        ArrayAdapter<String> adapterHarga = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinHarga);
        h.setAdapter(adapterHarga);
        h.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String  mselection=h.getSelectedItem().toString();
                if (mselection.equals("5rb"))
                {
                    harga.setText("5");
                }

                else if (mselection.equals("10rb"))
                {
                    harga.setText("10");
                }

                else if (mselection.equals("20rb"))
                {
                    harga.setText("20");
                }

                else if (mselection.equals("25rb"))
                {
                    harga.setText("25");
                }

                else if (mselection.equals("50rb"))
                {
                    harga.setText("50");
                }

                else if (mselection.equals("100rb"))
                {
                    harga.setText("100");
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                //
            }
        });

    }

    public void ser(){
        EditText hasil = (EditText)findViewById(R.id.hasilText);
        server = (EditText)findViewById(R.id.serverText);

        this.spinServer = new String[]{
                "085781448000", "085710652000","085282213222", "082111540777"
        };


        final Spinner v = (Spinner) findViewById(R.id.spinserver);

        ArrayAdapter<String> adapterServer = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinServer);
        v.setAdapter(adapterServer);



        v.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String  mselection=v.getSelectedItem().toString();
                server.setText(mselection);
                //Toast.makeText(getApplicationContext(), "No. Server "+ mselection, Toast.LENGTH_SHORT).show();
                /**** do your code*****/
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                //
            }
        });
    }

    public void proses(View v){
        EditText no = (EditText)findViewById(R.id.telpText);
        hasil = (EditText)findViewById(R.id.hasilText);
        tujuan = (EditText)findViewById(R.id.tujuanText);
        hasil.setText("I."+operator.getText().toString()+harga.getText().toString()+"."+no.getText().toString()+".8888");
        tujuan.setText(server.getText().toString());
    }

    public void hapus(View view){
        del();
    }

    public void del(){
        EditText no = (EditText)findViewById(R.id.telpText);
        EditText hasil = (EditText)findViewById(R.id.hasilText);
        EditText tujuan = (EditText)findViewById(R.id.tujuanText);

        no.setText("");
        hasil.setText("");
        tujuan.setText("");
    }

    public void kirim(View v){

        String nomor = tujuan.getText().toString();
        String pesan = hasil.getText().toString();

        if (nomor.length()>0 && pesan.length()>0){
            sendSMS(nomor, pesan);
            del();
            Toast.makeText(getApplicationContext(), "Pesan sudah dikirim", Toast.LENGTH_SHORT).show();
        }

        else if(nomor.equals("")&&pesan.equals("")){
            Toast.makeText(getBaseContext(),"No. tujuan dan pesan harus diisi",Toast.LENGTH_SHORT).show();
        }
    }

    private void sendSMS(String phoneNumber, String message){
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new
                Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS telah dikirim", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(),
                                "Generic failure", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS Terkirim", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS Tidak Terkirim", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI,
                deliveredPI);
    }


}
