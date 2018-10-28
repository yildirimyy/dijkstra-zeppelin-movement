import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;


import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class GuiTrDraw extends JPanel {
	
	Color bg;
	//dosya oku yaz icinde doluyor
	public static Vector<PixelKonum> pixelVector = new Vector<>();
	
	public GuiTrDraw(boolean setLocationButton) {
        setOpaque(false);
        setLayout(null);
        
        if(setLocationButton)
        	setLocationButton(pixelVector);
	}
		
	public void setLocationButton(Vector<PixelKonum> pixels) {
		
		//ilk eleman bos
		for (int i = 1; i < pixels.size(); i++) {
			
			int x = pixels.get(i).x;
			int y = pixels.get(i).y;
			int id = i;
			
			JPanel panel = new JPanel();
			//karelerin yerlesimi icin
			panel.setBounds(x - 10, y - 10, 20, 20);
			panel.setBackground(new Color(200, 150, 150, 120));
			panel.setBorder(new LineBorder(panel.getBackground(), 1, true));
			panel.add(new JLabel(Integer.toString(i)));
			
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
             	   bg = panel.getBackground();
             	   
             	   if(bg == Color.RED || bg == Color.GREEN){
             		   //bir sey yapma
             	   }else{
             		   panel.setBackground(Color.GRAY); 
             	   }
                }

                @Override
                public void mouseExited(MouseEvent e) {
             	   if(bg != Color.RED)
             		   panel.setBackground(bg);
                }
                
                @Override
                public void mouseClicked(MouseEvent e){
             	   if(Gui.clickCount == 0){
             		   panel.setBackground(Color.GREEN);
             		   bg = panel.getBackground();
             		   
             		   Gui.guiBaslangicId = id;
             		   System.out.println("bas:" + id);
             		   
             		   Gui.clickCount++;
             	   }else if(Gui.clickCount == 1){
             		   panel.setBackground(Color.RED);
             		   bg = panel.getBackground();
             		   
             		   Gui.guiBitisId = id;
            		   System.out.println("bit:" + id);;
            		   Gui.clickCount++;
             	   }
             	   
                }
            });
            
            add(panel);
					
		}
			
	}
	
	public void pathDraw(List<Dugum> path, Dugum bitisDugum) {
		
		int x1 = 0;
		int x2 = 0;
		int y1 = 0;
		int y2 = 0;
		
		for (int i = 0; i < path.size() - 1; i++) {
			
			Dugum item1 = path.get(i);
			Dugum item2 = path.get(i + 1);
			
			x1 = item1.getX();
			y1 = item1.getY();
			
			y2 = item2.getY();
			x2 = item2.getX();
			
			//cizgi cizme
			add(drawPathLine(x1, x2, y1, y2, Color.RED));
			
			//illere kutu koyma
			JPanel panel1 = new JPanel();
			panel1.setBounds(x1 - 10, y1 - 10, 20, 20);
			panel1.setBackground(new Color(200, 150, 150, 120));
			panel1.setBorder(new LineBorder(panel1.getBackground(), 1, true));
			panel1.add(new JLabel(Integer.toString(item1.getId())));
			
			if(i == 0)
				panel1.setBackground(Color.GREEN);
			else
				panel1.setBackground(Color.GRAY);
				
			add(panel1);
			
			JPanel panel2 = new JPanel();
			panel2.setBounds(x2 - 10, y2 - 10, 20, 20);
			panel2.setBackground(new Color(200, 150, 150, 120));
			panel2.setBorder(new LineBorder(panel2.getBackground(), 1, true));
			panel2.add(new JLabel(Integer.toString(item2.getId())));
			
			panel2.setBackground(Color.GRAY);
			
			add(panel2);
			
		}
		
		if(path.size() > 0) {
			Dugum sonDugum = path.get(path.size() -1 );
			
			x1 = sonDugum.getX();
			y1 = sonDugum.getY();
			
			x2 = bitisDugum.getX();
			y2 = bitisDugum.getY();
			
			add(drawPathLine(x1, x2, y1, y2, Color.RED));
			
			//bitis kutusu
			JPanel panel = new JPanel();
			//karelerin yerlesimi icin
			panel.setBounds(x2 - 10, y2 - 10, 20, 20);
			panel.setBackground(new Color(200, 150, 150, 120));
			panel.setBorder(new LineBorder(panel.getBackground(), 1, true));
			panel.add(new JLabel(Integer.toString(bitisDugum.getId())));
			
			panel.setBackground(Color.RED);
			
			add(panel);		
			
		}	
		
	}
	
	public JComponent drawPathLine (int x1, int x2, int y1, int y2, Color color) {
		
		JComponent drawComp = new JComponent() {
			
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(3));
                
                g.setColor(color);
                g.drawLine(x1, y1 , x2, y2);
            }
        };
        
        drawComp.setBounds(0, 0, 1001, 517);
					
		return drawComp;
	}
	

	
}
