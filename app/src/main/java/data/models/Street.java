package data.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Street implements Parcelable {
    private int StreetNameIdMax;
    private String StreetName;
    private int CommunityId;
    private int CityId;

    public Street(){}

    public Street(int streetNameIdMax, String streetName, int communityId, int cityId) {
        StreetNameIdMax = streetNameIdMax;
        StreetName = streetName;
        CommunityId = communityId;
        CityId = cityId;
    }

    protected Street(Parcel in) {
        StreetNameIdMax = in.readInt();
        StreetName = in.readString();
        CommunityId = in.readInt();
        CityId = in.readInt();
    }

    public static final Creator<Street> CREATOR = new Creator<Street>() {
        @Override
        public Street createFromParcel(Parcel in) {
            return new Street(in);
        }

        @Override
        public Street[] newArray(int size) {
            return new Street[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(StreetNameIdMax);
        parcel.writeString(StreetName);
        parcel.writeInt(CommunityId);
        parcel.writeInt(CityId);
    }

    @NonNull
    @Override
    public String toString() {
        return StreetName;
    }

    //region GETTERS AND SETTERS
    public int getStreetNameIdMax() {
        return StreetNameIdMax;
    }

    public void setStreetNameIdMax(int streetNameIdMax) {
        StreetNameIdMax = streetNameIdMax;
    }

    public String getStreetName() {
        return StreetName;
    }

    public void setStreetName(String streetName) {
        StreetName = streetName;
    }

    public int getCommunityId() {
        return CommunityId;
    }

    public void setCommunityId(int communityId) {
        CommunityId = communityId;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }
    //endregion
}
