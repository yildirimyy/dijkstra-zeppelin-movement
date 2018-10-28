
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class Gui extends JFrame {

	private JPanel contentPane;
	
	public static int guiBaslangicId = 0;
	public static int guiBitisId = 0;
	public static int clickCount = 0;
	
	public JPanel panel;
	public JPanel panel_1;
	
	public JButton temizleButton;
	public JButton problem1Button;
	public JButton problem2Button;
	
	public static JComboBox comboBox;
	public JTextArea textArea;
	
	public static long runTime = 0;
	public static long endTime = 0;
	
	
	//dropdown icin
	static Map<Integer, Vector<Dugum>> staticGraphMapProblem2;
	
	public void HaritadaGoster(List<Dugum> bitisDugumyolu, Dugum bitisDugumu) {
		panel.removeAll();
		
		//harita
		GuiTr trHaritaOut = new GuiTr();
		trHaritaOut.setBounds(12, 39, 1001, 517);
							
		panel.add(trHaritaOut);
		
		panel.validate();
		panel.revalidate();
		panel.repaint(); 
		
		//cizim ve butonlar						
		GuiTrDraw pathDraw = new GuiTrDraw(false);
		pathDraw.setBounds(0, 0, 1001, 517);
		pathDraw.pathDraw(bitisDugumyolu, bitisDugumu);
		trHaritaOut.add(pathDraw);
		
	}
	
	public void SecimHaritasiOlustur() {
		panel.setLayout(null);
		
		//harita
		GuiTr trHarita = new GuiTr();
		trHarita.setBounds(12, 39, 1001, 517);
		panel.add(trHarita);
		
		//cizim ve butonlar
		GuiTrDraw trDraw = new GuiTrDraw(true);
		trDraw.setBounds(0, 0, 1001, 517);
		trHarita.add(trDraw);
	}
	
	public void ComboBoxDoldur() {
		Vector<Integer> yolcuSayilari = new Vector<>();
		yolcuSayilari.add(10);
		yolcuSayilari.add(20);
		yolcuSayilari.add(30);
		yolcuSayilari.add(40);
		yolcuSayilari.add(50);
		
		for (int yolcuSayisi : yolcuSayilari) {
			comboBox.addItem(Integer.toString(yolcuSayisi));
		}
		
		comboBox.setEnabled(false);
	}
	
	public Gui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 600);
		//setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JSplitPane splitPane = new JSplitPane();
		//splitPane.setEnabled(false);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(splitPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
		);
		
		panel = new JPanel();
		splitPane.setRightComponent(panel);

		SecimHaritasiOlustur();
				
		panel_1 = new JPanel();
		splitPane.setLeftComponent(panel_1);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBackground(getBackground());
