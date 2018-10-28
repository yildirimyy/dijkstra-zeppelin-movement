import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Vector;

public class Algorithm {
	
	Set<Dugum> dZiyaretEdilmisler = new HashSet<>();
	Set<Dugum> dZiyaretEdilmemisler = new HashSet<>();
	
	//tum dugumlerin maliyetlerini olustur
	public Vector<Dugum> Dijkstra(int baslangicId, Vector<Dugum> graph) {
		
		Dugum baslangicDugum = graph.get(baslangicId);
		baslangicDugum.setMaliyet(0);
		
		//baslangic dugumunu ziyaret edilmemis olarak ekle
		dZiyaretEdilmemisler.add(baslangicDugum);
		
		//ziyaret edilmemisler bitene kadar tekrarla
		while(dZiyaretEdilmemisler.size() > 0) {
			
			//ziyaret edilmemislerden en dusuk maliyetli olani bul
			//ilk sefer de baslangic dugumu gelir
			Dugum mevcutDugum = EnDusukMaliyetliDugumuGetir();
			
			//mevcutDugumu ziyaret edilmemislerden kaldir
			dZiyaretEdilmemisler.remove(mevcutDugum);
			
			//mevcut dugumun komsularina maliyet atamasi yap
			KomsularaMaliyetAtamasiYap(mevcutDugum);
			
			//ziyaret edilmislere mevcut dugumu ekle
			dZiyaretEdilmisler.add(mevcutDugum);			
		}
		
		return graph;
	}
	
	//ziyaret edilmemislerden en dusuk maliyetli olani bul
	public Dugum EnDusukMaliyetliDugumuGetir() {

		Dugum resultDugum = null;
		double maliyet = Double.MAX_VALUE;

		for (Dugum dugum : dZiyaretEdilmemisler) {
			
			double dugumMaliyet = dugum.getMaliyet();
			if (maliyet > dugumMaliyet) {
				maliyet = dugumMaliyet;
				resultDugum = dugum;
			}

		}

		return resultDugum;
	}
	
	//mevcut dugumun komsularina maliyet atamasi yap
	public void KomsularaMaliyetAtamasiYap(Dugum mevcutDugum) {
		
		//komsu dugumlerin idleri don
		for (int id : mevcutDugum.getKenarMap().keySet()) {
			
				//komsuDugumu getir
				Dugum komsuDugum = mevcutDugum.getKomsu(id);
				
				//ziyaret edilmislerde bu dugum yoksa maliyet atamasi yap
				if(!dZiyaretEdilmisler.contains(komsuDugum)) {
					MaliyetAtamasi(mevcutDugum, komsuDugum);
					
					//ziyaret edilmemislere komsuyu ekle
					dZiyaretEdilmemisler.add(komsuDugum);
				}
			
		}	
	}
	
	public void MaliyetAtamasi(Dugum mevcutDugum, Dugum komsuDugum) {
		
		//mevcut dugumun maliyeti
		double mevcutDugumMaliyet = mevcutDugum.getMaliyet();
		
		//mevcut dugumden komsuya gitme maliyeti
		//mevcut dugumun komsuya bagli oldugu kenar maliyeti
		double komsuDugumeGitmeMaliyet = mevcutDugum.getKenarMap().get(komsuDugum.getId());
		
		//komsu dugumun maliyeti
		double komsuDugumMaliyet = komsuDugum.getMaliyet();
		
		//mevcut dugumden bu komsuya gitmek komsu maliyetininden kucukse atama yap
		if( komsuDugumMaliyet > (komsuDugumeGitmeMaliyet + mevcutDugumMaliyet)) {
			
			komsuDugum.setMaliyet((komsuDugumeGitmeMaliyet + mevcutDugumMaliyet));
			
            LinkedList<Dugum> mevcutDugumYolu = new LinkedList<>(mevcutDugum.getDugumYolu());
            mevcutDugumYolu.add(mevcutDugum);
            
            //komsudugume gelmek icin mevcut dugumun yolunu komsu dugume atama yap
            komsuDugum.setDugumYolu(mevcutDugumYolu);
            
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
