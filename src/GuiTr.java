import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

class GuiTr extends JPanel
{
    private BufferedImage image;

    public GuiTr()
    {
        setOpaque(true);
        try
        {
            image = ImageIO.read(new File("tr.png"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        setLayout(null);
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}