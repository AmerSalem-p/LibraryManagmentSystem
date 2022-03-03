package application;

public class Book {
	private String Title;
	private String Author;
	private String Country;
	private String Category;
	private String Publisher;
	private Long ISBN;
	private int Edition;
	private int Year_Of_Production;
	private String Date_Of_Entry;

	public Book(String title, String author, String country, String category, String publisher, Long iSBN, int edition,
			int year_Of_Production, String date_Of_Entry) {
		super();
		Title = title;
		Author = author;
		Country = country;
		Category = category;
		Publisher = publisher;
		ISBN = iSBN;
		Edition = edition;
		Year_Of_Production = year_Of_Production;
		Date_Of_Entry = date_Of_Entry;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public String getPublisher() {
		return Publisher;
	}

	public void setPublisher(String publisher) {
		Publisher = publisher;
	}

	public Long getISBN() {
		return ISBN;
	}

	public void setISBN(Long iSBN) {
		ISBN = iSBN;
	}

	public int getEdition() {
		return Edition;
	}

	public void setEdition(int edition) {
		Edition = edition;
	}

	public int getYear_Of_Production() {
		return Year_Of_Production;
	}

	public void setYear_Of_Production(int year_Of_Production) {
		Year_Of_Production = year_Of_Production;
	}

	public String getDate_Of_Entry() {
		return Date_Of_Entry;
	}

	public void setDate_Of_Entry(String date_Of_Entry) {
		Date_Of_Entry = date_Of_Entry;
	}

}
