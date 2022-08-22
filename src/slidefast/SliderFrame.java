
package slidefast;

import java.awt.Component;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

/**
 *
 * @author user
 */
public class SliderFrame extends JLayeredPane{
private final MigLayout layout;
private final JPanel panel;
private final Pagnition pagnition;
private final Animator animate;
private Component componentShow;
private Component componentOut;
private int selectedIndex;
private boolean selcted;

    
    public SliderFrame() {
        layout = new MigLayout("inset 0");
        panel = new JPanel();
        pagnition = new Pagnition();
        pagnition.addEventPagnition(new EventPagnition() {
            @Override
            public void Onclick(int index) {
                System.out.println("Click :" +index);
            }
        });
        TimingTarget target = new TimingTargetAdapter(){
            @Override
            public void begin() {
            componentShow.setVisible(true);
            componentOut.setVisible(true);
            }

            @Override
            public void timingEvent(float fraction) {
                double width = panel.getWidth();
                int location = (int) (width * fraction);
                int locationShow = (int) (width *(1f-fraction));
                if (selcted) {
                    layout.setComponentConstraints(componentShow, "pos " +locationShow+ " 0 100% 100%, w 100%!");
                    layout.setComponentConstraints(componentOut, "pos -" +location+ " 0 " + (width -location) + " 100%");
                }else{
                       layout.setComponentConstraints(componentShow, "pos -" +locationShow+ " 0 " + (width -locationShow) + " 100%");
                      layout.setComponentConstraints(componentOut, "pos " +location+ " 0 100% 100%, w 100%!");
                
                }
                panel.revalidate();
                
            }

            @Override
            public void end() {
            componentOut.setVisible(false);
            layout.setComponentConstraints(componentShow, "pos 0 0 100% 100%, width 100%");
            }
      
        
    };
        animate = new Animator(1000, target);
        animate.setResolution(0);
        animate.setAcceleration(0.5f);
        animate.setDeceleration(0.5f);
        setLayer(pagnition, JLayeredPane.POPUP_LAYER);
        panel.setLayout(layout);
        setLayout(new MigLayout("fill, inset 0", "[fill, center]", "3[fill]3"));
        add(pagnition, "pos 0.5al 1al n n");
        add(panel, "w 100%-6!");
        
    }
    
    public void getSlider (Component ...com){
        if (com.length >= 2) {
            for(Component coms: com){
                coms.setVisible(false);
                panel.add(coms, "pos 0 0 0 0");
            }
        }
        if (panel.getComponentCount() >0) {
            componentShow = panel.getComponent(0);
            componentShow.setVisible(true);
            layout.setComponentConstraints(componentShow, "pos 0 0 100% 100%");
        }
        pagnition.setTotal(panel.getComponentCount());
           pagnition.setCurrentIndex(0);
    }
    public void Back(){
        if (!animate.isRunning()) {
            selcted = false;
            selectedIndex = goBack(selectedIndex);
            componentShow = panel.getComponent(selectedIndex);
            componentOut =panel.getComponent(checkBack(selectedIndex +1));
            animate.start();
        }
    }
    
    public void Next(){
        if(!animate.isRunning()){
            selcted = true;
            selectedIndex = getNext(selectedIndex);
            componentShow = panel.getComponent(selectedIndex);
            componentOut = panel.getComponent(checkNext(selectedIndex -1));
            animate.start();
        }
   
    } 
    private int getNext(int index){
        if (index == panel.getComponentCount()-1) {
            return 0;
        }else{
            return index +1;
        }
    }
    private int checkNext(int index){
        if (index == -1) {
            return panel.getComponentCount() -1;
        }else{
            return index;
        }
    }
    private int goBack(int index){
        if (index == 0) {
            return panel.getComponentCount() -1;
        }else{
            return index -1;
        }
    }
    
    private int checkBack(int index){
        if (index==panel.getComponentCount()) {
            return 0;
        }else
            return  index;
    }
}
