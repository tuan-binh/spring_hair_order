package rikkei.academy.model;

public class Reviews {
	private int id;
	private String comment;
	private int rate;
	private Orders order;
	
	public Reviews() {
	}
	
	public Reviews(int id, String comment, int rate, Orders order) {
		this.id = id;
		this.comment = comment;
		this.rate = rate;
		this.order = order;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public int getRate() {
		return rate;
	}
	
	public void setRate(int rate) {
		this.rate = rate;
	}
	
	public Orders getOrder() {
		return order;
	}
	
	public void setOrder(Orders order) {
		this.order = order;
	}
}
