package patientIPD;

import java.io.Serializable;

public class IpdAppointmentObj implements Serializable {
	private static final long serialVersionUID = 1L;

	private int ipd_appointment_id;
    private String fullname;
    private String wardName;
    private String doctorFirstname;
    private String nurseFirstname;
    private String checkinDate;
    private String checkoutDate;
    private String status;

    // Constructors, getters, and setters

    public IpdAppointmentObj() {
    }

    public IpdAppointmentObj(int ipd_appointment_id,String fullname, String wardName, String doctorFirstname,
    String nurseFirstname,String checkinDate, String checkoutDate, String status) 
    {
    	this.ipd_appointment_id = ipd_appointment_id;
        this.fullname = fullname;
        this.wardName = wardName;
        this.doctorFirstname = doctorFirstname;
        this.nurseFirstname = nurseFirstname;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.status = status;
    }
    
    
    public int getIpd_appointment_id() {
		return ipd_appointment_id;
	}

	public void setIpd_appointment_id(int ipd_appointment_id) {
		this.ipd_appointment_id = ipd_appointment_id;
	}

	public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getDoctorFirstname() {
        return doctorFirstname;
    }

    public void setDoctorFirstname(String doctorFirstname) {
        this.doctorFirstname = doctorFirstname;
    }

    public String getNurseFirstname() {
        return nurseFirstname;
    }

    public void setNurseFirstname(String nurseFirstname) {
        this.nurseFirstname = nurseFirstname;
    }

    public String getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
