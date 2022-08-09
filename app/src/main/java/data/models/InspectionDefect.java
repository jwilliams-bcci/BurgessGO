package data.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.OffsetDateTime;

public class InspectionDefect implements Parcelable {
    private int RowId;
    private int LocationId;
    private int InspectionId;
    private int InspectionNumber;
    private OffsetDateTime InspectionDate;
    private String DefectCategoryDisplayName;
    private String DefectItemDescription;
    private String DeviationText;
    private String ColumnHeader1;
    private String ColumnHeader2;
    private int SourceId;
    private int OrderBy;

    public InspectionDefect(){}

    public InspectionDefect(int rowId, int locationId, int inspectionId, int inspectionNumber, OffsetDateTime inspectionDate, String defectCategoryDisplayName,
                            String defectItemDescription, String deviationText, String columnHeader1, String columnHeader2, int sourceId, int orderBy) {
        RowId = rowId;
        LocationId = locationId;
        InspectionId = inspectionId;
        InspectionNumber = inspectionNumber;
        InspectionDate = inspectionDate;
        DefectCategoryDisplayName = defectCategoryDisplayName;
        DefectItemDescription = defectItemDescription;
        DeviationText = deviationText;
        ColumnHeader1 = columnHeader1;
        ColumnHeader2 = columnHeader2;
        SourceId = sourceId;
        OrderBy = orderBy;
    }

    protected InspectionDefect(Parcel in) {
        RowId = in.readInt();
        LocationId = in.readInt();
        InspectionId = in.readInt();
        InspectionNumber = in.readInt();
        InspectionDate = OffsetDateTime.parse(in.readString());
        DefectCategoryDisplayName = in.readString();
        DefectItemDescription = in.readString();
        DeviationText = in.readString();
        ColumnHeader1 = in.readString();
        ColumnHeader2 = in.readString();
        SourceId = in.readInt();
        OrderBy = in.readInt();
    }

    public static final Creator<InspectionDefect> CREATOR = new Creator<InspectionDefect>() {
        @Override
        public InspectionDefect createFromParcel(Parcel in) {
            return new InspectionDefect(in);
        }

        @Override
        public InspectionDefect[] newArray(int size) {
            return new InspectionDefect[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(RowId);
        parcel.writeInt(LocationId);
        parcel.writeInt(InspectionId);
        parcel.writeInt(InspectionNumber);
        parcel.writeString(InspectionDate.toString());
        parcel.writeString(DefectCategoryDisplayName);
        parcel.writeString(DefectItemDescription);
        parcel.writeString(DeviationText);
        parcel.writeString(ColumnHeader1);
        parcel.writeString(ColumnHeader2);
        parcel.writeInt(SourceId);
        parcel.writeInt(OrderBy);
    }

    //region GETTERS AND SETTERS

    public int getRowId() {
        return RowId;
    }

    public void setRowId(int rowId) {
        RowId = rowId;
    }

    public int getLocationId() {
        return LocationId;
    }

    public void setLocationId(int locationId) {
        LocationId = locationId;
    }

    public int getInspectionId() {
        return InspectionId;
    }

    public void setInspectionId(int inspectionId) {
        InspectionId = inspectionId;
    }

    public int getInspectionNumber() {
        return InspectionNumber;
    }

    public void setInspectionNumber(int inspectionNumber) {
        InspectionNumber = inspectionNumber;
    }

    public OffsetDateTime getInspectionDate() {
        return InspectionDate;
    }

    public void setInspectionDate(OffsetDateTime inspectionDate) {
        InspectionDate = inspectionDate;
    }

    public String getDefectCategoryDisplayName() {
        return DefectCategoryDisplayName;
    }

    public void setDefectCategoryDisplayName(String defectCategoryDisplayName) {
        DefectCategoryDisplayName = defectCategoryDisplayName;
    }

    public String getDefectItemDescription() {
        return DefectItemDescription;
    }

    public void setDefectItemDescription(String defectItemDescription) {
        DefectItemDescription = defectItemDescription;
    }

    public String getDeviationText() {
        return DeviationText;
    }

    public void setDeviationText(String deviationText) {
        DeviationText = deviationText;
    }

    public String getColumnHeader1() {
        return ColumnHeader1;
    }

    public void setColumnHeader1(String columnHeader1) {
        ColumnHeader1 = columnHeader1;
    }

    public String getColumnHeader2() {
        return ColumnHeader2;
    }

    public void setColumnHeader2(String columnHeader2) {
        ColumnHeader2 = columnHeader2;
    }

    public int getSourceId() {
        return SourceId;
    }

    public void setSourceId(int sourceId) {
        SourceId = sourceId;
    }

    public int getOrderBy() {
        return OrderBy;
    }

    public void setOrderBy(int orderBy) {
        OrderBy = orderBy;
    }

    //endregion
}
