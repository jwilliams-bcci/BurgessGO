package data.models;

import java.util.Date;

public class Inspection {
    public int InspectionId;
    public Date InspectionDate;
    public String InspectionStatus;
    public String CommunityName;
    public String StreetNumber;
    public String StreetName;
    public String AddressToDisplay;
    public String AddressToDisplay2;
    public String TypeName;
    public String TypeNameDisplay;
    public int InspectionNumber;
    public String InspectorName;
    public String Phone;
    public boolean InspectionMissed;
    public int CommunityId;
    public int StreetId;
    public String City;

    public Inspection(){}

    public Inspection(int inspectionId, Date inspectionDate, String inspectionStatus, String communityName, String streetNumber,
                      String streetName, String addressToDisplay, String addressToDisplay2, String typeName, String typeNameDisplay,
                      int inspectionNumber, String inspectorName, String phone, boolean inspectionMissed, int communityId, int streetId, String city) {
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
}
