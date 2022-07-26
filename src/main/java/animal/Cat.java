package animal;

public class Cat implements Pet {
    @Override
    public void sayPet() {
        System.out.println("Hello World, I am Pet, actually a cat");
    }

    public void sayCat(){
        System.out.println("Cat Cat Cat");
    }
}
