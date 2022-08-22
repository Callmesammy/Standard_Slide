
package slidefast;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;


public class Pagnition extends JComponent{

    private EventPagnition event;
    private int index;
    private int currentIndex;
    
    public void addEventPagnition(EventPagnition event){
        this.event = event;
    }
    
    public void setIndex(int index){
        this.index = index;
    }
    
    public void setCurrentIndex(int currentIndex){
        this.currentIndex = currentIndex;
    }
    
    public void setTotal(int total){
        removeAll();
        for (int i = 0; i < total; i++) {
            add(new item(i, event));
        }
        repaint();
        revalidate();
        
    }
    public Pagnition() {
        setLayout(new MigLayout("inset 15", "[center]8[center]"));
    }
    
    private class item extends JButton{

        public item( int index, EventPagnition event) {
            setContentAreaFilled(false);
            setBorder(new EmptyBorder(5,5,5,5));
            setBackground(Color.white);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    event.Onclick(index);
                }
            });
        }
        
           @Override
    public void paint(Graphics g) {
        super.paint(g); 
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g2.setColor(getBackground());
        g2.fillOval(0, 0, width, height);
        
        g2.dispose();
    } 
    }


    
}
