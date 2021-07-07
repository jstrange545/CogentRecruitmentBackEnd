package cogent.recruitment.entities;

public enum Status {
	UNDER_REVIEW("Your application is under review"),
	FIRST_STAGE("Your application is in the first stage"),
	BEHAVIORAL("You are in the behavioral round of interviews"),
	TECHNICAL("You are in the technical round of interviews"),
	HIRED("You are hired!"),
	DECLINED("Your application has been declined");

	private String message;

	private Status(String m) {
		message = m;
	}

	public String getMsgStatus() {
		return message;
	}
}
