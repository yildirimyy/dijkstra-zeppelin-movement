import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Problemler {
	
	
	//en cok kar yapilacak yolcu sayisini bul min maliyet maks kar olusturur
	public int Problem_EniyiYolcuSayisiniBul(Map<Integer, Vector<Dugum>> graphMap, int baslangicId, int bitisId, String problemNo) {
		
		double maksKar = Integer.MIN_VALUE;
		int maksKarYolcuSayisi = 0;
		int zeplin100KmMaliyet = Zeplin.yuzKmMaliyet;
		int yolcuFiyati = Zeplin.yolcuFiyat;
		
		// key = yolcu sayisina denk geliyor yolcu sayilari donuluyor
		for (int key : graphMap.keySet()) {
			
			if(key == 0)
				continue;
			
			Vector<Dugum> graph = graphMap.get(key);
			
			//bitis dugumun
			Dugum bitisDugumu = graph.get(bitisId);
			double toplamKm = bitisDugumu.getMaliyet();
			double kar = (yolcuFiyati * key) - ( (toplamKm / 100)  * zeplin100KmMaliyet );
			
			if(kar > maksKar) {
				maksKar = kar;
				maksKarYolcuSayisi = key;
			}
			
		}
		
		System.out.println("\nen iyi yolcu : " + maksKarYolcuSayisi);
		
		DosyaOkuYaz dosyaOkuYaz = new DosyaOkuYaz();
		if(maksKarYolcuSayisi > 0) {
			Dugum bitisDugum = graphMap.get(maksKarYolcuSayisi).get(bitisId);
			dosyaOkuYaz.ProblemPathOut(bitisDugum, problemNo, maksKarYolcuSayisi, true);
		}else {
			dosyaOkuYaz.ProblemPathOut(null, problemNo, maksKarYolcuSayisi, false);
		}
		
		return maksKarYolcuSayisi;
	}
	
	//problem 2 icin en iyi yolcu en dusuk yol km veren yolcu
	public int Problem2_EniyiYolcuSayisiniBul(Map<Integer, Vector<Dugum>> graphMap, int baslangicId, int bitisId, String problemNo) {
		
		double minYolMaliyeti = Double.MAX_VALUE;
		int endusukyolKisiSayisi = 0;
		
		// key = yolcu sayisina denk geliyor yolcu sayilari donuluyor
		for (int key : graphMap.keySet()) {
			
			if(key == 0)
				continue;
			
			Vector<Dugum> graph = graphMap.get(key);
			
			//bitis dugumun
			Dugum bitisDugumu = graph.get(bitisId);
			double toplamKm = bitisDugumu.getMaliyet();
			
			if(minYolMaliyeti > toplamKm) {
				minYolMaliyeti = toplamKm;
				endusukyolKisiSayisi = key;
			}
			
		}
		
		System.out.println("\nen iyi yolcu : " + endusukyolKisiSayisi);
		
		DosyaOkuYaz dosyaOkuYaz = new DosyaOkuYaz();
		if(endusukyolKisiSayisi > 0) {
			Dugum bitisDugum = graphMap.get(endusukyolKisiSayisi).get(bitisId);
			dosyaOkuYaz.ProblemPathOut(bitisDugum, problemNo, endusukyolKisiSayisi, true);
		}else {
			dosyaOkuYaz.ProblemPathOut(null, problemNo, endusukyolKisiSayisi, false);
		}
		
		return endusukyolKisiSayisi;
	}
	
	// farklı yolcu sayilarla temiz graph olusturmak icin
	public Vector<Dugum> temizGraphOlustur(Vector<Dugum> graph) {

		Vector<Dugum> temizGraph = new Vector<>();
		// temizGraph.add(new Dugum(0));

		for (Dugum dugum : graph) {

			Dugum yeniDugum = dugum.getCopyDugum();

			temizGraph.add(yeniDugum);
		}

		return temizGraph;
	}
	
	
	// 50 - 5 arasinda tum yolculari dene graph olustur algoritma uygula
	public Map<Integer, Vector<Dugum>> Problem1(int baslangicId, int bitisId, Vector<Dugum> graph) {
		
		// min 5 , maks 50 yolcu
		int minYolcu = Zeplin.minYolcu;
		int maksYolcu = Zeplin.maksYolcu;
		
		//yolcu sayisi graph eslemesi
		Map<Integer, Vector<Dugum>> graphMap = new HashMap<>();
		
		for (int i = minYolcu; i <= maksYolcu; i++) {
			
			Zeplin yeniZeplin = new Zeplin(i);
			Vector<Dugum> yeniGraph = temizGraphOlustur(graph);	
			Islemler yeniIslem = new Islemler();
			Algorithm yeniAlgorithm = new Algorithm();
			
			//graph komsulari kenarlari olustur egim hesapla
			yeniGraph = yeniIslem.KenarlariKomsulariOlustur_EgimHesapla(baslangicId, bitisId, yeniGraph, yeniZeplin);
			
			//algoritmayı uygula illere maliyet atamasi yap
			yeniGraph = yeniAlgorithm.Dijkstra(baslangicId, yeniGraph);
			
			//yolcu sayisi - graph
			graphMap.put(i, yeniGraph);
		}
		
		DosyaOkuYaz dosyaOkuYaz = new DosyaOkuYaz();
		dosyaOkuYaz.ProblemOut(graphMap, bitisId, "1");
		dosyaOkuYaz.ProblemKomsulukOut(graphMap, "1");
		
		return graphMap;
	}
	
	//10 20 30 40 50 yolculari ile graphlar olustur
	public Map<Integer, Vector<Dugum>> Problem2(int baslangicId, int bitisId, Vector<Dugum> graph) {
		
		Vector<Integer> yolcuSayilari = new Vector<>();
		yolcuSayilari.add(10);
		yolcuSayilari.add(20);
		yolcuSayilari.add(30);
		yolcuSayilari.add(40);
		yolcuSayilari.add(50);
		
		//yolcu sayisi graph eslemesi
		Map<Integer, Vector<Dugum>> graphMap = new HashMap<>();
		
		for (int yolcuSayisi : yolcuSayilari) {
			
			Zeplin yeniZeplin = new Zeplin(yolcuSayisi);
			Vector<Dugum> yeniGraph = temizGraphOlustur(graph);	
			Islemler yeniIslem = new Islemler();
			Algorithm yeniAlgorithm = new Algorithm();
			
			//graph komsulari kenarlari olustur egim hesapla
			yeniGraph = yeniIslem.KenarlariKomsulariOlustur_EgimHesapla(baslangicId, bitisId, yeniGraph, yeniZeplin);
			
			//algoritmayı uygula illere maliyet atamasi yap
			yeniGraph = yeniAlgorithm.Dijkstra(baslangicId, yeniGraph);
			
			//yolcu sayisi - graph
			graphMap.put(yolcuSayisi, yeniGraph);
			
		}
	
		DosyaOkuYaz dosyaOkuYaz = new DosyaOkuYaz();
		dosyaOkuYaz.ProblemOut(graphMap, bitisId, "2");
		dosyaOkuYaz.ProblemKomsulukOut(graphMap, "2");
		
		return graphMap;
		
	}
	
	public static double GelirHesapla(String problemNumarasi, double toplamMaliyetTl, int yolcusayisi) {
		
		double gelir = 0;
		int yolcuFiyati = Zeplin.yolcuFiyat;
		
    	//problem 1 de gelir sabit problem 2 de %50 olmali
    	if(problemNumarasi == "1") {
    		gelir = (yolcuFiyati * yolcusayisi);
    	}else {
    		//yuzde 50 hesabi
    		double yuzde50Karli = toplamMaliyetTl + (toplamMaliyetTl / 2); 
    		double olmasiGerekenYolcuFiyati = yuzde50Karli / yolcusayisi;
    		gelir = (olmasiGerekenYolcuFiyati * yolcusayisi);
    	}
		
		return gelir;
	}
	
	public static double ToplamTlMaliyetHesapla(double toplamKm) {
		
        int zeplin100KmMaliyet = Zeplin.yuzKmMaliyet;
		
		double maliyet = (toplamKm / 100)  * zeplin100KmMaliyet ;
		
		return maliyet;
		
	}
	
	
	
	
	
	
	
	
	
	
	

}
