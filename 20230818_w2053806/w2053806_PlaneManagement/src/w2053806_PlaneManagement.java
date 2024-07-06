import java.util.InputMismatchException;
import java.util.Scanner;

public class w2053806_PlaneManagement {
    //creating 2D array to save reserved ticket
    static Ticket[][] tickets = new Ticket[4][];
    //String array for find first available and  print tickets info method
    static  String [] rows={"A","B","C","D"};
    //Arrays for seat booking process
    static int [] RowA= new int[14];
    static int [] RowB= new int[12];
    static int [] RowC= new int[12];
    static int [] RowD= new int[14];


    public static void main(String[] args) {
        initializingTickets();

        System.out.println("""
                \n------------------------------------------------------------
                -     Welcome to the plane Management application          -
                ------------------------------------------------------------
                """);
        //string array for main menu
        String[] MenuQuestions = {
                "\n*************************************************",
                "*                MENU OPTIONS                   *",
                "*************************************************",
                "1) Buy a seat",
                "2) Cancel a seat",
                "3) Find first available seat",
                "4) Show seating plan",
                "5) Print tickets information and total sales",
                "6) Search ticket",
                "0) Quit",
                "*************************************************"
        };
        //menu printing
        while (true){
            for(String temp:MenuQuestions){
                System.out.println(temp);
            }

            try {
                Scanner input = new Scanner(System.in);
                System.out.println("Please select an option: ");
                int option = input.nextInt();

                switch (option){//switch case for menu options
                    case 1:
                        buy_seat();
                        break;
                    case 2:
                        cancel_seat();
                        break;
                    case 3:
                        find_first_available();
                        break;
                    case 4:
                        show_seating_plan("A",RowA);
                        show_seating_plan("B",RowB);
                        System.out.println();
                        show_seating_plan("C",RowC);
                        show_seating_plan("D",RowD);
                        break;
                    case 5:
                        print_tickets_info();
                        break;
                    case 6:
                        search_ticket();
                        break;
                    case 0:
                        System.out.println("Thank You for choosing us....");
                        return;
                    default:
                        System.out.println("Wrong input...!\n");
                }

            }catch (InputMismatchException e){
                System.out.println("Please input valid option...!");

            }
        }
    }
    public static void initializingTickets(){
        //Initializing 2D array for save booked tickets
        tickets[0]=new Ticket[14];
        tickets[1]=new Ticket[12];
        tickets[2]=new Ticket[12];
        tickets[3]=new Ticket[14];
    }
    public static void buy_seat(){// buy seat method
        Scanner input = new Scanner(System.in);

        System.out.println("Enter seat row letter (A-D) :");// user input for seat row
        String rowLetter=input.nextLine().toUpperCase();// convert user input to uppercase

        System.out.println("Enter seat number: ");//user input for seat number
        int seatNumber=input.nextInt();

        input.nextLine();

        //create array to assign seat array
        int [] selectedRow;

        switch(rowLetter){//switch case for assigning user input to the correct seat row
            case "A":
                selectedRow= RowA;
                break;
            case "B":
                selectedRow= RowB;
                break;
            case "C":
                selectedRow= RowC;
                break;
            case "D":
                selectedRow= RowD;
                break;
            default:
                System.out.println("Invalid seat row...!\n");
                return;
        }

        if(seatNumber>=1 && seatNumber<=selectedRow.length){// check if the seat number is in valid range

            if(selectedRow[seatNumber-1]==1){//check if the seat is already booked

                System.out.println("Sorry, seat row " + rowLetter + ", seat number " + seatNumber + " is already booked...!\n");

            }else{
                selectedRow[seatNumber-1]=1;//book the seat
                //create a new person object
                Person person = new Person("name","surname","email");

                System.out.println("enter your name:");
                String name= input.nextLine();
                person.setName(name);//set name

                System.out.println("enter your surname: ");
                String surName= input.nextLine();
                person.setSurname(surName);//set surname

                //email validating process
                String email;
                do{
                    System.out.println("Enter you email: ");
                    email= input.nextLine();
                    if(!validateEmail(email)){
                        System.out.println("\nInvalid email format.Please enter a valid email address...!\n");
                    }
                }while(!validateEmail(email));
                person.setEmail(email);//set email

                System.out.printf("\nSeat row %s, seat number %d, has been booked for %s %s. Email:%s%n",
                        rowLetter, seatNumber, person.getName(), person.getSurname(), person.getEmail());

                //create new ticket object
                Ticket ticket = new Ticket(rowLetter,seatNumber,price(seatNumber),person);

                //set attributes
                ticket.setRow(rowLetter);
                ticket.setSeatNumber(seatNumber);
                ticket.setPrice(price(seatNumber));
                ticket.setPerson(person);

                //save booked tickets information in the "tickets" array
                switch (rowLetter){
                    case "A":
                        tickets[0][seatNumber-1]= ticket;
                        break;
                    case "B":
                        tickets[1][seatNumber-1]= ticket;
                        break;
                    case "C":
                        tickets[2][seatNumber-1]= ticket;
                        break;
                    case "D":
                        tickets[3][seatNumber-1]= ticket;
                        break;
                }
                //save ticket information to text file
                ticket.save();
            }


        }else{
            System.out.println("Invalid seat number...!");
        }
    }

