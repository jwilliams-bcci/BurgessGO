package data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class BuilderPersonnel implements Parcelable {
    private int BuilderPersonnelId;
    private String PersonnelName;
    private String Initials;
    private String Address1;
    private String Address2;

    public BuilderPersonnel(){}

    public BuilderPersonnel(int builderPersonnelId, String personnelName, String initials, String address1, String address2) {
        BuilderPersonnelId = builderPersonnelId;
        PersonnelName = personnelName;
        Initials = initials;
        Address1 = address1;
        Address2 = address2;
    }

    protected BuilderPersonnel(Parcel in) {
        BuilderPersonnelId = in.readInt();
        PersonnelName = in.readString();
        Initials = in.readString();
        Address1 = in.readString();
        Address2 = in.readString();
    }

    public static final Creator<BuilderPersonnel> CREATOR = new Creator<BuilderPersonnel>() {
        @Override
        public BuilderPersonnel createFromParcel(Parcel in) {
            return new BuilderPersonnel(in);
        }

        @Override
        public BuilderPersonnel[] newArray(int size) {
            return new BuilderPersonnel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(BuilderPersonnelId);
        parcel.writeString(PersonnelName);
        parcel.writeString(Initials);
        parcel.writeString(Address1);
        parcel.writeString(Address2);
    }

    //region GETTERS AND SETTERS
    public int getBuilderPersonnelId() {
        return BuilderPersonnelId;
    }

    public void setBuilderPersonnelId(int builderPersonnelId) {
        BuilderPersonnelId = builderPersonnelId;
    }

    public String getPersonnelName() {
        return PersonnelName;
    }

    public void setPersonnelName(String personnelName) {
        PersonnelName = personnelName;
    }

    public String getInitials() {
        return Initials;
    }

    public void setInitials(String initials) {
        Initials = initials;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String address2) {
        Address2 = address2;
    }
    //endregion
}
