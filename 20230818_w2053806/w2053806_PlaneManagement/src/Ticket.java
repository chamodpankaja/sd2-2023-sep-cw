import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ticket{
    private String row;
    private int seatNumber;
    private double price;
    private Person person;

    public Ticket(String row, int seatNumber, double price, Person person) {
        this.row = row;
        this.seatNumber = seatNumber;
        this.price = price;
        this.person = person;
    }

    // constructor for text file deleting method
    public Ticket(String row, int seatNumber) {
        this.row = row;
        this.seatNumber = seatNumber;
    }

    //method for print information
    public String printInfo() {
        System.out.println("Ticket Information:");
        System.out.println("Row: " + getRow());
        System.out.println("Seat: " + getSeatNumber());
        System.out.println("Price: £" + getPrice()+"\n");
        System.out.println("Person Information:");
        System.out.println("Name :" +getPerson().getName());
        System.out.println("Surname : "+ getPerson().getSurname());
        System.out.println("Mail : "+ getPerson().getEmail());
        return " ";
    }
    //method for saving ticket information to a text file
    public void save(){
        try {
            FileWriter file = new FileWriter(row+seatNumber+ ".txt");
            file.write("Ticket Information:\n");
            file.write("Row: " + row + "\n");
            file.write("Seat: " + seatNumber + "\n");
            file.write("Price: £" + price + "\n\n");

            file.write("Personal Information:\n");
            file.write("Name: " +person.getName() + "\n");
            file.write("Surname: " +person.getSurname()+ "\n");
            file.write("Email: " +person.getEmail() + "\n");

            file.close();
            System.out.println("\nTicket information has been saved to " + row + seatNumber + ".txt");
        }catch(IOException e){

            System.out.println("Error while writing to a file.");
            e.printStackTrace();

        }
    }
    //method for deleting ticket information from a text file
    public void deleteFile() {
        String fileName = row + seatNumber + ".txt";
        File fileToDelete = new File(fileName);

        try {
            if (fileToDelete.exists()) {
                if (fileToDelete.delete()) {
                    System.out.println( fileName + " has been deleted.");
                } else {
                    System.out.println("Failed to delete text file '" + fileName + "'.");
                }
            } else {
                System.out.println( fileName + " does not exist.");
            }
        } catch (SecurityException e) {
            System.out.println("Error while deleting a file.");

        }
    }
    //getters and setters
    public String getRow() {
        return row;
    }

    public void setRow(String row) {

        this.row = row;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {

        this.seatNumber = seatNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}