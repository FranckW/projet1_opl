

package controller;


public class Controller implements java.awt.event.ActionListener , java.awt.event.KeyListener {
    private int pressedKeyCode;

    public static final controller.Controller THE_CONTROLLER = new controller.Controller();

    private Controller() {
        controller.Controller.this.pressedKeyCode = java.awt.event.KeyEvent.VK_RIGHT;
    }

    @java.lang.Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        controller.Controller.this.handlePressedKey(e.getKeyCode());
    }

    private void handlePressedKey(int kc) {
        controller.Controller.this.pressedKeyCode = kc;
        switch (controller.Controller.this.pressedKeyCode) {
            case java.awt.event.KeyEvent.VK_UP :
                if (!(view.View.THE_VIEW.isRunning())) {
                    view.View.THE_VIEW.restart();
                } 
                model.Snake.THE_SNAKE.changeDirection(model.Direction.NORTH);
                break;
            case java.awt.event.KeyEvent.VK_DOWN :
                if (!(view.View.THE_VIEW.isRunning())) {
                    view.View.THE_VIEW.restart();
                } 
                model.Snake.THE_SNAKE.changeDirection(model.Direction.SOUTH);
                break;
            case java.awt.event.KeyEvent.VK_RIGHT :
                if (!(view.View.THE_VIEW.isRunning())) {
                    view.View.THE_VIEW.restart();
                } 
                model.Snake.THE_SNAKE.changeDirection(model.Direction.EAST);
                break;
            case java.awt.event.KeyEvent.VK_LEFT :
                if (!(view.View.THE_VIEW.isRunning())) {
                    view.View.THE_VIEW.restart();
                } 
                model.Snake.THE_SNAKE.changeDirection(model.Direction.WEST);
                break;
            case java.awt.event.KeyEvent.VK_SPACE :
                if (view.View.THE_VIEW.isRunning()) {
                    view.View.THE_VIEW.stop();
                    view.View.THE_VIEW.redrawContents();
                } 
                break;
            default :
                break;
        }
    }

    private void control() {
        controller.Controller.this.handlePressedKey(controller.Controller.this.pressedKeyCode);
        model.Model.THE_MODEL.behave();
        view.View.THE_VIEW.showScore();
    }

    @java.lang.Override
    public void keyTyped(java.awt.event.KeyEvent e) {
    }

    @java.lang.Override
    public void keyReleased(java.awt.event.KeyEvent e) {
    }

    @java.lang.Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (!(model.Snake.THE_SNAKE.isDead())) {
            controller.Controller.this.control();
        } else {
            view.View.THE_VIEW.stop();
            try {
                java.lang.Thread.sleep(2500);
                view.View.THE_VIEW.dispose();
                javax.swing.JOptionPane.showMessageDialog(null, ("Vous terminez la partie avec un score de " + (model.Snake.THE_SNAKE.getScore())), "Fin du jeu", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            } catch (java.lang.InterruptedException e1) {
            }
        }
    }
}

