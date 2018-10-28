import java.util.Vector;

public class Islemler {
		
	//yol kullanilabilir mi kismi onemli buyuk kucuk
	public Vector<Dugum> KenarlariKomsulariOlustur_EgimHesapla(int baslangicId, int bitisId, Vector<Dugum> Graph, Zeplin zeplin){
		
		for (Dugum  item : Graph) {
			
			for (Integer intItem : item.tumKomsuIds) {
				
				Dugum komsuDugum = Graph.get(intItem.intValue());
				
				double yatayKm = LatLongHesabı(item.latitude, komsuDugum.latitude, item.longitute, komsuDugum.longitute);
				
				
				TanHipo tanHipo = TanYolHesapla(item, komsuDugum, yatayKm, baslangicId, bitisId);
				double tan = tanHipo.tan;
				int tanYuvarlanmis = (int) Math.round(tan);
				double hipotenus = tanHipo.hipo;
				
				//yolun kullanibilir olduguna bak
				//zeplin icerisindeki yolcu sayisina gore belirlenen 
				//egim ile kontrol et
				boolean yolKullanilabilirMi = zeplin.getEgimDerecesi() >= tanYuvarlanmis;
				
				item.addKenar(yatayKm, hipotenus, tanYuvarlanmis, komsuDugum.getId(), yolKullanilabilirMi);

				//yol kullanilabilir ise bu iki dugumu komsu olarak ekle
				if(yolKullanilabilirMi) {
					item.addKomsu(komsuDugum);
				}
				
			}
		}
		
		return Graph;
	}
	
	public TanHipo TanYolHesapla(Dugum dugum1, Dugum dugum2, double yatayKm, int baslangicId, int bitisId) {
		
		
		int rakimFark = 0;
		
		if((dugum1.getId() == baslangicId && dugum2.getId() == bitisId) || dugum2.getId() == baslangicId && dugum1.getId() == bitisId) {
		
			rakimFark = Math.abs((dugum1.altitude - dugum2.altitude));
		
		}else {

			//kontrol et kısalt
			if(dugum1.getId() == baslangicId ) {
				
				rakimFark = Math.abs(((dugum2.altitude + Zeplin.yerdenYukseklik) - dugum1.altitude));
			
			}else if( dugum2.getId() == baslangicId) {
				
				rakimFark = Math.abs(((dugum1.altitude + Zeplin.yerdenYukseklik) - dugum2.altitude));
			
			}else if(dugum1.getId() == bitisId ) {
				
				rakimFark = Math.abs((dugum2.altitude + Zeplin.yerdenYukseklik) - dugum1.altitude);
				
			}else if(dugum2.getId() == bitisId ) {
			
				rakimFark = Math.abs((dugum1.altitude + Zeplin.yerdenYukseklik) - dugum2.altitude);
			
			}else {
				
				rakimFark = Math.abs((dugum1.altitude - dugum2.altitude));
			
			}
			
			
		}
		
		
		//karsi bolu komsu ve arc
		double karsiBoluKomsu = rakimFark / yatayKm;
		double arct = Math.atan(karsiBoluKomsu);
		double degree = Math.toDegrees(arct);
		double tan = degree;
		
		double rakimFarkKm = (rakimFark * Math.pow(10, -3));
		
		//hipotenus uzakligi
		double hipo = Math.sqrt(Math.pow(rakimFarkKm, 2) + Math.pow(yatayKm, 2));	
		
		TanHipo result = new TanHipo(tan, hipo);
		
		return result;
	}
	
	public double LatLongHesabı(double lat1, double lat2, double long1, double long2) {
		
		int Dunya_Yaricap = 6371;
		
		//lat long hesabı		
		double latFark  = Math.toRadians((lat2 - lat1));
		double longFark = Math.toRadians((long2 - long1));
      
		double baslangicLatRadians = Math.toRadians(lat1);
		double bitisLatRadians   = Math.toRadians(lat2);
      
		//(sin(dlat/2))^2 + cos(lat1) * cos(lat2) * (sin(dlon/2))^2 
		double formul = Math.pow(Math.sin(latFark / 2), 2) + Math.cos(baslangicLatRadians) * Math.cos(bitisLatRadians) 
				* Math.pow(Math.sin(longFark / 2), 2);
		
		double formulDevam = 2 * Math.atan2(Math.sqrt(formul), Math.sqrt(1 - formul));
		
		double sonuc0 = Dunya_Yaricap * formulDevam;
		int sonuc = (int) Math.round(sonuc0);
				
		return sonuc;
    }
    
}