//		textArea.setLineWrap(true);
//		textArea.setWrapStyleWord(true);
		
		comboBox = new JComboBox();
		ComboBoxDoldur();
		
		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				String secilenYolcu = (String) comboBox.getSelectedItem();
				int secilenYolcuSayisi = Integer.parseInt(secilenYolcu);
				
				Vector<Dugum> secilenYolculuGraph = staticGraphMapProblem2.get(secilenYolcuSayisi);
				
				Dugum bitisDugumu = secilenYolculuGraph.get(guiBitisId);
				List<Dugum> bitisDugumyolu = bitisDugumu.getDugumYolu();

				if(bitisDugumyolu.size() == 0) {
					JOptionPane.showMessageDialog(
					        null, "Yol Yok!" , "Problem 2 Out", JOptionPane.INFORMATION_MESSAGE);
				}else {
					String sonucMetin = "";
					
					sonucMetin = "secilen yolcu sayisi : " + secilenYolcuSayisi + "\nyol: ";
	            	
	            	for (Dugum dugum : bitisDugumyolu) {
	            		sonucMetin += dugum.getId() + "->";
					}
	            	
	            	if(bitisDugumyolu.size() > 0) {
	            		sonucMetin += guiBitisId;
	            	}
	            	
	            	double maliyetKm = bitisDugumu.getMaliyet();
	            	double maliyetTl = Problemler.ToplamTlMaliyetHesapla(maliyetKm);
	            	double gelir = Problemler.GelirHesapla("2", maliyetTl, secilenYolcuSayisi);
	            	
	            	sonucMetin += "\nkm : " + maliyetKm;
	            	
	            	sonucMetin += "\nmaliyet tl : " + maliyetTl;
	            	
	            	sonucMetin += "\ngelir tl : " + gelir;
	            	
	            	sonucMetin += "\nyolcu tl : " + (gelir / secilenYolcuSayisi);
	            	
	            	sonucMetin += "\nkar tl : " + (gelir - maliyetTl);
	            	
					
					JOptionPane.showMessageDialog(
					        null, sonucMetin , "Problem 2 Out", JOptionPane.INFORMATION_MESSAGE);
					
					textArea.setText(sonucMetin);

					HaritadaGoster(bitisDugumyolu, bitisDugumu);
					
				}
				
			}
		});
			
		
		temizleButton = new JButton("temizle");		
		problem1Button = new JButton("problem1");
		problem2Button = new JButton("problem2");
		
		problem1Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(guiBaslangicId == 0 || guiBitisId == 0) {
					JOptionPane.showMessageDialog(
					        null, "Başlangıç veya bitiş seçilmemiş", "Problem 1", JOptionPane.ERROR_MESSAGE);
				}else {
					problem1Button.setEnabled(false);
					problem2Button.setEnabled(false);
					comboBox.setEnabled(false);
					
					runTime = System.currentTimeMillis();
					
					Vector<Dugum> Graph = new Vector<Dugum>();
					DosyaOkuYaz dosya = new DosyaOkuYaz();
					Graph = dosya.DosyaScanner(Graph);
					
					Problemler problemler = new Problemler();
					
					int baslangicId = guiBaslangicId;
					int bitisId = guiBitisId;
					
					//problem 1 icin ciktilar olustur
					Map<Integer, Vector<Dugum>> graphMap = problemler.Problem1(baslangicId, bitisId, Graph);
					
					int eniyiyolcuSayisi = problemler.Problem_EniyiYolcuSayisiniBul(graphMap, baslangicId, bitisId, "1");
					
					if(eniyiyolcuSayisi == 0) {
						
						endTime = System.currentTimeMillis() - runTime;
						String metin = "Yol Yok!\n";
						metin += "calisma zamani: " + endTime + "ms";
						textArea.setText(metin);						
						
						JOptionPane.showMessageDialog(
						        null, metin , "Problem 1 Out", JOptionPane.INFORMATION_MESSAGE);
					}else {
						Vector<Dugum> eniyiyolculuGraph = graphMap.get(eniyiyolcuSayisi);
						
						Dugum bitisDugumu = eniyiyolculuGraph.get(bitisId);
						List<Dugum> bitisDugumyolu = bitisDugumu.getDugumYolu();
						
						String sonucMetin = "";
						
						sonucMetin = "en iyi yolcu sayisi : " + eniyiyolcuSayisi + "\nyol: ";
		            	
		            	for (Dugum dugum : bitisDugumyolu) {
		            		sonucMetin += dugum.getId() + "->";
						}
		            	
		            	if(bitisDugumyolu.size() > 0) {
		            		sonucMetin += bitisId;
		            	}
						
		            	double maliyetKm = bitisDugumu.getMaliyet();
		            	double maliyetTl = Problemler.ToplamTlMaliyetHesapla(maliyetKm);
		            	double gelir = Problemler.GelirHesapla("1", maliyetTl, eniyiyolcuSayisi);
		            	
		            	sonucMetin += "\nkm : " + maliyetKm;
		            	
		            	sonucMetin += "\nmaliyet tl : " + maliyetTl;
		            	
		            	sonucMetin += "\ngelir tl : " + gelir;
		            	
		            	sonucMetin += "\nyolcu tl : " + (gelir / eniyiyolcuSayisi);
		            	
		            	sonucMetin += "\nkar tl : " + (gelir - maliyetTl);
						
		            	endTime = System.currentTimeMillis() - runTime;
		            	
		            	sonucMetin += "\ntime : " + endTime + "ms";
		            	
						JOptionPane.showMessageDialog(
						        null, sonucMetin , "Problem 1 Out", JOptionPane.INFORMATION_MESSAGE);
						
						textArea.setText(sonucMetin);
												
						HaritadaGoster(bitisDugumyolu, bitisDugumu);
						
						
					}
									
					
				}
				

			}
		});
			
		problem2Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(guiBaslangicId == 0 || guiBitisId == 0) {
					JOptionPane.showMessageDialog(
					        null, "Başlangıç veya bitiş seçilmemiş", "Problem 2", JOptionPane.ERROR_MESSAGE);
				}else {
					
					problem1Button.setEnabled(false);
					problem2Button.setEnabled(false);
					comboBox.setEnabled(true);
					
					runTime = System.currentTimeMillis();
					
					Vector<Dugum> Graph = new Vector<Dugum>();
					DosyaOkuYaz dosya = new DosyaOkuYaz();
					Graph = dosya.DosyaScanner(Graph);
					
					Problemler problemler = new Problemler();
					
					int baslangicId = guiBaslangicId;
					int bitisId = guiBitisId;
					
					//problem 2 icin ciktilar olustur
					Map<Integer, Vector<Dugum>> graphMapProblem2 = problemler.Problem2(baslangicId, bitisId, Graph);
					
					staticGraphMapProblem2 = graphMapProblem2;
					
					int eniyiyolcuSayisi = problemler.Problem2_EniyiYolcuSayisiniBul(graphMapProblem2, baslangicId, bitisId, "2");
					
					if(eniyiyolcuSayisi == 0) {
						
						endTime = System.currentTimeMillis() - runTime;
						String metin = "Yol Yok!\n";
						metin += "calisma zamani: " + endTime + "ms";
						
						textArea.setText(metin);	
						
						JOptionPane.showMessageDialog(
						        null, metin , "Problem 2 Out", JOptionPane.INFORMATION_MESSAGE);
						
						comboBox.setEnabled(false);	
					}else {
						
						Vector<Dugum> eniyiyolculuGraph = graphMapProblem2.get(eniyiyolcuSayisi);
						
						Dugum bitisDugumu = eniyiyolculuGraph.get(bitisId);
						List<Dugum> bitisDugumyolu = bitisDugumu.getDugumYolu();
						
						String sonucMetin = "";
						
						sonucMetin = "en iyi yolcu sayisi : " + eniyiyolcuSayisi + "\nyol: ";
		            	
		            	for (Dugum dugum : bitisDugumyolu) {
		            		sonucMetin += dugum.getId() + "->";
						}
		            	
		            	if(bitisDugumyolu.size() > 0) {
		            		sonucMetin += bitisId;
		            	}
						
		            	double maliyetKm = bitisDugumu.getMaliyet();
		            	double maliyetTl = Problemler.ToplamTlMaliyetHesapla(maliyetKm);
		            	double gelir = Problemler.GelirHesapla("2", maliyetTl, eniyiyolcuSayisi);
		            	
		            	sonucMetin += "\nkm : " + maliyetKm;
		            	
		            	sonucMetin += "\nmaliyet tl : " + maliyetTl;
		            	
		            	sonucMetin += "\ngelir tl : " + gelir;
		            	
		            	sonucMetin += "\nyolcu tl : " + (gelir / eniyiyolcuSayisi);
		            	
		            	sonucMetin += "\nkar tl : " + (gelir - maliyetTl);
		            	
		            	endTime = System.currentTimeMillis() - runTime;
		            	
		            	sonucMetin += "\ntime : " + endTime + "ms";
						
						JOptionPane.showMessageDialog(
						        null, sonucMetin , "Problem 2 Out", JOptionPane.INFORMATION_MESSAGE);

						
						textArea.setText(sonucMetin);
						
						HaritadaGoster(bitisDugumyolu, bitisDugumu);
												
					}
				}
				

			}
		});
		
		temizleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				guiBaslangicId = 0;
				guiBitisId = 0;
				clickCount = 0;
				
				DosyaOkuYaz dosya = new DosyaOkuYaz();
				dosya.DosyaScannerPixels();
				
				panel.removeAll();
				
				SecimHaritasiOlustur();
				
				panel.validate();
				panel.revalidate();
				panel.repaint(); 
				
				problem1Button.setEnabled(true);
				problem2Button.setEnabled(true);
				comboBox.setEnabled(false);
				textArea.setText("");

			}
		});
			

		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(problem1Button, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
						.addComponent(temizleButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
						.addComponent(problem2Button, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
						.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
						.addComponent(comboBox, Alignment.TRAILING, 0, 143, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(35)
					.addComponent(problem1Button)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(problem2Button)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(temizleButton)
					.addGap(33))
		);
		panel_1.setLayout(gl_panel_1);
		contentPane.setLayout(gl_contentPane);
	}
}
