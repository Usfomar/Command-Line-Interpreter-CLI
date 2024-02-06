import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;



public class Terminal {
    Parser parser = new Parser();
    private static final List<String> Commands_Without_Arguments = Arrays.asList("help", "pwd", "history", "date", "ls");
    private static ArrayList<String> History_of_Commands = new ArrayList<String>();//This array stores all commands that the user entered
    private static String CurrentDirectory = System.getProperty("user.dir");
    private static final String HomeDirectory = System.getProperty("user.home");


    public void help(String command, List<String> arguments) {
        if (Commands_Without_Arguments.contains(command) && !arguments.isEmpty()) {
            System.out.println("help command does not take arguments");
        } else {
            System.out.println("""
                    echo: Prints the the arguments
                    cd: Changes the current working directory.
                    ls: Lists the contents of the current working directory.
                    cp: Copies a file or directory from a source location to a destination location.
                    rm: Removes (deletes) a file or directory.
                    mkdir: Creates a new directory.
                    touch: Create a new file.
                    wc: (Word Count) returns the number of lines, words, characters and file name of a file
                    rmdir: Recursively removes a directory and its contents.
                    cat: Reads and displays the contents of a file. It can also append or overwrite contents.
                    pwd: current working directory.
                    date: Current date/time.
                    exit: stop all""");
        }
    }//End of help function

    public void history(String command, List<String> arguments) {
        if (Commands_Without_Arguments.contains(command) && !arguments.isEmpty())
        {
            System.out.println("help command does not take arguments");
        } else {
            for (String history : History_of_Commands) {
                System.out.println(history);
            }
        }
    }//End of History function


    public void pwd(String command, List<String> arguments) {
        if (Commands_Without_Arguments.contains(command) && !arguments.isEmpty()) {
            System.out.println("help command does not take arguments");
        } else {
            System.out.println("The Current Directory: " + CurrentDirectory);
        }
    }//End of pwd function

    public void echo(String command, List<String> arguments) {
        if (!arguments.isEmpty()) {
            for (String word : arguments) {
                System.out.print(word + " ");
            }
            System.out.println();
        } else {
            System.out.println(command+" should has an argument to print");
        }
    }//End of echo function

