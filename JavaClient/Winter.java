
public class Winter {

	private int r_id;
	private String type;
	private float shoeSize;
	private int skiSize;
	private int poleSize;
	private int headSize;
	private float price;
	public Winter(int r_id, String type, float shoeSize, int skiSize,
			int poleSize, int headSize, float price) {
		super();
		this.r_id = r_id;
		this.type = type;
		this.shoeSize = shoeSize;
		this.skiSize = skiSize;
		this.poleSize = poleSize;
		this.headSize = headSize;
		this.price = price;
	}
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
	public float getShoeSize() {
		return shoeSize;
	}
	public void setShoeSize(float shoeSize) {
		this.shoeSize = shoeSize;
	}
	public int getSkiSize() {
		return skiSize;
	}
	public void setSkiSize(int skiSize) {
		this.skiSize = skiSize;
	}
	public int getPoleSize() {
		return poleSize;
	}
	public void setPoleSize(int poleSize) {
		this.poleSize = poleSize;
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
}
