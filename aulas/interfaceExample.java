package aulas;

public class interfaceExample{
    public static void main(String[] args){
        Dog cao = new Dog();
        System.out.println(cao.som());
    }
}

interface Animal{
    String som();
}
interface Pet{
    boolean isPartner();
}

class Dog implements Animal, Pet{

    @Override
    public boolean isPartner() {
       
        return true;
    }

    @Override
    public String som() {
        
        return "Au Au";
    }

}