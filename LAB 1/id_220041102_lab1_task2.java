import java.util.*;

class movie{
    String title, director, producer,genre, unique_id;
    int cast_no;
    String[] cast;
    boolean isAvailable;

    movie(String title, String director, String producer, String genre, String unique_id,int cast_no, String[] cast) {
        this.title = title;
        this.director = director;
        this.producer = producer;
        this.genre = genre;
        this.unique_id = unique_id;
        this.cast_no = cast_no;
        this.cast = cast;
        this.isAvailable = true;
    }

    public void printdetails(){
        System.out.println("Title: " + title);
        System.out.println("Director: " + director);
        System.out.println("Producer: " + producer);
        System.out.println("Genre: " + genre);
        System.out.println("Unique ID: " + unique_id);
        System.out.println("Cast: ");
        for (int i=0;i<cast_no;i++){
            System.out.print(cast[i]);
            if(i!=cast_no)System.out.print(", ");
        }
        System.out.println();
    }
}
class user{
    String username,ID;
    String [] viewed_movie_dump;
    int rented_movie_count = 0;

    user(String username, String ID){
        this.ID = ID;
        this.username = username;
        this.viewed_movie_dump = new String[1996];
        this.rented_movie_count = 0;
    }
    boolean hasRanted(String mov_id){
        for(int i=0;i<rented_movie_count;i++){
            if(viewed_movie_dump[i].equals(mov_id))return true;
        }
        return false;
    }
    void rentMovie(String mov_id){
        if(!hasRanted(mov_id)){
            viewed_movie_dump[rented_movie_count] = mov_id;
            rented_movie_count++;
        }
    }
}
public class id_220041102_lab1_task2 {
    static Scanner sc = new Scanner(System.in);
    static movie[] movies_holder = new movie[1996];
    static int movie_count =0;
    static user[] users_holder = new user[1996];
    static int user_count =0;

    public static void main(String[] args){
        while (true){
            System.out.println("Select one of the following options available");
            System.out.println("1. Add movie");
            System.out.println("2. Register user");
            System.out.println("3. Search movie by Title");
            System.out.println("4. Search movie by ID");
            System.out.println("5. View Available movies");
            System.out.println("6. Rent movies");
            System.out.println("7. rented movies by the user");
            System.out.println("8. Exit");
            int choice = sc.nextInt();
            if(choice ==1){
                //add movie to the array
                System.out.println("Enter the title of the movie");
                String title = sc.next();
                System.out.println("Enter the director of the movie");
                String director = sc.next();
                System.out.println("Enter the producer of the movie");
                String producer = sc.next();
                System.out.println("Enter the genre of the movie");
                String genre = sc.next();
                System.out.println("Enter the unique ID of the movie");
                String unique_id = sc.next();
                System.out.println("Enter the number of cast members");
                int cast_no = sc.nextInt();
                String[] cast = new String[cast_no];
                System.out.println("Enter the cast members");
                for(int i=0; i<cast_no;i++)cast[i] = sc.next();
                movies_holder[movie_count] = new movie(title, director, producer, genre, unique_id, cast_no, cast);
                movie_count++;
            }
            else if( choice == 2 ){
                //add an user to the array
                System.out.println("Enter the username");
                String username = sc.next();
                System.out.println("Enter the ID");
                String ID = sc.next();
                users_holder[user_count] = new user(username, ID);
                user_count++;
            }
            else if (choice == 3){
                //search movie by title
                System.out.println("Enter the title of the movie");
                String name = sc.next();
                for (int i =0;i<movie_count;i++)
                    if(movies_holder[i].title.equals(name))movies_holder[i].printdetails();
            }
            else if(choice ==4){
                //search movie by ID
                System.out.println("Enter the ID of the movie");
                String name = sc.next();
                for (int i =0;i<movie_count;i++)
                    if(movies_holder[i].unique_id.equals(name))movies_holder[i].printdetails();
            }
            else if (choice == 5){
                //view available movies
                for (int i =0;i<movie_count;i++)
                    if(movies_holder[i].isAvailable)movies_holder[i].printdetails();
            }
            else if (choice == 6){
                //rent movie
                System.out.println("Enter the ID of the movie you want to rent");
                String name = sc.next();
                //check if the movie is available
                boolean is_Available =true;
                for (int i =0;i <movie_count;i++){
                    if(movies_holder[i].unique_id.equals(name)){
                        is_Available = movies_holder[i].isAvailable;
                        break;
                    }
                }
                if (is_Available){
                    System.out.println("Entern user id");
                    String name1 = sc.next();
                    for (int i =0;i <user_count;i++){
                        if(users_holder[i].ID.equals(name1)){
                            users_holder[i].rentMovie(name);
                            break;
                        }
                    }
                }
            }
            else if (choice == 7){
                //rented movies by the user
                System.out.println("Enter the ID of the user");
                String name = sc.next();
                for (int i=0;i<user_count;i++){
                    if(users_holder[i].ID.equals(name)){
                        for(int j =0;j<users_holder[i].rented_movie_count;j++){
                            System.out.println(users_holder[i].viewed_movie_dump[j]);
                        }
                        break;
                    }
                }
            }
            else break;
        }
    }
}
