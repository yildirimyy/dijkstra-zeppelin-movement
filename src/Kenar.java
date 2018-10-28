
public class Kenar {
	
	private double yataykm;
	private double yolKm;
	private int tan;
	private int connectedId;
	private boolean isUse;
	
	public Kenar(double yataykm, double yolKm, int tan ,int connId, boolean isUse) {
		this.setKm(yataykm);
		this.setYolKm(yolKm);
		this.setTan(tan);
		this.setConnectedId(connId);
		this.setUse(isUse);
	}

	public double getYatayKm() {
		return yataykm;
	}

	public void setKm(double km) {
		this.yataykm = km;
	}

	public int getTan() {
		return tan;
	}

	public void setTan(int tan) {
		this.tan = tan;
	}

	public int getConnectedId() {
		return connectedId;
	}

	public void setConnectedId(int connectedId) {
		this.connectedId = connectedId;
	}

	public double getYolKm() {
		return yolKm;
	}

	public void setYolKm(double yolKm) {
		this.yolKm = yolKm;
	}

	public boolean isUse() {
		return isUse;
	}

	public void setUse(boolean isUse) {
		this.isUse = isUse;
	}

}
