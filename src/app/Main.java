package app;

import audit.AuditLogger;
import model.StudentAccount;
import repository.AccountRepository;

/**
 * UniAccounts - TP1 (Creational Design Patterns) : demonstration.
 * Groupe 04 : Aicha Diab - Radhia Bahri
 */
public class Main {
    public static void main(String[] args) {

        System.out.println("=== UniAccounts - TP1 (Creational Patterns) ===");
        System.out.println("Equipe : Aicha Diab - Radhia Bahri (Groupe 04)");

        AccountRepository repo = AccountRepository.getInstance();

        // ---------- 1) SINGLETON ----------
        System.out.println("\n--- Singleton ---");
        System.out.println("Meme depot ? " + (repo == AccountRepository.getInstance()));
        System.out.println("Meme logger ? " + (AuditLogger.getInstance() == AuditLogger.getInstance()));

        // ---------- 2) BUILDER ----------
        System.out.println("\n--- Builder ---");
        StudentAccount minimal = new StudentAccount.Builder("202439405009", "Radhia Bahri", "r95657730@gmail.com").build();
        System.out.println(minimal);

        StudentAccount full = new StudentAccount.Builder("202439429612", "Aicha Diab", "aicha.lat@gmail.com")
                .level("L4").major("Informatique").addCourse("POO").addCourse("BDD").build();
        System.out.println(full);

        try {
            new StudentAccount.Builder("202439429612", "Aicha Diab", "email-sans-arobase").build();
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur attendue : " + e.getMessage());
        }
        try {
            new StudentAccount.Builder("202439429612", "Aicha Diab", "aicha.lat@gmail.com").level("L9").build();
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur attendue : " + e.getMessage());
        }

        // ---------- 3) PROTOTYPE ----------
        System.out.println("\n--- Prototype ---");
        StudentAccount template = new StudentAccount.Builder("TEMPLATE", "Modele", "modele@univ.dz")
                .level("L4").major("Informatique").addCourse("POO").addCourse("BDD").build();

        StudentAccount s1 = template.clone().withIdentity("202439429612", "Aicha Diab", "aicha.lat@gmail.com");
        StudentAccount s2 = template.clone().withIdentity("202439405009", "Radhia Bahri", "r95657730@gmail.com");
        repo.add(s1);
        repo.add(s2);
        System.out.println(s1);
        System.out.println(s2);
        System.out.println("Meme objet que le modele ? " + (s1 == template));
        System.out.println("Modele inchange : " + template.getFullName());

        // Copie profonde : modifier s1 ne touche ni s2 ni le modele
        s1.enroll("IA");
        System.out.println("Modules de s1      : " + s1.getCourses());
        System.out.println("Modules de s2      : " + s2.getCourses());
        System.out.println("Modules du modele  : " + template.getCourses());

        // ---------- Doublon ----------
        System.out.println("\n--- Test doublon ---");
        try {
            repo.add(template.clone().withIdentity("202439429612", "Aicha Diab", "aicha.lat@gmail.com"));
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur attendue : " + e.getMessage());
        }

        System.out.println("\nNombre de comptes : " + repo.count());
        System.out.println();
        AuditLogger.getInstance().printAll();
    }
}
