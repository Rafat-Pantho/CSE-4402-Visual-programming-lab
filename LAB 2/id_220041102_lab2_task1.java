import java.util.Scanner;

abstract class Animal{
    String name, ID;
    int age;

    Animal(String n, int a, String i){
        name = n;
        age = a;
        ID = i;
    }

    abstract void feed();
    abstract void makeSound();
    void displayInfo(){
        System.out.println("Name: "+ name + "\nAge: "+ age + "\nID: "+ ID);
    }
}

class Lion extends Animal{
    String size;
    boolean isAlpha;
    Lion (String n, int a, String i, String s, boolean isA){
        super(n,a,i);
        size = s;
        isAlpha = isA;
    }

    void feed(){
        System.out.println("Lion is eating meat");
    }
    void makeSound(){
        roar();
    }
    void roar(){
        System.out.println("The lion is roaring");
    }

    void hunt(){
        if (isAlpha)System.out.println("Lion is hunting alone");
        else System.out.println("Lion is hunting in a group");
    }
}

class Elephant extends Animal{
    double traunk_len, weight;
    Elephant(String n, int a, String i, double tl, double w){
        super(n,a,i);
        traunk_len = tl;
        weight = w;
    }
    void feed(){
        System.out.println("The hati is eating fruit!");
    }
    void makeSound(){
        System.out.println("The hati is making a sound!");
    }
    void sprayWater(){
        System.out.println("The hati is spraying water!");
    }
    void walk(){
        ////* sir didn't tell the specefic effect condition point for weight *////
        ////* So, I am assuming 500 kg as a point of effect *////
        if (weight > 500)System.out.println("The hati is walking slowly!");
        else System.out.println("The hati is walking normally!");
    }
}

class Parrot extends Animal{
    int vocabularySize;
    boolean cantalk;
    Parrot(String n, int a, String i, boolean ct, int vs){
        super(n,a,i);
        cantalk = ct;
        vocabularySize = vs;
    }
    void feed(){
        System.out.println("The Parrot is eating fruit!");
    }
    void makeSound(){
        System.out.println("Parrot is chirping");
    }

    void speak(Scanner sc){
        if (cantalk){
            System.out.println("how many words you want it to speak?");
            int n = sc.nextInt();
            for (int i =0;i<n; i++){
                System.out.println("Enter a word: ");
                String word = sc.next();
                if(word.length() <= vocabularySize)System.out.println(word);
                else System.out.println("Vocabulary length out of bound");
            }
        }
        else System.out.println("Parrot cannot talk");
    }
    
    void fly(){
        System.out.println("The parrot is flying");
    }
}

public class id_220041102_lab2_task1 {
    static Scanner sc = new Scanner(System.in);
    public static void main(String [] args){
        Lion l1 = new Lion("Abr", 47, "Lion - 1029", "Large", true);
        l1.feed();
        l1.makeSound();
        l1.hunt();
        l1.roar();
        l1.displayInfo();

        Elephant e1 = new Elephant("Abacus", 5, "Elephant - 2341",3.5,45);
        e1.feed();
        e1.makeSound();
        e1.sprayWater();
        e1.walk();
        e1.displayInfo();

        Parrot p1 = new Parrot("Solp", 1, "Parrot - 9842",true,6 );
        Parrot p2 = new Parrot("Sled", 2, "Parrot -9829",false,0 );
        p1.feed();
        p1.fly();
        p1.makeSound();
        p1.displayInfo();
        p2.displayInfo();
        p2.speak(sc);
        p1.speak(sc);
    }
}
