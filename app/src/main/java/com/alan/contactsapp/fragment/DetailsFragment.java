package com.alan.contactsapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alan.contactsapp.R;
import com.alan.contactsapp.constant.IntentConst;
import com.alan.contactsapp.constant.ParserConst;
import com.alan.contactsapp.controller.AppController;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author alan
 */
public class DetailsFragment extends Fragment implements
        Response.Listener<JSONObject>,
        Response.ErrorListener,
        ParserConst,
        IntentConst {

    private static final String LOG_TAG = DetailsFragment.class.getSimpleName();
    private static final String DATE_PATTERN = "MMMM dd, yyyy";
    private NetworkImageView mProfilePicture;
    private TextView mContactName;
    private TextView mContactCompany;
    private RatingBar mContactFavorite;
    private TextView mPhoneNumber;
    private TextView mAddressNumber;
    private TextView mBirthDate;
    private TextView mEmailDirection;

    public DetailsFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_details, container, false);

        findViewsByParent(view);
        bindViews(getActivity().getIntent());

        return view;
    }

    /**
     *
     * @param view
     */
    private void findViewsByParent(@NonNull final View view) {
        mProfilePicture = (NetworkImageView) view.findViewById(R.id.profile_picture);
        mContactName = (TextView) view.findViewById(R.id.contact_name);
        mContactCompany = (TextView) view.findViewById(R.id.contact_company);
        mContactFavorite = (RatingBar) view.findViewById(R.id.contact_favorite);
        mPhoneNumber = (TextView) view.findViewById(R.id.phone_number);
        mAddressNumber = (TextView) view.findViewById(R.id.address_number);
        mBirthDate = (TextView) view.findViewById(R.id.birth_date);
        mEmailDirection = (TextView) view.findViewById(R.id.email_direction);
    }

    /**
     *
     * @param intent
     */
    private void bindViews(@NonNull final Intent intent) {
        mContactName.setText(intent.getStringExtra(INTENT_NAME));
        mPhoneNumber.setText(intent.getStringExtra(INTENT_WORK));
        mContactCompany.setText(intent.getStringExtra(INTENT_COMPANY));

        setFormattedDate(intent.getLongExtra(INTENT_BIRTH_DATE, 0));
        getDetailsFromNetwork(intent.getStringExtra(INTENT_DETAILS_URL));
    }

    /**
     *
     * @param millis
     */
    private void setFormattedDate(@NonNull final Long millis) {
        Date date = new Date(millis);
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN, Locale.US);
        mBirthDate.setText(formatter.format(date));
    }

    /**
     *
     * @param url
     */
    private void getDetailsFromNetwork(@NonNull final String url) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(url, this, this);
        AppController.getInstance(getContext()).addToRequestQueue(jsonRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        ImageLoader loader = AppController.getInstance(getContext()).getImageLoader();
        mContactFavorite.setIsIndicator(true);
        mContactFavorite.setNumStars(1);

        try {
            if(response.getBoolean(FAVORITE))
                mContactFavorite.setRating(1);

            mProfilePicture.setImageUrl(response.getString(LARGE_IMAGE_URL), loader);
            mEmailDirection.setText(response.getString(EMAIL));
            mAddressNumber.setText(getAddressFromJson(response.getJSONObject(ADDRESS)));

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        VolleyLog.d(LOG_TAG, "Error: " + error.getMessage());
    }

    /**
     *
     * @param jsonAddress
     * @return
     */
    private String getAddressFromJson(@NonNull final JSONObject jsonAddress) {
        String address = null;

        try {
            address =  jsonAddress.getString(STREET) +
                    ", " + jsonAddress.getString(CITY) +
                    ", " + jsonAddress.getString(STATE) +
                    " " + jsonAddress.getString(COUNTRY);

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return address;
    }
}
