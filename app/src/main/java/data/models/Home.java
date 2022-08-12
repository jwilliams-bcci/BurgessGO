package data.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Home implements Parcelable {
    private int LocationId;
    private String Community;
    private String Address;
    private List<BuilderPersonnel> BuilderPersonnelList;

    public Home(){}

    public Home(int locationId, String community, String address, List<BuilderPersonnel> builderPersonnelList) {
        LocationId = locationId;
        Community = community;
        Address = address;
        BuilderPersonnelList = builderPersonnelList;
    }

    protected Home(Parcel in) {
        LocationId = in.readInt();
        Community = in.readString();
        Address = in.readString();
    }

    public static final Creator<Home> CREATOR = new Creator<Home>() {
        @Override
        public Home createFromParcel(Parcel in) {
            return new Home(in);
        }

        @Override
        public Home[] newArray(int size) {
            return new Home[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(LocationId);
        parcel.writeString(Community);
        parcel.writeString(Address);
    }

    //region GETTERS AND SETTERS
    public int getLocationId() {
        return LocationId;
    }

    public void setLocationId(int locationId) {
        LocationId = locationId;
    }

    public String getCommunity() {
        return Community;
    }

    public void setCommunity(String community) {
        Community = community;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public List<BuilderPersonnel> getBuilderPersonnelList() {
        return BuilderPersonnelList;
    }

    public void setBuilderPersonnelList(List<BuilderPersonnel> builderPersonnelList) {
        BuilderPersonnelList = builderPersonnelList;
    }
    //endregion
}