package RequestBuilder;

public class FaxRequestBuilder {

	public String filename;
	public boolean CoverPageEnabled;
    public String CoverPageName;
    public String FaxRecipient;
    public int FaxNumber;
    
    
    public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public boolean isCoverPageEnabled() {
		return CoverPageEnabled;
	}
	public void setCoverPageEnabled(boolean coverPageEnabled) {
		CoverPageEnabled = coverPageEnabled;
	}
	public String getCoverPageName() {
		return CoverPageName;
	}
	public void setCoverPageName(String coverPageName) {
		CoverPageName = coverPageName;
	}
	public String getFaxRecipient() {
		return FaxRecipient;
	}
	public void setFaxRecipient(String faxRecipient) {
		FaxRecipient = faxRecipient;
	}
	public int getFaxNumber() {
		return FaxNumber;
	}
	public void setFaxNumber(int faxNumber) {
		FaxNumber = faxNumber;
	}
	
   
}
