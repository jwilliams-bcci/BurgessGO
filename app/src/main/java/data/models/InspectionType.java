package data.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class InspectionType implements Parcelable {
    private int InspectionTypeId;
    private String TypeName;

    public InspectionType(){}

    public InspectionType(int inspectionTypeId, String typeName) {
        InspectionTypeId = inspectionTypeId;
        TypeName = typeName;
    }

    protected InspectionType(Parcel in) {
        InspectionTypeId = in.readInt();
        TypeName = in.readString();
    }

    public static final Creator<InspectionType> CREATOR = new Creator<InspectionType>() {
        @Override
        public InspectionType createFromParcel(Parcel in) {
            return new InspectionType(in);
        }

        @Override
        public InspectionType[] newArray(int size) {
            return new InspectionType[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(InspectionTypeId);
        parcel.writeString(TypeName);
    }

    @NonNull
    @Override
    public String toString() {
        return TypeName;
    }

    //region GETTERS AND SETTERS
    public int getInspectionTypeId() {
        return InspectionTypeId;
    }

    public void setInspectionTypeId(int inspectionTypeId) {
        InspectionTypeId = inspectionTypeId;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }
    //endregion
}
