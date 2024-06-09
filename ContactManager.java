import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Contact implements Serializable {
    private String name;
    private String phoneNumber;
    private String email;

    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phoneNumber + ", Email: " + email;
    }
}

public class ContactManager {
    private ArrayList<Contact> contacts;
    private static final String FILE_NAME = "contacts.dat";

    public ContactManager() {
        contacts = new ArrayList<>();
        loadContacts();
    }

    public void addContact(String name, String phoneNumber, String email) {
        contacts.add(new Contact(name, phoneNumber, email));
        saveContacts();
    }

    public void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
        } else {
            for (int i = 0; i < contacts.size(); i++) {
                System.out.println((i + 1) + ". " + contacts.get(i));
            }
        }
    }

    public void editContact(int index, String name, String phoneNumber, String email) {
        if (index >= 0 && index < contacts.size()) {
            Contact contact = contacts.get(index);
            contact.setName(name);
            contact.setPhoneNumber(phoneNumber);
            contact.setEmail(email);
            saveContacts();
        } else {
            System.out.println("Invalid contact index.");
        }
    }

    public void deleteContact(int index) {
        if (index >= 0 && index < contacts.size()) {
            contacts.remove(index);
            saveContacts();
        } else {
            System.out.println("Invalid contact index.");
        }
    }

    @SuppressWarnings("unchecked")
    private void loadContacts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            contacts = (ArrayList<Contact>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File not found, initialize an empty contact list
            contacts = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveContacts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ContactManager manager = new ContactManager();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nContact Manager Menu:");
            System.out.println("1. Add new contact");
            System.out.println("2. View contacts");
            System.out.println("3. Edit contact");
            System.out.println("4. Delete contact");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    manager.addContact(name, phoneNumber, email);
                    break;
                case 2:
                    manager.viewContacts();
                    break;
                case 3:
                    System.out.print("Enter the contact index to edit: ");
                    int editIndex = scanner.nextInt() - 1;
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new phone number: ");
                    String newPhoneNumber = scanner.nextLine();
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.nextLine();
                    manager.editContact(editIndex, newName, newPhoneNumber, newEmail);
                    break;
                case 4:
                    System.out.print("Enter the contact index to delete: ");
                    int deleteIndex = scanner.nextInt() - 1;
                    scanner.nextLine(); // consume newline
                    manager.deleteContact(deleteIndex);
                    break;
                case 5:
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
