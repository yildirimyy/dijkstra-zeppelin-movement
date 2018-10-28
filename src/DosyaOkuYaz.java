import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

public class DosyaOkuYaz {
	
	public static final String komsulukDosyaAdi = "komsuluk.txt";
	public static final String latlongDosyaAdi = "latlong.txt";
	public static final String pixelDosyaAdi = "pixel.txt";
	public static final String problemOutDosyaAdi = "problemPROBLEMNOOut.txt";
	public static final String problemOutKomsulukDosyaAdi = "problemPROBLEMNOOutTumKomsuluk.txt";
	
	// 1 ve 2 olma durumu parametre olarak ekleniyor
	public static final String problem1ve2KomsulukOutDosyaAdi = "problemPROBLEMNOyolcu";

	public static final String problemPathOutDosyasi = "problemPROBLEMNOpath.txt";

	//dugum oluştururken latitu altitu vs bilgileri eklenecek?
	public Vector<Dugum> DosyaScanner(Vector<Dugum> Graph) {
		
		File file = new File(komsulukDosyaAdi);
		try {

			Scanner scanner = new Scanner(file);

			int i = 1;
			Graph.add(new Dugum(0));
			while (scanner.hasNextLine()) {

				// satir oku
				String satir = scanner.nextLine();
				// virgülleri temizle
				String[] numaralars = satir.split(",");

				// virgülden kurtuldugun komsuları int yap
				int[] numaralar = new int[numaralars.length];
				for (int a = 0; a < numaralars.length; a++) {
					numaralar[a] = Integer.parseInt(numaralars[a]);
				}

				// yeni dugum olustur
				//ilk eleman dugumun kendisi
				Graph.add(new Dugum(numaralar[0]));

				// olusan dugume komsuları yerlestir
				for (int a = 1; a < numaralar.length; a++) {
					Graph.get(i).addKomsuId(numaralar[a]);
				}

				// okunan satirin numarasi
				i++;
			}

			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Dosya Yok1..");
		}
		
		//lat long bilgilerini yerlestir
		File fileLatLong = new File(latlongDosyaAdi);
		try {

			Scanner scannerLatLong = new Scanner(fileLatLong);
			
			while (scannerLatLong.hasNextLine()) {

				// satir oku
				String satir = scannerLatLong.nextLine();
				// virgülleri temizle
				String[] numaralars = satir.split(",");

				// virgülden kurtuldugun degerleri eslestir
				double lat_t = Double.parseDouble(numaralars[0]);
				double long_t = Double.parseDouble(numaralars[1]);
				int id = Integer.parseInt(numaralars[2]);
				int altitude = Integer.parseInt(numaralars[3]);
				
				//lat long alti bilgilerini ilgili sehire ata
				Graph.get(id).setKonum(lat_t, long_t, altitude);
				
			}

			scannerLatLong.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Dosya Yok2..");
		}
		
		//cizim icin pixel yerlestir
		GuiTrDraw.pixelVector = new Vector<>();
		GuiTrDraw.pixelVector.addElement(new PixelKonum(0, 0, 0));
		File filePixel = new File(pixelDosyaAdi);
		try {
			Scanner scannerFilePixel = new Scanner(filePixel);
			
			while (scannerFilePixel.hasNextLine()) {

				// satir oku
				String satir = scannerFilePixel.nextLine();
				// virgülleri temizle
				String[] numaralars = satir.split(",");

				// virgülden kurtuldugun degerleri eslestir
				int id = Integer.parseInt(numaralars[0]);
				int x = Integer.parseInt(numaralars[1]);
				int y = Integer.parseInt(numaralars[2]);
				
				//set pixel vector
				GuiTrDraw.pixelVector.addElement(new PixelKonum(id, x, y));
				
				//set pixel
				Graph.get(id).setXandY(x, y);
			}

			scannerFilePixel.close();
			
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Dosya Yok3..");
		}
		
		return Graph;
	}
	
