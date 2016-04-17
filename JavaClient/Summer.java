
public class Summer {

	private int r_id;
	private String type;
	private int length;
	private int headSize;
	private float price;
	public int getR_id() {
		return r_id;
	}
	public void setR_id(int r_id) {
		this.r_id = r_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getHeadSize() {
		return headSize;
	}
	public void setHeadSize(int headSize) {
		this.headSize = headSize;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Summer(int r_id, String type, int length, int headSize, float price) {
		super();
		this.r_id = r_id;
		this.type = type;
		this.length = length;
		this.headSize = headSize;
		this.price = price;
	}
	
}