    public void ls(String command, List<String> arguments) {
        if (Commands_Without_Arguments.contains(command) && !arguments.isEmpty()) {
            if (arguments.get(0).equals("-r")) {
                try {
                    File folder_files = new File(CurrentDirectory);
                    String[] files_list = folder_files.list();
                    if (files_list != null)//Check if the folder has no files to print or not
                    {
                        for (int i = files_list.length - 1; i >= 0; i--) {
                            System.out.println(files_list[i]);
                        }
                    } else {
                        System.out.println("Not files in this directory");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Invalid argument");
            }
        } else {
            try {
                File folder_files = new File(CurrentDirectory);
                File[] files_list = folder_files.listFiles();//.listFiles is a function that return array of File that has the files in this folder path in out case is Current directory
                //.list() is function that return only file's names in an array of strings
                if (files_list != null) {
                    for (File file : files_list) {
                        if (file.isFile()) {
                            System.out.println("File: " + file.getName());
                        } else if (file.isDirectory()) {
                            System.out.println("Folder: " + file.getName());
                        } else {
                            System.out.println(file.getName());
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }//End of ls function


    public void date(String command, List<String> arguments) {
        if (Commands_Without_Arguments.contains(command) && !arguments.isEmpty()) {
            System.out.println(command + " takes no arguments");
        } else {
            System.out.println("Data: " + Calendar.getInstance().getTime());
        }
    }//End of date function


    public void mkdir(String command, List<String> arguments) {
        if (arguments.isEmpty()) {
            System.out.println(command + " should take an argument directory name");
        }
        else if(arguments.size() == 1)
        {
            for (String folderName : arguments) {
                String path = CurrentDirectory + "\\" +folderName;
                File folder = new File(path);
                if (folder.mkdir()) {
                    System.out.println("Directory created: " + folderName);
                } else {
                    System.out.println("Failed to create directory: " + folderName);
                }
            }
        }
        else {
            System.out.println(command +" takes one argument");
        }
    }//End of mkdir function

    public void touch(String command, List<String> arguments) {
        if (arguments.isEmpty()) {
            System.out.println(command + " should take a parameter (filename.txt)");
        } else {
            for (String fileName : arguments) {
                String path = CurrentDirectory + File.separator + fileName;
                File file = new File(path);
                try {
                    if (file.createNewFile()) {
                        System.out.println("File created: " + fileName);
                    } else {
                        System.out.println("File already exists: " + fileName);
                    }
                } catch (Exception e) {
                    System.out.println("Failed to create file: " + fileName);
                }
            }
        }
    }//End of touch function

    public void cd(String command, List<String> arguments)
    {
        if(arguments.isEmpty())
        {
            CurrentDirectory = HomeDirectory;
        }
        else if (arguments.size() == 1)
        {
            String folderName = arguments.get(0);
            if (folderName.equals(".."))
            {
                File currentFolder = new File(CurrentDirectory);
                String parentDirectory = currentFolder.getParent();
                if (parentDirectory !=null)
                {
                    CurrentDirectory = parentDirectory;
                }
            }
            else
            {
                String path = CurrentDirectory + File.separator + folderName;
                File folder = new File(path);
                if (folder.isDirectory())
                {
                    CurrentDirectory = path;
                }
                else
                {
                    System.out.println("Directory does not exist: " + folderName);
                }
            }
        }
        else
        {
            System.out.println(command +" takes only one argument at most");
        }

    }//End of cd function
    public void wc(String command , List<String> arguments)
    {
        int Number_of_Lines=0;
        int Number_of_words = 0;
        int Number_of_Character = 0;
        if (arguments.isEmpty())
        {
            System.out.println(command + " should take an argument the file name");
        }
        else if(arguments.size() > 1)
        {
            System.out.println(command+" takes one parameter as a filename");
        }
        else {
            String filename = arguments.get(0);
            try
            {

                File file = new File(filename);
                Scanner in = new Scanner(file);
                String line;
                while(in.hasNextLine())
                {
                    line = in.nextLine();

                    for(int i = 0 ; i < line.length() ; i++)
                    {
                        Number_of_Character++;
                        if(line.charAt(i) == ' ')
                        {
                            Number_of_words++;
                        }
                    }
                    Number_of_words++;
                    Number_of_Lines++;
                }
                System.out.println("The number of lines is "+Number_of_Lines);
                System.out.println("The number of words is "+Number_of_words);
                System.out.println("The number of characters the spaces included is "+Number_of_Character);
                System.out.println("The name of the file " + file.getName());
            }catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }//End of wc function

    public void rm(String command, List<String> arguments)
    {
        if (arguments.size() != 1) {
            System.out.println(command+" requires exactly one argument (file or directory name)");
        } else {
            String target = arguments.get(0);
            File fileToDelete = new File(CurrentDirectory + "\\" + target);
            if (fileToDelete.exists()) {
                if (fileToDelete.isFile()) {
                    if (fileToDelete.delete()) {
                        System.out.println("File " + target + " deleted successfully");
                    } else {
                        System.out.println("Failed to delete file " + target);
                    }
                } else if (fileToDelete.isDirectory()) {
                    System.out.println("rm command does not support deleting directories. Use rmdir instead.");
                }
            } else {
                System.out.println("File " + target + " does not exist.");
            }
        }
    }//End of rm function

    public void rmdir(String command, List<String> arguments)
    {
        if (arguments.size() != 1)
        {
            System.out.println(command+" command requires exactly one argument (directory name)");
        } else {
            String target = arguments.get(0);
            File dirToDelete = new File(CurrentDirectory + "\\" + target);
            if (dirToDelete.exists() && dirToDelete.isDirectory())
            {
                if (dirToDelete.delete()) {
                    System.out.println("Directory " + target + " deleted successfully");
                } else {
                    System.out.println("Failed to delete directory " + target);
                }
            } else
            {
                System.out.println("Directory " + target + " does not exist.");
            }
        }
    }//End of rmdir function

    public void cat(String command , List<String> arguments) throws FileNotFoundException {

        if (arguments.isEmpty() || arguments.size() > 2)
        {
            System.out.println(command+" should has at least one argument and at most two");
        }
        else if(arguments.size() == 1)
        {
            String filename1 = arguments.get(0);
            File file1 = new File(filename1);
            if(file1.exists())
            {
                String output = Read(file1);
                System.out.println(output);
            }
            else {
                System.out.println("File not found");
            }
        }
        else
        {
            String file_name1 = arguments.get(0);
            String file_name2 = arguments.get(1);
            File file1 = new File(file_name1);
            File file2 = new File(file_name2);
            if(file1.exists() && file2.exists())
            {

                String output = Read(file1);
                output+=Read(file2);
                System.out.println(output);
            }
            else {
                System.out.println("The files are not found");
            }
        }
    }//End of cat function


    public void cp(String command, List<String> arguments) {
        if(!arguments.get(0).equals("-r"))
        {
            if(arguments.size() != 2)
            {
                System.out.println(command+" should take 2 arguments 1st(Source file) 2nd(Destination file)");
            }
            else {
                String filename1 = arguments.get(0);
                String filename2 = arguments.get(1);
                try{
                    File file1 = new File(filename1);
                    File file2 = new File(filename2);

                    if(file1.isFile())
                    {
                        if(!file1.exists())
                        {
                            System.out.println("The source file does not exist");
                        }
                        else if(!file2.exists())
                        {
                            if(file2.createNewFile())
                            {
                                System.out.println(filename2+" is created");
                                //read data from the source file and write it in destination file
                                String output = Read(file1);//This function is made to read data from a file and return this data as a string
                                Write(file2 , output);//This function is made to write a string of data in a file
                                System.out.println("Copying Done");
                            }
                        }
                        else {
                            Write(file2 , Read(file1));//If two files exist read data from file1 and write it in file2
                            System.out.println("Copying process is Done");
                        }
                    }
                    else {
                        System.out.println(filename1+" and "+filename2 + " must be files to copy");
                    }

                }catch(Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }
        else {
            String dirname1 = arguments.get(1);
            String dirname2 = arguments.get(2);
            try{
                File file1 = new File(dirname1);
                File file2 = new File(dirname2);
                if(file1.isDirectory() && file2.isDirectory())
                {

                    System.out.println("Done");
                }
                else {
                    System.out.println(dirname1+ " and "+dirname2+" must be a directories");
                }

            }catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }//End of cp function


    public String Read(File file) throws FileNotFoundException {
        Scanner reader= new Scanner(file);
        StringBuilder output = new StringBuilder();
        while(reader.hasNextLine())
        {
            output.append(reader.nextLine());
            output.append("\n");
        }
        reader.close();
        return output.toString();
    }//End of Read

    public void Write(File file , String data) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(data);
        writer.close();
    }//End of Write


    public void CLI() throws FileNotFoundException {
        Scanner in  = new Scanner(System.in);
        String input;
        while(true)
        {
            System.out.print(CurrentDirectory);
            System.out.print(" $ ");
            input = in.nextLine();
            parser.parser(input);
            History_of_Commands.add(parser.getCommand());
            switch (parser.getCommand()) {
                case "help" -> help(parser.getCommand(), parser.getArguments());
                case "pwd" -> pwd(parser.getCommand(), parser.getArguments());
                case "echo" -> echo(parser.getCommand(), parser.getArguments());
                case "ls" -> ls(parser.getCommand(), parser.getArguments());
                case "mkdir" -> mkdir(parser.getCommand(), parser.getArguments());
                case "touch" -> touch(parser.getCommand(), parser.getArguments());
                case "wc" -> wc(parser.getCommand(), parser.getArguments());
                case "date" -> date(parser.getCommand(), parser.getArguments());
                case "cd" -> cd(parser.getCommand(), parser.getArguments());
                case "rm" -> rm(parser.getCommand(), parser.getArguments());
                case "rmdir" -> rmdir(parser.getCommand(), parser.getArguments());
                case "cat" -> cat(parser.getCommand() , parser.getArguments());
                case "cp" -> cp(parser.getCommand() , parser.getArguments());
                case "history" -> history(parser.getCommand(), parser.getArguments());
                case "exit" -> System.exit(0);
                default -> System.out.println("Invalid Command pls try again");
            }
        }
    }//End of process function
}//End of Class
