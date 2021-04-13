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


abstract class Pet{
    abstract boolean isPartner();
}
class Dog extends Pet implements Animal{

    @Override
    public boolean isPartner() {
       
        return true;
    }

    @Override
    public String som() {
        
        return "Au Au";
    }

}