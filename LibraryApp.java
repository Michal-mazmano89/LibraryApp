package pl.mazmano89.library;

import java.io.*;
import java.util.*;

public class LibraryApp {
    public static void main(String[] args){



        System.out.println("---- Library App ----");


        Scanner userIn = new Scanner(System.in).useDelimiter("\\n");

        List<Author> authorsList = new ArrayList<>();
        List<Book> booksList = new ArrayList<>();

        Author king = new Author("Stephen", "King");
        Author terry = new Author("Terry", "Pratchett");
        Author aghata = new Author("Aghata", "Christie");

        Book carrie = new Book("Carrie", king, Genre.HORROR);
        Book guards = new Book("Guard! Guard!", terry, Genre.FANTASY);
        Book nile = new Book("Death on the Nile", aghata, Genre.MYSTERY);

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
                    addingAuthorLoop(userIn, authorsList);
                } else if (userType == 2) {
                    showAuthorList(authorsList);
                } else if (userType == 3) {
                    showBookList(booksList);
                } else if (userType == 4) {
                    addBook(userIn, authorsList, booksList);
                } else if (userType == 5) {
                    saveData(authorsList, booksList);
                } else if (userType == 6){
                    loadData(authorsList,booksList);
                }else if (userType == 0) {
                    System.out.println("Bye bye.");
                    break;
                } else {
                    System.out.println("Unknown command. Please type number from main menu.");
                    }
            } catch (Exception e) {
                System.out.println("Something went wrong, please try again.");
                userIn.next();
              }
        }
    }

    private static void saveData(List<Author> authorsList, List<Book> booksList) {
        try {
            FileOutputStream is = new FileOutputStream("src/pl/mazmano89/library/files/authorsBackup.ser");
            ObjectOutputStream os = new ObjectOutputStream(is);
            os.writeObject(authorsList);

            FileOutputStream is2 = new FileOutputStream("src/pl/mazmano89/library/files/booksBackup.ser");
            ObjectOutputStream os2 = new ObjectOutputStream(is2);
            os2.writeObject(booksList);
            System.out.println("--- Author and book data exported into file(s) ---");
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private static void loadData(List<Author> authorsList, List<Book> booksList) throws IOException, ClassNotFoundException{
        try {
            FileInputStream fisA = new FileInputStream("src/pl/mazmano89/library/files/authorsBackup.ser");
            ObjectInputStream oisA = new ObjectInputStream(fisA);
            authorsList.clear();
            authorsList.addAll((List<Author>) oisA.readObject());

            FileInputStream fisB = new FileInputStream("src/pl/mazmano89/library/files/booksBackup.ser");
            ObjectInputStream oisB = new ObjectInputStream(fisB);
            booksList.clear();
            booksList.addAll((List<Book>) oisB.readObject());

            Author.setID(authorsList.size());

            System.out.println("--- Author and book data imported from file(s) ---");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static void addBook(Scanner userIn, List<Author> authorsList, List<Book> booksList) {
        String bookTitle = "";
        Author newAuthor = null;
        System.out.println("Type book's title: ");
        bookTitle = userIn.next();

        boolean authorLoop = true;
        while(authorLoop){
            System.out.println("Type author's first name and last name like Firstname Lastname: ");

        try {

            String authorsName = userIn.next();
            boolean authorCheckLoop = true;
            while (authorCheckLoop) {
                String[] author = authorsName.split(" ", 2);
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
            if (Objects.equals(authorTmpName, "q")){break;}
            System.out.println("Imie autora: " + authorTmpName);
            userIn.nextLine();
            System.out.print("Type author's last name: ");
            String authorTmpLName = userIn.next();
            if (Objects.equals(authorTmpLName, "q")){break;}
            System.out.println("Nazwisko autora: " + authorTmpLName);
            Author author = new Author(authorTmpName, authorTmpLName);
                if(authorsList.contains(author)){
                    System.out.println("Author is already in database. Please try again with different data.");
                    i--;
                    Author.decreaseAuthorID();
                    continue;
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
        System.out.println("Type 5 to save all authors and books into file.");
        System.out.println("Type 6 to import all authors and books from file (replaces all existing data!).");
        System.out.println("Type 0 quit.");
        System.out.print("Type here: ");
    }



}
