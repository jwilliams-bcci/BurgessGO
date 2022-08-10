package data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ActiveLocation implements Parcelable {
    private int LocationId;
    private String Community;
    private String Address;
    private int InspectionCount;
    private int InspectionCountRemaining;
    private String StreetName;
    private int MAXBuilderPersonnelIDRequestingAccess;

    public ActiveLocation(){}

    public ActiveLocation(int locationId, String community, String address, int inspectionCount, int inspectionCountRemaining,
                          String streetName, int MAXBuilderPersonnelIDRequestingAccess) {
        LocationId = locationId;
        Community = community;
        Address = address;
        InspectionCount = inspectionCount;
        InspectionCountRemaining = inspectionCountRemaining;
        StreetName = streetName;
        this.MAXBuilderPersonnelIDRequestingAccess = MAXBuilderPersonnelIDRequestingAccess;
    }

    protected ActiveLocation(Parcel in) {
        LocationId = in.readInt();
        Community = in.readString();
        Address = in.readString();
        InspectionCount = in.readInt();
        InspectionCountRemaining = in.readInt();
        StreetName = in.readString();
        MAXBuilderPersonnelIDRequestingAccess = in.readInt();
    }

    public static final Creator<ActiveLocation> CREATOR = new Creator<ActiveLocation>() {
        @Override
        public ActiveLocation createFromParcel(Parcel in) {
            return new ActiveLocation(in);
        }

        @Override
        public ActiveLocation[] newArray(int size) {
            return new ActiveLocation[size];
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
        parcel.writeInt(InspectionCount);
        parcel.writeInt(InspectionCountRemaining);
        parcel.writeString(StreetName);
        parcel.writeInt(MAXBuilderPersonnelIDRequestingAccess);
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

    public int getInspectionCount() {
        return InspectionCount;
    }

    public void setInspectionCount(int inspectionCount) {
        InspectionCount = inspectionCount;
    }

    public int getInspectionCountRemaining() {
        return InspectionCountRemaining;
    }

    public void setInspectionCountRemaining(int inspectionCountRemaining) {
        InspectionCountRemaining = inspectionCountRemaining;
    }

    public String getStreetName() {
        return StreetName;
    }

    public void setStreetName(String streetName) {
        StreetName = streetName;
    }

    public int getMAXBuilderPersonnelIDRequestingAccess() {
        return MAXBuilderPersonnelIDRequestingAccess;
    }

    public void setMAXBuilderPersonnelIDRequestingAccess(int MAXBuilderPersonnelIDRequestingAccess) {
        this.MAXBuilderPersonnelIDRequestingAccess = MAXBuilderPersonnelIDRequestingAccess;
    }
    //endregion
}
