package com.burgess.burgessgo.add_new_address;

import static com.burgess.burgessgo.Constants.PREF;
import static com.burgess.burgessgo.Constants.PREF_BUILDER_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.burgess.burgessgo.GoAPIQueue;
import com.burgess.burgessgo.GoLogger;
import com.burgess.burgessgo.R;
import com.burgess.burgessgo.ServerCallback;
import com.google.android.material.snackbar.Snackbar;

import data.models.Community;
import data.models.Street;

public class AddNewAddressActivity extends AppCompatActivity {
    private static final String TAG = "ADD_NEW_ADDRESS";

    // View components
    private AddNewAddressViewModel mViewModel;
    private ConstraintLayout mConstraintLayout;
    private Spinner mSpinnerCommunity;
    private Spinner mSpinnerStreet;
    private Spinner mSpinnerState;
    private Spinner mSpinnerCity;
    private Spinner mSpinnerCounty;
    private TextView mTextViewNewCommunityName;
    private TextView mTextViewNewStreetName;
    private TextView mTextViewHouseNumber;
    private TextView mTextViewCityName;
    private TextView mTextViewCountyName;
    private TextView mTextViewPermitNumber;
    private TextView mTextViewSquareFootage;
    private TextView mTextViewPlanNumber;
    private TextView mTextViewJobNumber;
    private Button mButtonSubmit;

