package com.alan.contactsapp.parser;

import com.alan.contactsapp.adapter.model.Contact;
import com.alan.contactsapp.constant.ParserConst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alan
 */
public class ContactParser implements ParserConst {
    private JSONArray mContactsArray;

    public ContactParser(final JSONArray contactsArray) {
        this.mContactsArray = contactsArray;
    }

    public List jsonToContactsList() {
        List contacts = new ArrayList<>();

        try {
            // Parsing json array response
            // loop through each json object
            for (int i = 0; i < mContactsArray.length(); i++) {

                JSONObject person = (JSONObject) mContactsArray.get(i);

                contacts.add(
                        i,
                        new Contact()
                                .setName(person.getString(NAME))
                                .setPhoneNumber(person
                                        .getJSONObject(PHONE)
                                        .getString(WORK_PHONE))
                                .setSmallImageUrl(person.getString(SMALL_IMAGE_URL))
                                .setDetailsUrl(person.getString(DETAILS_URL))
                                .setBirthDate(Long.parseLong(person.getString(BIRTH_DATE)))
                                .setCompany(person.getString(COMPANY))
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contacts;
    }
}
