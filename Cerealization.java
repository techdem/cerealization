import java.util.*;
import javax.swing.*;
import java.io.*;

public class Cerealization {

    public static void main(String[] args) throws Exception {
        int userOption;
        String name;
        ArrayList<Name> firstList = new ArrayList<>();
        ArrayList<Name> secondList = new ArrayList<>();
        String[] options = new String[] {"Display First List", "Display Second List", "Add Name to First List", "Add Name to Second List", "Search Second List"};

        writeList(firstList, true);
        writeList(secondList, false);
        
        do {
            userOption = JOptionPane.showOptionDialog(null, "Please choose one of the following options: ", "Ryanair", 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
            System.out.println("User selection: " + userOption);
            
            switch (userOption) {
                case 0:
                    JOptionPane.showMessageDialog(null, readList(true, ""));
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, readList(false, ""));
                    break;
                case 2:
                    name = JOptionPane.showInputDialog(null, "Please type a name: ");
                    
                    if(name != null) {
                        firstList.add(new Name(name));
                        writeList(firstList, true);
                        JOptionPane.showMessageDialog(null, "Name added to the first list!");
                    }
                    break;
                case 3:
                    name = JOptionPane.showInputDialog(null, "Please type a name: ");
                    
                    if(name != null) {
                        secondList.add(new Name(name));
                        writeList(secondList, false);
                        JOptionPane.showMessageDialog(null, "Name added to the second list!");
                    }
                    break;
                case 4:
                    String search = JOptionPane.showInputDialog(null, "Please type a name: ");
                    readList(false, search);
                default:
                    break;
            }
        } while (userOption != -1);
    }
    
    public static void writeList(ArrayList<Name> list, boolean s) throws Exception {

        FileOutputStream outputStream = (s) ? new FileOutputStream("firstList.ser") : new FileOutputStream("secondList.ser");
        ObjectOutputStream outputStreamOut = new ObjectOutputStream(outputStream);
        
        outputStreamOut.writeObject(list);
        outputStreamOut.close();
        outputStream.close();
        
        System.out.println("Serializing finished");
    }
    
    public static String readList(boolean s, String search) throws Exception {
        boolean found = false;
        String output = "";
        
        FileInputStream inputStream = (s) ? new FileInputStream("firstList.ser") : new FileInputStream("secondList.ser");
        ObjectInputStream inputStreamIn = new ObjectInputStream(inputStream);
        ArrayList<Name> readingList = (ArrayList)inputStreamIn.readObject();
        
        inputStream.close();
        inputStreamIn.close();

        System.out.println("List contents: ");
        
        for(Name n : readingList) {
            output+= n.getName() + "\n";
            if(search.equals(n.getName())) {
                JOptionPane.showMessageDialog(null, "Name found in second list !!!");
                found = true;
            }
            System.out.println(n.getName());
        }
        
        if(!found && !search.equals("")) {
            JOptionPane.showMessageDialog(null, "Name NOT found in second list !!!");
        }
        
        System.out.println("De-serializing finished");
        return output;
    }
}

class Name implements java.io.Serializable {
    private String name;
    
    public Name(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
