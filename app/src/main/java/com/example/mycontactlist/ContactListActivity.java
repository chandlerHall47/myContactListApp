package com.example.mycontactlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.SwitchCompat$InspectionCompanion;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    ContactAdapter contactAdapter;
    ArrayList<Contact> contacts;



    private final View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            int contactId = contacts.get(position).getContactID();
            Intent intent = new Intent(ContactListActivity.this, MainActivity.class);
            intent.putExtra("contactID", contactId);
            startActivity(intent);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        initMapButton();
        initSettingsButton();
        initListButton();

        initAddContactButton();
        initDeleteSwitch();

        BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
               //code to convert broadcast into battery level on screen
                double batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
                double levelScale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,0);
                int batteryPercent = (int) Math.floor(batteryLevel/levelScale *100);
                TextView textBatteryState = (TextView) findViewById(R.id.textBatteryLevel);
                textBatteryState.setText(batteryPercent + "%");
            }
        };
// this filter makes sure that only battery changed intents get through
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);

    }


    @Override
    public void onResume() {
        super.onResume();


        String sortBy = getSharedPreferences("MyContactListPreferences",
                Context.MODE_PRIVATE).getString("sortfield", "contactname");
        String sortOrder = getSharedPreferences("MyContactListPreferences",
                Context.MODE_PRIVATE).getString("sortorder", "ASC");

        ContactDataSource ds = new ContactDataSource(this);

        try {
            ds.open();
            contacts = ds.getContacts(sortBy, sortOrder);
            ds.close();


            if (contacts.size() > 0) {
                RecyclerView contactList = findViewById(R.id.rvContacts);
                contactAdapter = new ContactAdapter(contacts, this);
                contactAdapter.setOnItemClickListener(onItemClickListener);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                contactList.setLayoutManager(layoutManager);
                contactList.setAdapter(contactAdapter);
            } else {
                Intent intent = new Intent(ContactListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
        catch(Exception e){
                Toast.makeText(this, "Error retrieving contacts", Toast.LENGTH_SHORT).show();
            }

        }


    private void initMapButton(){
        ImageButton ibMap = findViewById(R.id.imageButtonMap);
        ibMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactListActivity.this, ContactMapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


    private void initSettingsButton(){
        ImageButton ibSettings = findViewById(R.id.imageButtonSettings);
        ibSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactListActivity.this, ContactSettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


    private void initListButton(){
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setEnabled(false);
        ibList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactListActivity.this, ContactListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

    }


    private void initAddContactButton(){
        Button newContact = findViewById(R.id.buttonAddContact);
        newContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }



    private void initDeleteSwitch(){
       Switch s = findViewById(R.id.switchDelete);

        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean status = compoundButton.isChecked();
                contactAdapter.setDelete(status);
                contactAdapter.notifyDataSetChanged();
            }
        });
    }

}