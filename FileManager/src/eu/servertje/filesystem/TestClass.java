package eu.servertje.filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class TestClass
{
    
    public static void main(String[] args)
    {
        TestClass tc = new TestClass();
//        tc.listFiles();
//        tc.testPath();
//        tc.testFsTree();
        tc.testView();
    }

    public void listFiles()
    {
        File directory = new File("/home/ronald");
        
        File[] files = directory.listFiles();
        
//        for (File f : files)
//        {
//            if (f.isFile())
//                System.out.println("File: " + f.getName());
//            else if (f.isDirectory())
//                System.out.println("Directory: " + f.getName());
//        }
        List<File> lFiles = Arrays.asList(files);
        for (File f : lFiles)
            System.out.println(f.getName());
        
//        File file = new File("");
    }
    
    void testView()
    {
        FsTree tree = new FsTree();
        FsView view = tree.getView();
        System.out.println("Parent list");
        System.out.println("--------------------------");
        for (String string : view.getParentList())
        {
            System.out.println(string);
        }
        System.out.println("Current list");
        System.out.println("--------------------------");
        for (String string : view.getCurrentList())
        {
            System.out.println(string);
        }
        System.out.println("Child list");
        System.out.println("--------------------------");
        for (String string : view.getChildList())
        {
            System.out.println(string);
        }
        System.out.println("--------------------------");
        System.out.println("--------------------------");
    }
    void testFsTree()
    {
        FsTree tree = new FsTree();
//        Node n = (FsNode) tree.getNode();
//        n.storeChilderen();
//        System.out.println("is root: " + n.isRoot());
//        System.out.println("if file " + n.isFile());
//        System.out.println("is dir " + n.isDirectory());
//        System.out.println("....");
//        
//        tree.listChilderen();
//        tree.listCurrent();
        tree.listCurrent();
        System.out.println("--------------------------");
        tree.moveDown();
        tree.listCurrent();
        System.out.println("--------------------------");
        tree.moveDown();
        tree.listCurrent();
        System.out.println("--------------------------");
        tree.moveDown();
        tree.listCurrent();
//        tree.listChilderen();
        
    }
    void testPath()
    {
        try (DirectoryStream<Path> ds = 
                Files.newDirectoryStream(FileSystems.getDefault().getPath("/home/ronald")))
        {
            for (Path path : ds)
            {
                System.out.println(path.getFileName());
            }
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
