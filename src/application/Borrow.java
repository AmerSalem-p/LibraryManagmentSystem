package application;

import java.util.Date;

public class Borrow {
	private int SID;
	private long ISBN;
	private Date BorrowDate;
	private Date RetDate;

	public Borrow(int sID, long iSBN, Date borrowDate, Date retDate) {
		super();
		SID = sID;
		ISBN = iSBN;
		BorrowDate = borrowDate;
		RetDate = retDate;
	}

	public Date getBorrowDate() {
		return BorrowDate;
	}

	public void setBorrowDate(Date borrowDate) {
		BorrowDate = borrowDate;
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

	public Date getRetDate() {
		return RetDate;
	}

	public void setRetDate(Date retDate) {
		RetDate = retDate;
	}

}
