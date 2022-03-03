package application;

public class Fine {
	private int SID;
	private long ISBN;
	private double Amount;

	public Fine(int sID, long iSBN, double amount) {
		super();
		SID = sID;
		ISBN = iSBN;
		Amount = amount;
	}

	public int getSID() {
		return SID;
	}

	public void setSID(int sID) {
		SID = sID;
	}

	public long getISBN() {
		return ISBN;
	}

	public void setISBN(long iSBN) {
		ISBN = iSBN;
	}

	public double getAmount() {
		return Amount;
	}

	public void setAmount(double amount) {
		Amount = amount;
	}
}
