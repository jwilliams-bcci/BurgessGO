package data.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.OffsetDateTime;

public class NonPassedInspection implements Parcelable {
    private int InspectionId;
    private OffsetDateTime InspectionDate;
    private String InspectionStatus;
    private String CommunityName;
    private String StreetNumber;
    private String StreetName;
    private String TypeName;
    private int InspectionNumber;
    private String InspectionAddress;
    private int Items;
    private int DaysOld;
    private String InspectedBy;

    public NonPassedInspection(){}

    public NonPassedInspection(int inspectionId, OffsetDateTime inspectionDate, String inspectionStatus, String communityName, String streetNumber,
                               String streetName, String typeName, int inspectionNumber, String inspectionAddress, int items, int daysOld, String inspectedBy) {
        InspectionId = inspectionId;
        InspectionDate = inspectionDate;
        InspectionStatus = inspectionStatus;
        CommunityName = communityName;
        StreetNumber = streetNumber;
        StreetName = streetName;
        TypeName = typeName;
        InspectionNumber = inspectionNumber;
        InspectionAddress = inspectionAddress;
        Items = items;
        DaysOld = daysOld;
        InspectedBy = inspectedBy;
    }

    protected NonPassedInspection(Parcel in) {
        InspectionId = in.readInt();
        InspectionDate = OffsetDateTime.parse(in.readString());
        InspectionStatus = in.readString();
        CommunityName = in.readString();
        StreetNumber = in.readString();
        StreetName = in.readString();
        TypeName = in.readString();
        InspectionNumber = in.readInt();
        InspectionAddress = in.readString();
        Items = in.readInt();
        DaysOld = in.readInt();
        InspectedBy = in.readString();
    }

    public static final Creator<NonPassedInspection> CREATOR = new Creator<NonPassedInspection>() {
        @Override
        public NonPassedInspection createFromParcel(Parcel in) {
            return new NonPassedInspection(in);
        }

        @Override
        public NonPassedInspection[] newArray(int size) {
            return new NonPassedInspection[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(InspectionId);
        parcel.writeString(InspectionDate.toString());
        parcel.writeString(InspectionStatus);
        parcel.writeString(CommunityName);
        parcel.writeString(StreetNumber);
        parcel.writeString(StreetName);
        parcel.writeString(TypeName);
        parcel.writeInt(InspectionNumber);
        parcel.writeString(InspectionAddress);
        parcel.writeInt(Items);
        parcel.writeInt(DaysOld);
        parcel.writeString(InspectedBy);
    }

    //region GETTERS AND SETTERS
    public int getInspectionId() {
        return InspectionId;
    }

    public void setInspectionId(int inspectionId) {
        InspectionId = inspectionId;
    }

    public OffsetDateTime getInspectionDate() {
        return InspectionDate;
    }

    public void setInspectionDate(OffsetDateTime inspectionDate) {
        InspectionDate = inspectionDate;
    }

    public String getInspectionStatus() {
        return InspectionStatus;
    }

    public void setInspectionStatus(String inspectionStatus) {
        InspectionStatus = inspectionStatus;
    }

    public String getCommunityName() {
        return CommunityName;
    }

    public void setCommunityName(String communityName) {
        CommunityName = communityName;
    }

    public String getStreetNumber() {
        return StreetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        StreetNumber = streetNumber;
    }

    public String getStreetName() {
        return StreetName;
    }

    public void setStreetName(String streetName) {
        StreetName = streetName;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public int getInspectionNumber() {
        return InspectionNumber;
    }

    public void setInspectionNumber(int inspectionNumber) {
        InspectionNumber = inspectionNumber;
    }

    public String getInspectionAddress() {
        return InspectionAddress;
    }

    public void setInspectionAddress(String inspectionAddress) {
        InspectionAddress = inspectionAddress;
    }

    public int getItems() {
        return Items;
    }

    public void setItems(int items) {
        Items = items;
    }

    public int getDaysOld() {
        return DaysOld;
    }

    public void setDaysOld(int daysOld) {
        DaysOld = daysOld;
    }

    public String getInspectedBy() {
        return InspectedBy;
    }

    public void setInspectedBy(String inspectedBy) {
        InspectedBy = inspectedBy;
    }
    //endregion
}
