import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//This class split the commands of the user
public class Parser {
    private String command;
    private List<String> arguments = new ArrayList<>();
    Parser()
    {

    }

     public void parser(String UserInput)
    {
        //User input will split into two parts command and arguments and put the both into list of strings which index 0 has the command and the rest of the list has the arguments
           List<String> wordlist = Arrays.asList(UserInput.split(" "));
           command = wordlist.get(0);
           arguments = new ArrayList<>(wordlist.subList(1,wordlist.size()));//Copy the elements from index 1 to last from parts array
    }
    public String getCommand() {
        return command;
    }

    public List<String> getArguments() {
        return arguments;
    }
}