	//sadece baslangic da cagirmak icin
	public void DosyaScannerPixels() {
		// cizim icin pixel yerlestir
		GuiTrDraw.pixelVector = new Vector<>();
		GuiTrDraw.pixelVector.addElement(new PixelKonum(0, 0, 0));
		File filePixel = new File(pixelDosyaAdi);
		try {
			Scanner scannerFilePixel = new Scanner(filePixel);

			while (scannerFilePixel.hasNextLine()) {

				// satir oku
				String satir = scannerFilePixel.nextLine();
				// virgülleri temizle
				String[] numaralars = satir.split(",");

				// virgülden kurtuldugun degerleri eslestir
				int id = Integer.parseInt(numaralars[0]);
				int x = Integer.parseInt(numaralars[1]);
				int y = Integer.parseInt(numaralars[2]);

				// set pixel vector
				GuiTrDraw.pixelVector.addElement(new PixelKonum(id, x, y));

			}

			scannerFilePixel.close();

		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Dosya Yok3..");
		}
	}
	
	
	public void ProblemOut(Map<Integer, Vector<Dugum>> graphMap, int bitisId, String problemNumarasi) {
	    
		String outFileName = problemNumarasi + File.separator + problemOutDosyaAdi.replace("PROBLEMNO", problemNumarasi);
		
		//new File(Paths.get("").toAbsolutePath().toString() + "/" + problemNumarasi).mkdirs();
		File file = new File(outFileName); 
	    
        try {
        	
            FileOutputStream fos = new FileOutputStream(file);
    		double kar = 0;
    		double toplamKm = 0;
    		
            String metin = "";          
            Set<Integer> yolcuSayilari = graphMap.keySet();
            for (int yolcusayisi : yolcuSayilari) {
				
            	Vector<Dugum> graph = graphMap.get(yolcusayisi);
            	Dugum bitisDugum = graph.get(bitisId);
            	
            	metin = "yolcu sayisi : " + yolcusayisi + "\t  yol: ";
            	for (Dugum dugum : bitisDugum.getDugumYolu()) {
            		metin += dugum.getId() + "->";
				}
            	toplamKm = bitisDugum.getMaliyet();
            	
            	if(bitisDugum.getDugumYolu().size() > 0) {
            		metin += Integer.toString(bitisId);
            	}
            	
            	double toplamMaliyetTl = Problemler.ToplamTlMaliyetHesapla(toplamKm);
            	
            	metin += "\t  maliyet km : " + toplamKm;
            	metin += "\t  maliyet tl : " + toplamMaliyetTl;

            	//problem 1 de gelir sabit problem 2 de %50 olmali
            	double gelir = 0;
            	gelir = Problemler.GelirHesapla(problemNumarasi, toplamMaliyetTl, yolcusayisi);
            	
            	metin += "\t yolcu fiyati : " + (gelir / yolcusayisi); 	
            	
            	
            	metin += "\t gelir : " + gelir;
            	
            	kar = (gelir) - toplamMaliyetTl;
            	
            	metin += "\t  kar : " + kar + "\n";
            	
            	
            	fos.write(metin.getBytes());
            	metin = "";
			}
            
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        
        String outFileNameKomsuluk = problemNumarasi + File.separator + problemOutKomsulukDosyaAdi.replace("PROBLEMNO", problemNumarasi);
        File fileKomsuluk = new File(outFileNameKomsuluk); 
        
        try {
			
        	FileOutputStream fosKomsuluk = new FileOutputStream(fileKomsuluk);
    		
            String metinKomsuluk = "id (lat long al) \t \t komsu (yatay km)\n";          
            Vector<Dugum> dugumler = graphMap.get(Zeplin.maksYolcu);
            
        	for (Dugum dugum : dugumler) {
				
        		int id = dugum.getId();
        		
        		if(id == 0)
        			continue;
        		
        		double la = dugum.latitude;
        		double lo = dugum.longitute;
        		int al = dugum.altitude;
        		
        		Vector<Kenar> komsuKenarlar = dugum.getKenarlar();
        		
        		metinKomsuluk += "" + id + " (" + String.format("%.4f", la) + " " + String.format("%.4f", lo) + " " + String.format("%04d", al)  + ")" + "\t";
        		
        		for (Kenar kenar : komsuKenarlar) {
					metinKomsuluk += kenar.getConnectedId() + " (" + kenar.getYatayKm() + ")  ";
				}
        		
        		metinKomsuluk += "\n";
        		         		
        		fosKomsuluk.write(metinKomsuluk.getBytes());
        		metinKomsuluk = "";
			}
                        
            fosKomsuluk.flush();
            fosKomsuluk.close();
        	
		} catch (Exception e) {
			// TODO: handle exception
            e.printStackTrace();
		}
        
        
	}
	
	public void ProblemPathOut(Dugum bitisDugum, String problemNumarasi, int yolcusayisi, boolean isPath) {
	    
		String pathOut = problemNumarasi + File.separator + problemPathOutDosyasi.replace("PROBLEMNO", problemNumarasi);
		String outFileName = pathOut;
		
        try {
    		//new File(Paths.get("").toAbsolutePath().toString() + "/" + problemNumarasi).mkdirs();	
        	File file = new File(outFileName); 
            FileOutputStream fos = new FileOutputStream(file);
        	
            String metin = "";
    		
            if(isPath) {
        		double kar = 0;
        		double toplamKm = 0;
                
        		List<Dugum> path = bitisDugum.getDugumYolu();
            	metin = "en iyi yolcu sayisi : " + yolcusayisi + "\nyol: ";
            	for (Dugum dugum : path) {
            		metin += dugum.getId() + "->";
    			}
            	toplamKm = bitisDugum.getMaliyet();
            	
            	double toplamMaliyetTl = Problemler.ToplamTlMaliyetHesapla(toplamKm);
            	
            	metin += Integer.toString(bitisDugum.getId()) + "\nmaliyet km : " + toplamKm;
            	metin += "\nmaliyet tl : " + toplamMaliyetTl;
            	
            	//problem 1 de gelir sabit problem 2 de %50 olmali
            	double gelir = 0;
            	gelir = Problemler.GelirHesapla(problemNumarasi, toplamMaliyetTl, yolcusayisi);
            	
            	metin += "\nyolcu fiyati : " + (gelir / yolcusayisi); 	
            	
            	metin += "\ngelir : " + gelir;         	
            	kar = gelir - toplamMaliyetTl;
            	
            	metin += "\nkar : " + kar + "\n";
            }
            
        	fos.write(metin.getBytes());
            fos.flush();
            fos.close();
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
	}
	
	
	
	public void ProblemKomsulukOut(Map<Integer, Vector<Dugum>> graphMap, String problemNumarasi) {
	    
		String p1p2komsulukOut = problemNumarasi + File.separator + problem1ve2KomsulukOutDosyaAdi.replace("PROBLEMNO", problemNumarasi);
		String outFileName = p1p2komsulukOut;
		
        try {
    		
            String metin = "";          
            Set<Integer> yolcuSayilari = graphMap.keySet();
            for (int yolcusayisi : yolcuSayilari) {
				
            	outFileName += yolcusayisi + ".txt";
            	
            	File file = new File(outFileName); 
                FileOutputStream fos = new FileOutputStream(file);
                
            	Vector<Dugum> graph = graphMap.get(yolcusayisi);
            	
        		for (Dugum dugum : graph) {
        			metin += "id : " + dugum.getId() + " komsular(hipo km - aci) : ";
        			for (int komsuId : dugum.kenarMap.keySet()) {
        				metin += " " + komsuId;
        				double maliyet = dugum.kenarMap.get(komsuId);
        				metin += "(" + maliyet + " - ";
        				
        				Kenar kenar = dugum.getKenarByConnId(komsuId);
        				if(kenar != null) {
        					metin += kenar.getTan();
        				}
        				
        				metin += ")  ";        				
        				
        			}
        			metin += "\n";
        		}
            	
            	
            	fos.write(metin.getBytes());
                fos.flush();
                fos.close();
            	metin = "";
            	
            	outFileName = p1p2komsulukOut;
			}
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
	}
	
	
	
	
	
	

}
