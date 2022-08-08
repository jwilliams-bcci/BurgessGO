package data.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.OffsetDateTime;
import java.util.Date;

public class Inspection implements Parcelable {
    private int InspectionId;
    private OffsetDateTime InspectionDate;
    private String InspectionStatus;
    private String CommunityName;
    private String StreetNumber;
    private String StreetName;
    private String AddressToDisplay;
    private String AddressToDisplay2;
    private String TypeName;
    private String TypeNameDisplay;
    private int InspectionNumber;
    private String InspectorName;
    private String Phone;
    private int InspectionMissed;
    private int CommunityId;
    private int StreetId;
    private String City;

    public Inspection(){}

    public Inspection(int inspectionId, OffsetDateTime inspectionDate, String inspectionStatus, String communityName, String streetNumber,
                      String streetName, String addressToDisplay, String addressToDisplay2, String typeName, String typeNameDisplay,
                      int inspectionNumber, String inspectorName, String phone, int inspectionMissed, int communityId, int streetId, String city) {
        InspectionId = inspectionId;
        InspectionDate = inspectionDate;
        InspectionStatus = inspectionStatus;
        CommunityName = communityName;
        StreetNumber = streetNumber;
        StreetName = streetName;
        AddressToDisplay = addressToDisplay;
        AddressToDisplay2 = addressToDisplay2;
        TypeName = typeName;
        TypeNameDisplay = typeNameDisplay;
        InspectionNumber = inspectionNumber;
        InspectorName = inspectorName;
        Phone = phone;
        InspectionMissed = inspectionMissed;
        CommunityId = communityId;
        StreetId = streetId;
        City = city;
    }

    protected Inspection(Parcel in) {
        InspectionId = in.readInt();
        InspectionDate = OffsetDateTime.parse(in.readString());
        InspectionStatus = in.readString();
        CommunityName = in.readString();
        StreetNumber = in.readString();
        StreetName = in.readString();
        AddressToDisplay = in.readString();
        AddressToDisplay2 = in.readString();
        TypeName = in.readString();
        TypeNameDisplay = in.readString();
        InspectionNumber = in.readInt();
        InspectorName = in.readString();
        Phone = in.readString();
        InspectionMissed = in.readInt();
        CommunityId = in.readInt();
        StreetId = in.readInt();
        City = in.readString();
    }

    public static final Creator<Inspection> CREATOR = new Creator<Inspection>() {
        @Override
        public Inspection createFromParcel(Parcel in) {
            return new Inspection(in);
        }

        @Override
        public Inspection[] newArray(int size) {
            return new Inspection[size];
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
        parcel.writeString(AddressToDisplay);
        parcel.writeString(AddressToDisplay2);
        parcel.writeString(TypeName);
        parcel.writeString(TypeNameDisplay);
        parcel.writeInt(InspectionNumber);
        parcel.writeString(InspectorName);
        parcel.writeString(Phone);
        parcel.writeInt(InspectionMissed);
        parcel.writeInt(CommunityId);
        parcel.writeInt(StreetId);
        parcel.writeString(City);
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

    public String getAddressToDisplay() {
        return AddressToDisplay;
    }
    public void setAddressToDisplay(String addressToDisplay) {
        AddressToDisplay = addressToDisplay;
    }

    public String getAddressToDisplay2() {
        return AddressToDisplay2;
    }
    public void setAddressToDisplay2(String addressToDisplay2) {
        AddressToDisplay2 = addressToDisplay2;
    }

    public String getTypeName() {
        return TypeName;
    }
    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public String getTypeNameDisplay() {
        return TypeNameDisplay;
    }
    public void setTypeNameDisplay(String typeNameDisplay) {
        TypeNameDisplay = typeNameDisplay;
    }

    public int getInspectionNumber() {
        return InspectionNumber;
    }
    public void setInspectionNumber(int inspectionNumber) {
        InspectionNumber = inspectionNumber;
    }

    public String getInspectorName() {
        return InspectorName;
    }
    public void setInspectorName(String inspectorName) {
        InspectorName = inspectorName;
    }

    public String getPhone() {
        return Phone;
    }
    public void setPhone(String phone) {
        Phone = phone;
    }

    public int isInspectionMissed() {
        return InspectionMissed;
    }
    public void setInspectionMissed(int inspectionMissed) {
        InspectionMissed = inspectionMissed;
    }

    public int getCommunityId() {
        return CommunityId;
    }
    public void setCommunityId(int communityId) {
        CommunityId = communityId;
    }

    public int getStreetId() {
        return StreetId;
    }
    public void setStreetId(int streetId) {
        StreetId = streetId;
    }

    public String getCity() {
        return City;
    }
    public void setCity(String city) {
        City = city;
    }
    //endregion
}
