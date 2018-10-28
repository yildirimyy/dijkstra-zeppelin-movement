
public class Zeplin {

	// sabitler
	static final int yerdenYukseklik = 50;
	private static final float minEgimDerecesi = 30;
	@SuppressWarnings("unused")
	public static final int yuzKmMaliyet = 1000;
	public static final int yolcuFiyat = 100;
	public static final int maksYolcu = 50;
	@SuppressWarnings("unused")
	public static final int minYolcu = 5;
	@SuppressWarnings("unused")
	private static final int birKisiDerece = 1;

	int yolcuSayisi = 50;
	float egimDerecesi = 30;

	public Zeplin(int yolcuSayisi) {
		this.yolcuSayisi = yolcuSayisi;
		this.egimDerecesi = EgimDereceHesapla(yolcuSayisi);
	}

	public float EgimDereceHesapla(int yolcusayisi) {

		if(yolcusayisi > Zeplin.maksYolcu) {
			return -1;
		}
		
		float result = 0;
		
		//50 (maks) yolcu için 30 (min) derece
		result = (maksYolcu - yolcusayisi) + minEgimDerecesi;

		return result;
	}
	
	public float getEgimDerecesi() {
		return egimDerecesi;
	}

}