    // Class members
    private static GoAPIQueue apiQueue;
    private static GoLogger logger;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private ArrayAdapter<Community> mSpinnerCommunityAdapter;
    private ArrayAdapter<Street> mSpinnerStreetAdapter;
    private ArrayAdapter<String> mSpinnerStateAdapter;
    private ArrayAdapter<String> mSpinnerCityAdapter;
    private ArrayAdapter<String> mSpinnerCountyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_address);
        setSupportActionBar(findViewById(R.id.add_new_address_toolbar));
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(AddNewAddressViewModel.class);

        // Prepare logger and API queue
        logger = GoLogger.getInstance(this);
        apiQueue = GoAPIQueue.getInstance(this);

        // Prepare shared preferences
        mSharedPreferences = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        initializeViews();
        initializeButtons();
        initializeSpinners();
        initializeDisplayContent();
    }

    public void initializeViews() {
        mConstraintLayout = findViewById(R.id.add_new_address_constraint_layout);
        mSpinnerCommunity = findViewById(R.id.add_new_address_spinner_community);
        mSpinnerStreet = findViewById(R.id.add_new_address_spinner_street);
        mSpinnerState = findViewById(R.id.add_new_address_spinner_state);
        mSpinnerCity = findViewById(R.id.add_new_address_spinner_city);
        mSpinnerCounty = findViewById(R.id.add_new_address_spinner_county);
        mTextViewNewCommunityName = findViewById(R.id.add_new_address_textView_new_community_name);
        mTextViewNewStreetName = findViewById(R.id.add_new_address_textView_new_street_name);
        mTextViewHouseNumber = findViewById(R.id.add_new_address_textView_house_number);
        mTextViewCityName = findViewById(R.id.add_new_address_textView_new_city_name);
        mTextViewCountyName = findViewById(R.id.add_new_address_textView_new_county_name);
        mTextViewPermitNumber = findViewById(R.id.add_new_address_textView_permit_number);
        mTextViewSquareFootage = findViewById(R.id.add_new_address_textView_square_footage);
        mTextViewPlanNumber = findViewById(R.id.add_new_address_textView_plan_number);
        mTextViewJobNumber = findViewById(R.id.add_new_address_textView_job_number);
        mButtonSubmit = findViewById(R.id.add_new_address_button_submit);
    }

    public void initializeButtons() {
        mButtonSubmit.setOnClickListener(v -> {
            Snackbar.make(mConstraintLayout, "Submit button clicked", Snackbar.LENGTH_SHORT).show();
        });
    }

    public void initializeDisplayContent() {
        updateCommunityList();
    }

    public void initializeSpinners() {
        mSpinnerCommunityAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, mViewModel.getCommunityList());
        mSpinnerStreetAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, mViewModel.getStreetList());
        mSpinnerStateAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, mViewModel.getStateList());
        mSpinnerCityAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, mViewModel.getCityList());
        mSpinnerCountyAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, mViewModel.getCountyList());

        mSpinnerCommunity.setSelection(0, false);

        mSpinnerCommunity.setAdapter(mSpinnerCommunityAdapter);
        mSpinnerStreet.setAdapter(mSpinnerStreetAdapter);
        mSpinnerState.setAdapter(mSpinnerStateAdapter);
        mSpinnerCity.setAdapter(mSpinnerCityAdapter);
        mSpinnerCounty.setAdapter(mSpinnerCountyAdapter);

        mSpinnerCommunity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == mViewModel.getCommunityList().size() - 2) {
                    Snackbar.make(mConstraintLayout, "Not a valid community selection, please try again", Snackbar.LENGTH_SHORT).show();
                    mSpinnerStreet.setVisibility(View.GONE);
                    mSpinnerState.setVisibility(View.GONE);
                    mSpinnerCity.setVisibility(View.GONE);
                    mSpinnerCounty.setVisibility(View.GONE);
                    mTextViewNewCommunityName.setVisibility(View.GONE);
                    mTextViewNewStreetName.setVisibility(View.GONE);
                    mTextViewHouseNumber.setVisibility(View.GONE);
                    mTextViewCityName.setVisibility(View.GONE);
                    mTextViewCountyName.setVisibility(View.GONE);
                    mTextViewPermitNumber.setVisibility(View.GONE);
                    mTextViewSquareFootage.setVisibility(View.GONE);
                    mTextViewPlanNumber.setVisibility(View.GONE);
                    mTextViewJobNumber.setVisibility(View.GONE);
                    mButtonSubmit.setVisibility(View.GONE);
                }
                else if (i == mViewModel.getCommunityList().size() - 1) {
                    Snackbar.make(mConstraintLayout, "Creating a new community...", Snackbar.LENGTH_SHORT).show();
                    mSpinnerStreet.setVisibility(View.GONE);

                    mTextViewNewCommunityName.setVisibility(View.VISIBLE);
                    mTextViewNewStreetName.setVisibility(View.VISIBLE);
                    mTextViewHouseNumber.setVisibility(View.VISIBLE);
                    mTextViewPermitNumber.setVisibility(View.VISIBLE);
                    mTextViewSquareFootage.setVisibility(View.VISIBLE);
                    mTextViewPlanNumber.setVisibility(View.VISIBLE);
                    mTextViewJobNumber.setVisibility(View.VISIBLE);
                    mButtonSubmit.setVisibility(View.VISIBLE);

                    mSpinnerState.setVisibility(View.VISIBLE);
                    mSpinnerState.setSelection(0, false);
                    updateStateList();
                    mSpinnerCity.setVisibility(View.VISIBLE);
                    mSpinnerCity.setSelection(0, false);
                    updateCityList();
                    mSpinnerCounty.setVisibility(View.VISIBLE);
                    mSpinnerCounty.setSelection(0, false);
                    updateCountyList();
                }
                else if (i != 0) {
                    Community selectedCommunity = (Community) adapterView.getSelectedItem();
                    Snackbar.make(mConstraintLayout, selectedCommunity.getCommunityName() + ", ID: " + selectedCommunity.getCommunityId(), Snackbar.LENGTH_SHORT).show();

                    mSpinnerState.setVisibility(View.GONE);
                    mSpinnerCity.setVisibility(View.GONE);
                    mSpinnerCounty.setVisibility(View.GONE);
                    mTextViewNewCommunityName.setVisibility(View.GONE);
                    mTextViewNewStreetName.setVisibility(View.GONE);
                    mTextViewHouseNumber.setVisibility(View.GONE);
                    mTextViewCityName.setVisibility(View.GONE);
                    mTextViewCountyName.setVisibility(View.GONE);
                    mTextViewPermitNumber.setVisibility(View.GONE);
                    mTextViewSquareFootage.setVisibility(View.GONE);
                    mTextViewPlanNumber.setVisibility(View.GONE);
                    mTextViewJobNumber.setVisibility(View.GONE);
                    mButtonSubmit.setVisibility(View.GONE);

                    mSpinnerStreet.setVisibility(View.VISIBLE);
                    mSpinnerStreet.setSelection(0, false);
                    updateStreetList(selectedCommunity.getCommunityId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSpinnerStreet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == mViewModel.getStreetList().size() - 2) {
                    Snackbar.make(mConstraintLayout, "Not a valid street selection, please try again", Snackbar.LENGTH_SHORT).show();

                    mSpinnerState.setVisibility(View.GONE);
                    mSpinnerCity.setVisibility(View.GONE);
                    mSpinnerCounty.setVisibility(View.GONE);
                    mTextViewNewCommunityName.setVisibility(View.GONE);
                    mTextViewNewStreetName.setVisibility(View.GONE);
                    mTextViewHouseNumber.setVisibility(View.GONE);
                    mTextViewCityName.setVisibility(View.GONE);
                    mTextViewCountyName.setVisibility(View.GONE);
                    mTextViewPermitNumber.setVisibility(View.GONE);
                    mTextViewSquareFootage.setVisibility(View.GONE);
                    mTextViewPlanNumber.setVisibility(View.GONE);
                    mTextViewJobNumber.setVisibility(View.GONE);
                    mButtonSubmit.setVisibility(View.GONE);
                }
                else if (i == mViewModel.getStreetList().size() - 1) {
                    Snackbar.make(mConstraintLayout, "Creating a new street...", Snackbar.LENGTH_SHORT).show();

                    mSpinnerState.setVisibility(View.GONE);
                    mSpinnerCity.setVisibility(View.GONE);
                    mSpinnerCounty.setVisibility(View.GONE);
                    mTextViewNewCommunityName.setVisibility(View.GONE);

                    mTextViewNewStreetName.setVisibility(View.VISIBLE);
                    mTextViewHouseNumber.setVisibility(View.VISIBLE);
                    mTextViewPermitNumber.setVisibility(View.VISIBLE);
                    mTextViewSquareFootage.setVisibility(View.VISIBLE);
                    mTextViewPlanNumber.setVisibility(View.VISIBLE);
                    mTextViewJobNumber.setVisibility(View.VISIBLE);
                    mButtonSubmit.setVisibility(View.VISIBLE);
                }
                else if (i != 0) {
                    Street selectedStreet = (Street) adapterView.getSelectedItem();
                    Snackbar.make(mConstraintLayout, selectedStreet.getStreetName() + ", ID: " + selectedStreet.getStreetNameIdMax(), Snackbar.LENGTH_SHORT).show();

                    mTextViewNewStreetName.setVisibility(View.GONE);

                    mTextViewHouseNumber.setVisibility(View.VISIBLE);
                    mTextViewPermitNumber.setVisibility(View.VISIBLE);
                    mTextViewSquareFootage.setVisibility(View.VISIBLE);
                    mTextViewPlanNumber.setVisibility(View.VISIBLE);
                    mTextViewJobNumber.setVisibility(View.VISIBLE);
                    mButtonSubmit.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSpinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    Snackbar.make(mConstraintLayout, adapterView.getSelectedItem().toString() + " selected", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSpinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == mViewModel.getCityList().size() - 2) {
                    Snackbar.make(mConstraintLayout, "Not a valid city selection, please try again", Snackbar.LENGTH_SHORT).show();

                    mTextViewCityName.setVisibility(View.GONE);
                } else if (i == mViewModel.getCityList().size() - 1) {
                    Snackbar.make(mConstraintLayout, "Creating a new city...", Snackbar.LENGTH_SHORT).show();

                    mTextViewCityName.setVisibility(View.VISIBLE);
                } else if (i != 0) {
                    Snackbar.make(mConstraintLayout, adapterView.getSelectedItem().toString() + " selected", Snackbar.LENGTH_SHORT).show();

                    mTextViewCityName.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSpinnerCounty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == mViewModel.getCountyList().size() - 2) {
                    Snackbar.make(mConstraintLayout, "Not a valid county selection, please try again", Snackbar.LENGTH_SHORT).show();

                    mTextViewCountyName.setVisibility(View.GONE);
                } else if (i == mViewModel.getCountyList().size() - 1) {
                    Snackbar.make(mConstraintLayout, "Creating a new county...", Snackbar.LENGTH_SHORT).show();

                    mTextViewCountyName.setVisibility(View.VISIBLE);
                } else if (i != 0) {
                    Snackbar.make(mConstraintLayout, adapterView.getSelectedItem().toString() + " selected", Snackbar.LENGTH_SHORT).show();

                    mTextViewCountyName.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void updateCommunityList() {
        mViewModel.clearCommunityList();
        apiQueue.getRequestQueue().add(apiQueue.getCommunityList(mViewModel, mSharedPreferences.getInt(PREF_BUILDER_ID, -1), null, null, new ServerCallback() {
            @Override
            public void onSuccess(String message) {
                mViewModel.insertHelperCommunity();
                mViewModel.insertAddNewCommunity();
                mSpinnerCommunityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(mConstraintLayout, message, Snackbar.LENGTH_SHORT).show();
            }
        }));
    }
    public void updateStateList() {
        mViewModel.clearStateList();
        apiQueue.getRequestQueue().add(apiQueue.getStateList(mViewModel, mSharedPreferences.getInt(PREF_BUILDER_ID, -1), null, null, new ServerCallback() {
            @Override
            public void onSuccess(String message) {
                mViewModel.insertHelperState();
                mSpinnerStateAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(mConstraintLayout, message, Snackbar.LENGTH_SHORT).show();
            }
        }));
    }
    public void updateCityList() {
        mViewModel.clearCityList();
        apiQueue.getRequestQueue().add(apiQueue.getCityList(mViewModel, mSharedPreferences.getInt(PREF_BUILDER_ID, -1), new ServerCallback() {
            @Override
            public void onSuccess(String message) {
                mViewModel.insertHelperCity();
                mViewModel.insertAddNewCity();
                mSpinnerCityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(mConstraintLayout, message, Snackbar.LENGTH_SHORT).show();
            }
        }));
    }
    public void updateCountyList() {
        mViewModel.clearCountyList();
        apiQueue.getRequestQueue().add(apiQueue.getCountyList(mViewModel, mSharedPreferences.getInt(PREF_BUILDER_ID, -1), null, null, new ServerCallback() {
            @Override
            public void onSuccess(String message) {
                mViewModel.insertHelperCounty();
                mViewModel.insertAddNewCounty();
                mSpinnerCountyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(mConstraintLayout, message, Snackbar.LENGTH_SHORT).show();
            }
        }));
    }
    public void updateStreetList(int communityId) {
        mViewModel.clearStreetList();
        apiQueue.getRequestQueue().add(apiQueue.getStreetList(mViewModel, mSharedPreferences.getInt(PREF_BUILDER_ID, -1), communityId, new ServerCallback() {
            @Override
            public void onSuccess(String message) {
                mViewModel.insertHelperStreet();
                mViewModel.insertAddNewStreet();
                mSpinnerStreetAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(mConstraintLayout, message, Snackbar.LENGTH_SHORT).show();
            }
        }));
    }
}