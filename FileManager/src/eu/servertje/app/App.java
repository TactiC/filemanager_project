package eu.servertje.app;

import javax.swing.SwingUtilities;
import eu.servertje.gui.MainWindow;

public class App
{
    private static MainWindow mainWindow;
    
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
           public void run()
           {
//               new MainWindow().addContent();
               mainWindow = new MainWindow();
               mainWindow.fill();
           }
        });
    }

}
