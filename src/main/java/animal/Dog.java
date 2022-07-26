package animal;

public class Dog implements Pet {
    @Override
    public void sayPet() {
        System.out.println("Hello World, I am Pet, actually a dog");
    }

    public void sayDog(){
        System.out.println("Dog Dog Dog");
    }
}
