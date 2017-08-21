package de.clearit.kindergarten;

import javax.swing.SwingUtilities;

public class App {

  public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new ShowFenster();
      }
    });
  }
  
}


/*
Git konsole

git add --all

git commit -m "feat: updated gui"

git push origin kevin
*/