package pl.recstudio.library;

import java.util.*;

public class LibraryApp {
    public static void main(String[] args){



        System.out.println("---- Library App ----");


        Scanner userIn = new Scanner(System.in).useDelimiter("\\n");


        Author king = new Author("Stephen", "King");
        Author terry = new Author("Terry", "Pratchett");
        Author aghata = new Author("Aghata", "Christie");

        Book carrie = new Book("Carrie", king, Genre.HORROR);
        Book guards = new Book("Guard! Guard!", terry, Genre.FANTASY);
        Book nile = new Book("Death on the Nile", aghata, Genre.MYSTERY);

        List<Author> authorsList = new ArrayList<>();
        List<Book> booksList = new ArrayList<>();

        authorsList.add(king);
        authorsList.add(terry);
        authorsList.add(aghata);

        booksList.add(carrie);
        booksList.add(guards);
        booksList.add(nile);


        while(true) {
            showMainMenu();
            try {
                int userType = userIn.nextInt();
                if (userType == 1) {
                    addingAuthorLoop(userIn, authorsList);}
                else if (userType == 2) {showAuthorList(authorsList);}
                else if (userType == 3) {showBookList(booksList);}
                else if (userType == 4) {
                    String bookTitle = "";
                    Author newAuthor = null;
                    System.out.println("Type book's title: ");
                    bookTitle = userIn.next();

                    boolean authorLoop = true;
                    while(authorLoop){
                        System.out.println("Type author's first name and last name like firstname/lastname: ");

                    try {

                        String authorsName = userIn.next();
                        boolean authorCheckLoop = true;
                        while (authorCheckLoop) {


                            String[] author = authorsName.split("/", 2); //If no errors change limit
                            newAuthor = new Author(author[0], author[1]);
                            int newAuthorHash = newAuthor.hashCode();

                            for (Author a : authorsList) {
                                int hash = a.hashCode();
                                if (newAuthorHash == hash) {
                                    System.out.println("Author found.");
                                    newAuthor = a;
                                    Author.decreaseAuthorID();
                                    System.out.println();
                                    authorCheckLoop = false;
                                    authorLoop = false;


                                    boolean bookGenreLoop = true;

                                    while (bookGenreLoop) {
                                        System.out.println("Please enter book's genre from the list below or type q to discard changes.");
                                        System.out.println(Arrays.toString(Genre.values()));
                                        String bookGenre = userIn.next().toUpperCase();
                                        try {
                                           for (Genre g : Genre.values()) {
                                                if (bookGenre.equals(g.toString())) {
                                                    Book newBook = new Book(bookTitle, newAuthor, g);
                                                    booksList.add(newBook);
                                                    System.out.println("Book has been added.");
                                                    bookGenreLoop = false;
                                                    break;
                                                } else if (bookGenre.equals("Q")) {
                                                    System.out.println("Genre not found, choose the closest genre.");
                                                }
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Genre not found, choose the closest genre.");
                                        }
                                    } break;
                      }
                            }
                        if(authorCheckLoop){
                            System.out.println("Author not found, please try again or add new author from main menu.");
                            Author.decreaseAuthorID();
                            authorsName = userIn.next();
                        }
                        }
                    } catch (Exception e) {
                        System.out.println("Incorrect input. Try again");
                    }

                    }


                } else if (userType == 0) {
                    System.out.println("Bye bye.");
                    break;
                } else {
                    System.out.println("Unknown command. Please type number from main menu.");
                    }
            } catch (Exception e) {
                e.printStackTrace(System.out);
                System.out.println("Something went wrong. Please try again.");
                userIn.nextLine();
                    }
                }
            }




    private static void showAuthorList(List<Author> authorsList) {
        for(Author a: authorsList){
            System.out.println(a.getInfo());
        }
    }

    private static void showBookList(List<Book> booksList){
        System.out.println("---- List of books ----");
        for(Book a: booksList){
            System.out.println(a.getInfo());
        }
    }

    private static void addingAuthorLoop(Scanner userIn, List<Author> authorsList) {
        for(int i = Author.getNextID();;i++){
            System.out.println("---(type q to quit)---");
            System.out.print("Type author's name: ");
            String authorTmpName = userIn.next();
            System.out.println("Imie autora: " + authorTmpName);
            if (Objects.equals(authorTmpName, "q")){break;}
            userIn.nextLine();
            System.out.print("Type author's last name: ");
            String authorTmpLName = userIn.next();
            System.out.println("Nazwisko autora: " + authorTmpLName);
            if (Objects.equals(authorTmpLName, "q")){break;}
            Author author = new Author(authorTmpName, authorTmpLName);
            for(Author a: authorsList){
                if(a.equals(author)){
                    System.out.println("Author is already in database. Please try again with different data.");
                    i--;
                    break;
                }
            }
            authorsList.add((i),author);
            System.out.println("Author added.");
        }
    }

    private static void showMainMenu() {

        System.out.println("Type 1 to add an author.");
        System.out.println("Type 2 to list all authors.");
        System.out.println("Type 3 to list all books.");
        System.out.println("Type 4 to add a book.");
        System.out.println("Type 0 quit.");
        System.out.print("Type here: ");
    }



}
