

package view;


public class GridPanel extends javax.swing.JPanel {
    private static final long serialVersionUID = 1L;

    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        model.Model.THE_MODEL.drawModel(g);
        if (!(view.View.THE_VIEW.isRunning()))
            model.Model.THE_MODEL.drawPause(g);
        
        if (model.Snake.THE_SNAKE.isDead())
            model.Model.THE_MODEL.drawGameOver(g);
        
    }
}

