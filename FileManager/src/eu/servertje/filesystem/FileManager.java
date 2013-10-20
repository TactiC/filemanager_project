package eu.servertje.filesystem;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.swing.DefaultListModel;

public class FileManager
{
    private Path current;
    
    public FileManager()
    {
        this.current = Paths.get("/home/ronald");
    }
    
    public FileManager(String location)
    {
        this.current = Paths.get(location);
    }

    public Path getCurrent()
    {
        return this.current;
    }
    
//    private File[] parentFiles;
//    private File[] currentFiles;
//    private File[] childFiles;
    private Path[] currentPaths;

//    public String getCurrentFile(int index)
//    {
//        String result;
//        if (index < currentFiles.length)
//        {
//            result = currentFiles[index].getName();
//        }
//        else
//        {
//            result = "...";
//        }
//        return result;
//    }
 
    public void updateCurrentFiles(String location, DefaultListModel<String> model)
    {
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(
                FileSystems.getDefault().getPath(this.current.toString())))
        {
            for (Path path : ds)
            {
                model.addElement(path.getFileName().toString());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
//    private String getAbsChildPath(int index)
//    {
//        String result = null;
//        if (index < currentFiles.length)
//        {
//            result = currentFiles[index].getAbsolutePath();
//        }
//        return result;
//    }
    
    public void updateChildFiles(String location, DefaultListModel<String> model)
    {
        Path p = Paths.get(location);
        
        try(DirectoryStream<Path> ds = Files.newDirectoryStream(
                FileSystems.getDefault().getPath(this.current.toString() + "/" + location)))
        {
            for (Path path : ds)
            {
                model.addElement(path.getFileName().toString());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void updateParentFiles(int index, DefaultListModel<String> model)
    {
//        File fLoc = new File(getAbsChildPath(index));
//        model.clear();
//        File parent = fLoc.getParentFile();
//        if (parent != null)
//        {
//            this.parentFiles = fLoc.getParentFile().listFiles();
//            if (this.parentFiles != null)
//            {
//                for (File file : this.parentFiles)
//                {
//                    model.addElement(file.getName());
//                }
//            }
//        }
    }
}
