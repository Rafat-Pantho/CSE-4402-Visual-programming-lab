import java.util.Scanner;

class CustomMadeStack{
    private int[] arrayForStack;
    private int top, capacity;

    CustomMadeStack(int c){
        capacity = c;
        arrayForStack = new int[capacity];
        top = -1;
    }
    public void push(int a){
        if (top == capacity - 1){
            System.out.println("Overflow");
            return;
        }
        top++;
        arrayForStack[top] = a;
        printTheStack();
    }

    public int pop(){
        if(top == -1){
            System.out.println("Underflow");
            return -1;
        }
        return arrayForStack[top--];
    }

    public int MaxVal(){
        int a = arrayForStack[0];
        for (int i = 0; i <= top; i++) if (arrayForStack[i] > a)a = arrayForStack[i];
        return a;
    }

    public int MinVal(){
        int a = arrayForStack[0];
        for (int i = 0; i <= top; i++) if (arrayForStack[i] < a)a = arrayForStack[i];
        return a;
    }

    public int Size (){
        return top+1;
    }

    void printTheStack(){
        if (top == -1){
            System.out.println("Stack is empty");
            return;
        }
        System.out.println("Stack: ");
        for (int i =0 ; i<=top; i++){
            System.out.print(arrayForStack[i]+ " ");
        }
        System.out.println();
    }
}
public class id_220041102_lab2_task2 {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.print("Enter the capacity of the stack: ");
        int cap  = sc.nextInt();
        CustomMadeStack s = new CustomMadeStack(cap);

        System.out.println("1. Push\n2. Pop\n3. Max\n4. Min\n5. Size\n6. Exit");
        while(true){
            System.out.print("Choose option: ");
            int c = sc.nextInt();
            if (c== 1){
                System.out.print("Enter a number: ");
                int n = sc.nextInt();
                s.push(n);
            }
            else if (c==2)s.pop();
            else if (c==3)System.out.println("Max value: "+ s.MaxVal());
            else if (c==4)System.out.println("Min value: "+ s.MinVal());
            else if (c==5)System.out.println("Size: "+ s.Size());
            else if (c==6)break;
            else System.out.println("Invalid input");
        }
    }
}