    public static void cancel_seat(){//cancel seat method

        Scanner input = new Scanner(System.in);

        System.out.println("Enter seat row letter (A-D) :");// user input for seat row
        String rowLetter=input.nextLine().toUpperCase();//convert user input to uppercase

        System.out.println("Enter seat number: ");//user input for seat number
        int seatNumber=input.nextInt();

        //create array to assign seat array
        int [] cancelRow;

        switch(rowLetter){ //switch case for assigning user input to the correct seat row
            case "A":
                cancelRow= RowA;
                break;
            case "B":
                cancelRow= RowB;
                break;
            case "C":
                cancelRow= RowC;
                break;
            case "D":
                cancelRow= RowD;
                break;
            default:
                System.out.println("Invalid seat row...!\n");
                return;
        }
        if(seatNumber>=1 && seatNumber<= cancelRow.length){// check if the seat number is in valid range

            if(cancelRow[seatNumber-1]==0){//check if the seat is already available

                System.out.println("Seat row "+rowLetter+",seat number "+seatNumber+" is already available.");
            }else{
                cancelRow[seatNumber-1]=0;//cancel the seat
                System.out.println("Seat row "+rowLetter+" ,seat number "+seatNumber+", has been cancelled successfully.\n");

                //create new ticket2 object for delete text file
                Ticket ticket2=new Ticket(rowLetter,seatNumber);

                //delete the text file of the ticket after cancelling a seat
                ticket2.deleteFile();
            }
            //clear cancelled tickets information from the "tickets" array
            switch (rowLetter){
                case "A":
                    tickets[0][seatNumber-1]= null;
                    break;
                case "B":
                    tickets[1][seatNumber-1]= null;
                    break;
                case "C":
                    tickets[2][seatNumber-1]= null;
                    break;
                case "D":
                    tickets[3][seatNumber-1]= null;
                    break;
            }


        }else{
            System.out.println("Invalid seat number...!");
        }
    }

