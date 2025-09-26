abstract class Animal {
    abstract public void sound();

    public void displayName(String animalName){
        System.out.println("Animal name is : " + animalName);
    }
}
class Cat extends Animal{

    @Override
    public void sound() {
        System.out.println("Cat meow");
    }

}

public class AbstractDemo{
    public static void main(String[] args) {
        Animal animalName = new Cat();
        animalName.displayName("cat");
        animalName.sound();
    }
}
