package data.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Community implements Parcelable {
    private int CommunityId;
    private int AreaId;
    private String CommunityName;
    private int CityId1;
    private String CityName1;
    private int CityId2;
    private String CityName2;
    private String State;
    private String Additional;
    private String AreaName;

    public Community(){}

    public Community(int communityId, int areaId, String communityName, int cityId1, String cityName1, int cityId2, String cityName2, String state,
                     String additional, String areaName) {
        CommunityId = communityId;
        AreaId = areaId;
        CommunityName = communityName;
        CityId1 = cityId1;
        CityName1 = cityName1;
        CityId2 = cityId2;
        CityName2 = cityName2;
        State = state;
        Additional = additional;
        AreaName = areaName;
    }

    protected Community(Parcel in) {
        CommunityId = in.readInt();
        AreaId = in.readInt();
        CommunityName = in.readString();
        CityId1 = in.readInt();
        CityName1 = in.readString();
        CityId2 = in.readInt();
        CityName2 = in.readString();
        State = in.readString();
        Additional = in.readString();
        AreaName = in.readString();
    }

    public static final Creator<Community> CREATOR = new Creator<Community>() {
        @Override
        public Community createFromParcel(Parcel in) {
            return new Community(in);
        }

        @Override
        public Community[] newArray(int size) {
            return new Community[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(CommunityId);
        parcel.writeInt(AreaId);
        parcel.writeString(CommunityName);
        parcel.writeInt(CityId1);
        parcel.writeString(CityName1);
        parcel.writeInt(CityId2);
        parcel.writeString(CityName2);
        parcel.writeString(State);
        parcel.writeString(Additional);
        parcel.writeString(AreaName);
    }

    @NonNull
    @Override
    public String toString() {
        return CommunityName;
    }

    //region GETTERS AND SETTERS
    public int getCommunityId() {
        return CommunityId;
    }

    public void setCommunityId(int communityId) {
        CommunityId = communityId;
    }

    public int getAreaId() {
        return AreaId;
    }

    public void setAreaId(int areaId) {
        AreaId = areaId;
    }

    public String getCommunityName() {
        return CommunityName;
    }

    public void setCommunityName(String communityName) {
        CommunityName = communityName;
    }

    public int getCityId1() {
        return CityId1;
    }

    public void setCityId1(int cityId1) {
        CityId1 = cityId1;
    }

    public String getCityName1() {
        return CityName1;
    }

    public void setCityName1(String cityName1) {
        CityName1 = cityName1;
    }

    public int getCityId2() {
        return CityId2;
    }

    public void setCityId2(int cityId2) {
        CityId2 = cityId2;
    }

    public String getCityName2() {
        return CityName2;
    }

    public void setCityName2(String cityName2) {
        CityName2 = cityName2;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getAdditional() {
        return Additional;
    }

    public void setAdditional(String additional) {
        Additional = additional;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }

    //endregion
}
