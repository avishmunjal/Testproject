
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CalculateLoyaltyPoints {
	static List<CustomerDailyTxnDetails> daily_trxn_list;
	static ExistingCustomer customer = new ExistingCustomer();
	static Map<Integer, CustomerReportDetails> customerReport = new HashMap<Integer, CustomerReportDetails>();
	public static void main(String args[]){
		getTransactionalDetailofToday();

		for(Iterator<CustomerDailyTxnDetails> trxn = daily_trxn_list.iterator(); trxn.hasNext();){
			CustomerDailyTxnDetails customer_trxn_details = (CustomerDailyTxnDetails) trxn.next();
			String loyalty_card_no = customer_trxn_details.getLoyalty_card_no()+"";
			int total_loyalty_points = customer.getLoyaltyPoints();
			String cus_name = customer_trxn_details.getName();
			String cus_email = customer_trxn_details.getEmail();
			String cus_class = customer_trxn_details.getCurrentClass();

			if(loyalty_card_no!=null){
				if(isExistingCustomer(Integer.parseInt(loyalty_card_no))){
					if(cus_class.equals(Constants.GOLD_STATUS)){
						total_loyalty_points += (customer_trxn_details.getPurchase_amt()/500)*25; 
					}
					if(cus_class.equals(Constants.SILVER_STATUS)){
						total_loyalty_points += (customer_trxn_details.getPurchase_amt()/500)*2;
						cus_class = (customer.getCustomerYearlyPurchaseAmt() + customer_trxn_details.getPurchase_amt() > Constants.GOLD_LIMIT)? Constants.GOLD_STATUS:Constants.SILVER_STATUS;
						//updating existing customer class after the transaction happened today
						if(cus_class.equalsIgnoreCase(Constants.GOLD_STATUS))
							customer_trxn_details.setCurrentClass(Constants.GOLD_STATUS);
						else
							customer_trxn_details.setCurrentClass(Constants.SILVER_STATUS);
					}
					if(cus_class.equals(Constants.NORMAL_STATUS)){
						total_loyalty_points += customer_trxn_details.getPurchase_amt()*0.01;
						if((customer.getCustomerYearlyPurchaseAmt() + customer_trxn_details.getPurchase_amt() > Constants.SILVER_LIMIT) && (customer.getCustomerYearlyPurchaseAmt() + customer_trxn_details.getPurchase_amt() < Constants.GOLD_LIMIT)){
							cus_class = Constants.SILVER_STATUS;
							customer_trxn_details.setCurrentClass(Constants.SILVER_STATUS);
						}
						if(customer.getCustomerYearlyPurchaseAmt() + customer_trxn_details.getPurchase_amt() > Constants.GOLD_LIMIT){
							cus_class = Constants.GOLD_STATUS;
							customer_trxn_details.setCurrentClass(Constants.GOLD_STATUS);
						}
					}
					
					//updating customer details like yearlypurchasedAmount,Currrent class etc.
					updateExistingCustomer(total_loyalty_points, cus_email, cus_class,cus_name,customer_trxn_details.getPurchase_amt());
					
					//this customer report will be printed finally as output with all modified details
					customerReport.put(Integer.parseInt(loyalty_card_no), new CustomerReportDetails(cus_name, cus_email, cus_class, total_loyalty_points,customer_trxn_details.getPurchase_date(),customer_trxn_details.getTxn_id(),customer_trxn_details.getPurchase_amt()));

				}
				else{
					//setting initial values for new customer(Loyalty card number which doesn't exists in our database)
					customer.setLoyaltyPoints(Constants.NEW_CUSTOMER_LOYALTY_POINTS);
					customer.setCustomerYearlyPurchaseAmt(0);
					customer.setCustomerClass(Constants.NORMAL_STATUS);
				}
			}
			else {
				//Customers who has not provided loyalty card number or not enrolled for scheme
				System.out.println("Customer Name:"+customer_trxn_details.getName());
				System.out.println("Customer Email:"+customer_trxn_details.getEmail());
				System.out.println("Not Enrolled for Loyalty Points");
				System.out.println("Not Enrolled for Customer Class");
				System.out.println("No Transactions done yet");
			}

			//printing customer report as final output
			for(Iterator<Entry<Integer, CustomerReportDetails>> i = customerReport.entrySet().iterator(); i.hasNext();) {

				Map.Entry<Integer, CustomerReportDetails> printCustomerDetail = (Map.Entry<Integer, CustomerReportDetails> ) i.next();
				CustomerReportDetails sampleCase = printCustomerDetail.getValue();

				System.out.println("Customer Name: "+ sampleCase.getCus_name());
				System.out.println("Customer Email: "+sampleCase.getCus_email());
				System.out.println("Loyalty Points: "+sampleCase.getTotal_loyalty_points());
				System.out.println("Customer Class: "+sampleCase.getCus_class());
				System.out.println("Transactions: "+sampleCase.getTrxnDate()+"(Date time)"+" "+sampleCase.getTrxnId()+"(TrxnID)"+" "+sampleCase.getTrxn_amount()+"(TrxnAmount)"+" "+sampleCase.getTotal_loyalty_points()+"(Loyalty Points)");

			}

		}
	}

	private static void updateExistingCustomer(int total_loyalty_points,
			String cus_email, String cus_class, String cus_name, int purchasedAmount) {
		//updating customer details in database
		customer.setLoyaltyPoints(total_loyalty_points);
		customer.setCustomerClass(cus_class);
		customer.setCustomerEmail(cus_email);
		customer.setCustomerName(cus_name);
		customer.setCustomerYearlyPurchaseAmt(customer.getCustomerYearlyPurchaseAmt()+purchasedAmount);
	}

	private static boolean isExistingCustomer(int loyalty_card_no) {
		//check the database for the customer existence by the Primary key as Loyalty card no.,If it exists then it will return true
		//As of now ,I am taking this as existing customer case.
		return true;
	}

	//Mocking the data to avoid the creation of persistence class as given in the problem
	public static void getTransactionalDetailofToday(){
		daily_trxn_list = new ArrayList<CustomerDailyTxnDetails>();

		daily_trxn_list.add(new CustomerDailyTxnDetails("Abhay", "abhay@demo.com",11001, 7402, new Date(), 2348723,Constants.NORMAL_STATUS));
	}
}



