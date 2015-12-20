import java.util.Date;




class CustomerReportDetails{
	
	private String cus_name, cus_email, cus_class;
	private int total_loyalty_points,trxn_amount;
	private Date trxnDate;
	private long trxnId;
	public CustomerReportDetails(){
		
	}

	public CustomerReportDetails(String cus_name, String cus_email,
			String cus_class, int total_loyalty_points,Date trxnDate,long trxnId,int trxn_amount ) {
		this.cus_name=cus_name;
		this.cus_email=cus_email;
		this.cus_class=cus_class;
		this.total_loyalty_points=total_loyalty_points;
		this.trxnDate=trxnDate;
		this.trxnId=trxnId;
		this.trxn_amount=trxn_amount;
	}

	public String getCus_email() {
		return cus_email;
	}

	public void setCus_email(String cus_email) {
		this.cus_email = cus_email;
	}

	public String getCus_class() {
		return cus_class;
	}

	public void setCus_class(String cus_class) {
		this.cus_class = cus_class;
	}

	public String getCus_name() {
		return cus_name;
	}

	public void setCus_name(String cus_name) {
		this.cus_name = cus_name;
	}

	public int getTotal_loyalty_points() {
		return total_loyalty_points;
	}

	public void setTotal_loyalty_points(int total_loyalty_points) {
		this.total_loyalty_points = total_loyalty_points;
	}

	public int getTrxn_amount() {
		return trxn_amount;
	}

	public void setTrxn_amount(int trxn_amount) {
		this.trxn_amount = trxn_amount;
	}

	public Date getTrxnDate() {
		return trxnDate;
	}

	public void setTrxnDate(Date trxnDate) {
		this.trxnDate = trxnDate;
	}

	public long getTrxnId() {
		return trxnId;
	}

	public void setTrxnId(long trxnId) {
		this.trxnId = trxnId;
	}
}

