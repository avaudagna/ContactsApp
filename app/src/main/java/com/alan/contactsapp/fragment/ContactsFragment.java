package com.alan.contactsapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alan.contactsapp.constant.IntentConst;
import com.alan.contactsapp.controller.AppController;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.alan.contactsapp.activity.DetailsActivity;
import com.alan.contactsapp.adapter.ContactsAdapter;
import com.alan.contactsapp.adapter.model.Contact;
import com.alan.contactsapp.parser.ContactParser;
import com.alan.contactsapp.R;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 *  Fragment class that represents the message app UI.
 *
 *  @author alan
 */
public class ContactsFragment extends Fragment implements
        AdapterView.OnItemClickListener,
        Response.Listener<JSONArray>,
        Response.ErrorListener,
        IntentConst {

    private static final String JSON_URL = "https://solstice.applauncher.com/external/contacts.json";
    private static final String LOG_TAG = ContactsFragment.class.getSimpleName();
    private ContactsAdapter mAdapter;

    public ContactsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.list);

        mAdapter = new ContactsAdapter(getContext(), new ArrayList());

        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);

        final JsonArrayRequest request = new JsonArrayRequest(JSON_URL, this, this);
        AppController.getInstance(getContext()).addToRequestQueue(request);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Contact contact = (Contact) parent.getAdapter().getItem(position);

        Intent detailsActivity = new Intent(getActivity(), DetailsActivity.class);

        detailsActivity.putExtra(INTENT_NAME, contact.getName());
        detailsActivity.putExtra(INTENT_WORK, contact.getPhoneNumber());
        detailsActivity.putExtra(INTENT_DETAILS_URL, contact.getDetailsUrl());
        detailsActivity.putExtra(INTENT_BIRTH_DATE, contact.getBirthDate());
        detailsActivity.putExtra(INTENT_COMPANY, contact.getCompany());

        startActivity(detailsActivity);
    }

    @Override
    public void onResponse(JSONArray response) {
        ContactParser parser = new ContactParser(response);

        mAdapter.setItems(parser.jsonToContactsList());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        VolleyLog.d(LOG_TAG, "Error: " + error.getMessage());
    }
}
