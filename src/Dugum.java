import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class Dugum {

	private int id;
	//maliyet aslinda km
	private double maliyet = Double.MAX_VALUE;

	public double latitude;
	public double longitute;
	public int altitude;
	
	//gorsel cizim icin pixel
	public int x;
	public int y;

	public Vector<Kenar> kenarlar = new Vector<Kenar>();

	// komsuId - maliyet km
	public Map<Integer, Double> kenarMap = new HashMap<>();

	//dugumun yolu
	private LinkedList<Dugum> dugumYolu = new LinkedList<>();

	// gidilebilir komşular
	public Set<Dugum> komsular = new HashSet<Dugum>();

	// tüm komşu idler dosyadan okurken doldurmak icin
	public Vector<Integer> tumKomsuIds = new Vector<Integer>();

	public Dugum(int id) {
		this.id = id;
	}

	public void addKomsuId(int komsuId) {
		this.tumKomsuIds.addElement(komsuId);
	}

	public void addKomsu(Dugum komsuDugum) {
		komsular.add(komsuDugum);
	}

	public void addKenar(double yataykm, double yolKm, int tan, int connId, boolean isUse) {

		Kenar yeniKenar = new Kenar(yataykm, yolKm, tan, connId, isUse);

		this.kenarlar.addElement(yeniKenar);

		//kenar kullanilabir ile kenar map e ekle
		if (isUse) {
			this.kenarMap.put(connId, yolKm);
		}

	}

	public void setKonum(double lat_t, double long_t, int al_t) {
		this.latitude = lat_t;
		this.longitute = long_t;
		this.altitude = al_t;
	}

	public void setMaliyet(double maliyet) {
		this.maliyet = maliyet;
	}

	public List<Dugum> getDugumYolu() {
		return dugumYolu;
	}
	
	// komsuId - maliyet km
	public Map<Integer, Double> getKenarMap() {
		return this.kenarMap;
	}
	
	public Set<Dugum> getKomsular() {
		return komsular;
	}

	public Dugum getKomsu(int komsuId) {

		Dugum komsuDugum = null;
		for (Dugum item : komsular) {
			if (item.getId() == komsuId) {
				komsuDugum = item;
				break;
			}
		}

		return komsuDugum;
	}

	public void setDugumYolu(LinkedList<Dugum> dugumYolu) {
		this.dugumYolu = dugumYolu;
	}

	//maliyet aslinda km bilgisi algoritma hesabinda
	public double getMaliyet() {
		return maliyet;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setXandY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getId() {
		return id;
	}
	
	public Kenar getKenarByConnId(int komsuId) {
		
		Kenar resultKenar = null;
		
		for (Kenar item : this.kenarlar) {
			
			if(item.getConnectedId() == komsuId) {
				resultKenar = item;
			}
		}
		
		return resultKenar;
	}
	
	public Dugum getCopyDugum() {
		
		Dugum copyDugum = new Dugum(this.id);
		
		// olusan dugume komsuları yerlestir		
		for (Integer komsuId : this.tumKomsuIds) {
			copyDugum.addKomsuId(komsuId);
		}
		
		copyDugum.setKonum(this.latitude, this.longitute, this.altitude);
		
		copyDugum.setXandY(this.x, this.y);
				
		
		return copyDugum;		
	}

	
	
	public Vector<Kenar> getKenarlar() {
		return kenarlar;
	}

	
	
	
	
	
	
	
}
