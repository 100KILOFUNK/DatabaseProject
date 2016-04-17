import java.sql.Date;

	public class reservation{
		private int r_id;
		private int cabinNr;
		private long civicNr;
		private int groupSize;
		private float cabinPrice;
		private float eqPrice;
		private Date startDate;
		private Date endDate;
		public reservation(int r_id, int cabinNr, long civicNr, int groupSize,
				float cabinPrice, float eqPrice, Date startDate, Date endDate) {
			this.r_id = r_id;
			this.cabinNr = cabinNr;
			this.civicNr = civicNr;
			this.groupSize = groupSize;
			this.cabinPrice = cabinPrice;
			this.eqPrice = eqPrice;
			this.startDate = startDate;
			this.endDate = endDate;
		}
		public int getR_id() {
			return r_id;
		}
		public void setR_id(int r_id) {
			this.r_id = r_id;
		}
		public int getCabinNr() {
			return cabinNr;
		}
		public void setCabinNr(int cabinNr) {
			this.cabinNr = cabinNr;
		}
		public long getCivicNr() {
			return civicNr;
		}
		public void setCivicNr(long civicNr) {
			this.civicNr = civicNr;
		}
		public int getGroupSize() {
			return groupSize;
		}
		public void setGroupSize(int groupSize) {
			this.groupSize = groupSize;
		}
		public float getCabinPrice() {
			return cabinPrice;
		}
		public void setCabinPrice(float cabinPrice) {
			this.cabinPrice = cabinPrice;
		}
		public float getEqPrice() {
			return eqPrice;
		}
		public void setEqPrice(float eqPrice) {
			this.eqPrice = eqPrice;
		}
		public Date getStartDate() {
			return startDate;
		}
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
		public Date getEndDate() {
			return endDate;
		}
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
		
		
	}
	