

package view;


public class View extends javax.swing.JFrame implements java.lang.Runnable , model.observer.Observer {
    private static final long serialVersionUID = 1L;

    private javax.swing.JPanel gridPanel;

    private javax.swing.JPanel infosPanel;

    private javax.swing.JLabel scoreLabel;

    private java.lang.String explanationsDialog;

    private javax.swing.Timer timer;

    private final int DELAY = 65;

    public static final view.View THE_VIEW = new view.View();

    private View() {
        super("Snake");
        view.View.this.setFrame();
        view.View.this.setGrid();
        view.View.this.setInfos();
        view.View.this.addKeyListener(controller.Controller.THE_CONTROLLER);
        view.View.this.explanationsDialog = view.View.this.setExplanationsMessage();
        view.View.this.timer = new javax.swing.Timer(view.View.this.DELAY, controller.Controller.THE_CONTROLLER);
    }

    private void setFrame() {
        view.View.this.setSize(((model.Grid.GRID_SIZE) + 5), ((model.Grid.GRID_SIZE) + 50));
        view.View.this.setLocationRelativeTo(null);
        view.View.this.setResizable(false);
        view.View.this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    }

    private void setGrid() {
        view.View.this.gridPanel = new view.GridPanel();
        view.View.this.gridPanel.setPreferredSize(new java.awt.Dimension(model.Grid.GRID_SIZE, model.Grid.GRID_SIZE));
        view.View.this.gridPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK));
        view.View.this.add(view.View.this.gridPanel, java.awt.BorderLayout.NORTH);
    }

    private void setInfos() {
        view.View.this.infosPanel = new javax.swing.JPanel();
        view.View.this.scoreLabel = new javax.swing.JLabel();
        view.View.this.showScore();
        view.View.this.infosPanel.add(scoreLabel);
        view.View.this.add(view.View.this.infosPanel);
    }

    public void redrawContents() {
        view.View.this.gridPanel.repaint();
    }

    public void showScore() {
        view.View.this.scoreLabel.setText(("SCORE : " + (model.Snake.THE_SNAKE.getScore())));
    }

    public void run() {
        view.View.this.setVisible(true);
        view.View.this.timer.start();
    }

    public void stop() {
        view.View.this.timer.stop();
    }

    public void restart() {
        view.View.this.timer.restart();
    }

    public boolean isRunning() {
        return view.View.this.timer.isRunning();
    }

    public java.lang.String setExplanationsMessage() {
        view.View.this.explanationsDialog = "";
        try {
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("principe.txt"));
            java.lang.String currentLine;
            while ((currentLine = br.readLine()) != null) {
                view.View.this.explanationsDialog += currentLine + "\n";
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return view.View.this.explanationsDialog;
    }

    public static void main(java.lang.String[] args) {
        javax.swing.JOptionPane.showMessageDialog(null, view.View.THE_VIEW.explanationsDialog, "Début du jeu", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        view.View.THE_VIEW.run();
    }

    @java.lang.Override
    public void update(model.observer.Event e) {
        view.View.this.redrawContents();
    }
}