    public static void find_first_available(){

        for (String temp:rows){//for each loop iterating in "rows" array
            int [] findRow = new int[0];//create array to assign seat array

            switch (temp){
                case"A":
                    findRow=RowA;
                    break;
                case"B":
                    findRow=RowB;
                    break;
                case"C":
                    findRow=RowC;
                    break;
                case"D":
                    findRow=RowD;
                    break;
                default:
                    System.out.println("Invalid seat row...!\n");
                    break;
            }
            int i=0;
            while(i < findRow.length){// check all seat arrays
                if(findRow[i]==0){// if checking element is == to 0 then print the seat row and seat number and return.
                    System.out.println("\nRow "+temp+" seat "+(i+1)+" is available.");
                    return;
                }
                i++;// increment i
            }
        }
        System.out.println("Sorry,All seats are booked...");
    }
    // create show_seat planning array and pass the string row and int array as the arguments
    public static void show_seating_plan(String row, int [] array){

        System.out.print(row+": ");
        for (int temp:array){// for each loop iterating in given array
            if(temp==0){
                System.out.print("O ");//if element == 0 convert into O
            }else{
                System.out.print("X ");//if not convert into X
            }
        }
        System.out.println();
    }
    public static double price(int seatNumber){//method for pricing tickets
        if(seatNumber>0 && seatNumber<6){
            return 200.00;

        } else if (seatNumber>5 && seatNumber<10) {
            return 150.00;

        } else if (seatNumber>9 && seatNumber<15) {
            return 180.00;
        }else {
            return 0;
        }
    }

    public static void print_tickets_info(){//print_ticket_info method
        boolean booked=false;//create boolean variable an assign it to false
        double total=0;//total price for booked tickets

        //iterate through tickets array
        for (int i = 0; i < tickets.length; i++) {
            String rowLetter=rows[i];// get row letter
            Ticket[] rowTicket=tickets[i];//get the array of tickets for this row

            for (int j = 0; j < rowTicket.length; j++) {
                Ticket ticket= rowTicket[j];//get the ticket position in the row

                if(ticket!=null){
                    booked=true;
                    double ticketPrice=price(j+1);//calculate price of the ticket

                    total+=ticketPrice;//total ticket price calculate

                    //print ticket information
                    System.out.printf("""
                                    \nSeat row: %s
                                    seat number: %d 
                                    Booked by: %s %s 
                                    Email: %s 
                                    Price: £%.1f%n""",
                            rowLetter,(j+1),ticket.getPerson().getName(),ticket.getPerson().getSurname(),ticket.getPerson().getEmail(),ticket.getPrice());

                }

            }
        }
        if(!booked){
            //if no tickets are booked print following line
            System.out.println("No tickets have been booked yet.");
        }else{
            System.out.println("\nTotal is: £"+total);//print the total ticket price
        }


    }
    public static void search_ticket(){
        Scanner input = new Scanner(System.in);

        System.out.println("Enter seat row letter (A-D) :");// user input for seat row
        String rowLetter=input.nextLine().toUpperCase();//convert into uppercase

        System.out.println("Enter seat number: ");//user input for seat number
        int seatNumber=input.nextInt();

        int [] selectedRow;//create array to assign seat array
        int number;//variable to access the 2D "tickets" array

        switch(rowLetter){
            case"A":
                selectedRow=RowA;
                number=0;
                break;
            case"B":
                selectedRow=RowB;
                number=1;
                break;
            case"C":
                selectedRow=RowC;
                number=2;
                break;
            case"D":
                selectedRow=RowD;
                number=3;
                break;
            default:
                System.out.println("Invalid seat row...!\n");
                return;

        }
        if(seatNumber>=1 && seatNumber<= selectedRow.length){// check if the seat number is in valid range
            if(selectedRow[seatNumber-1]==1){// check if the seat is booked

                //print information
                System.out.println("\nSeat: "+rowLetter+seatNumber+"\n");
                Ticket bookedTicket = tickets[number][seatNumber-1];
                System.out.println(bookedTicket.printInfo());
            }else{
                //if seat is not booked print following line
                System.out.println("Seat row "+rowLetter+",seat number "+seatNumber+" is already available.");
            }
        }else{
            System.out.println("Invalid seat number...!");
        }
    }
    //simple email validator for email validation
    public static boolean validateEmail(String email){
        //email should  include "@" and "." and email length should be >= 10
        return email.contains("@") && email.contains(".") && email.length()>=10;
    }

}