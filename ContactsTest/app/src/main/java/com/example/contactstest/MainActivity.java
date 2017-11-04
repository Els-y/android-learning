package com.example.contactstest;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;

    private List<String> contactsList = new ArrayList<>();

    private EditText nameEdit;

    private EditText numberEdit;

    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEdit = (EditText) findViewById(R.id.name_edit);
        numberEdit = (EditText) findViewById(R.id.number_edit);
        submit = (Button) findViewById(R.id.submit);

        ListView contactsView = (ListView) findViewById(R.id.contacts_view);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactsList);
        contactsView.setAdapter(adapter);
        contactsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String contact = contactsList.get(position);
                String[] info = contact.split("\n");
                Log.d("onItemClick", info[0]);
                Log.d("onItemClick", info[1]);
                nameEdit.setText(info[0]);
                numberEdit.setText(info[1]);
                Toast.makeText(parent.getContext(), contact, Toast.LENGTH_SHORT).show();
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.READ_CONTACTS
            }, 1);
        } else {
            readContacts();
        }

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {
                            Manifest.permission.WRITE_CONTACTS
                    }, 2);
                } else {
                    changeNumber();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    changeNumber();
                } else {
                    Toast.makeText(this, "You denied the permission 2", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    public void readContacts() {
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactsList.add(displayName + "\n" + number);
                }
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void changeNumber() {
        String name = nameEdit.getText().toString();
        String number = numberEdit.getText().toString();

        Toast.makeText(this, "Change Number to " + number, Toast.LENGTH_SHORT).show();
        Log.d("changeNumber", "changeNumber: " + ContactsContract.CommonDataKinds.Phone.NUMBER);

        try {
            ContentValues values = new ContentValues();
            values.put(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/phone_v2");
            values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, number);
            getContentResolver().update(ContactsContract.Data.CONTENT_URI, values,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = ?", new String[] {
                            name
                    });
            adapter.notifyDataSetChanged();
//            Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = ?", new String[] {
//                            name
//                    }, null);
//
//            final String TAG = "changeNumber";
//            while (cursor.moveToNext()) {
//                String queryName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                String queryNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                Log.d(TAG, "name: " + queryName);
//                Log.d(TAG, "number: " + queryNumber);
//            }
//            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
