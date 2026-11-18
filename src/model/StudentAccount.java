package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * UniAccounts - TP1 : compte d'un etudiant.
 *
 * @author Aicha Diab
 * @author Radhia Bahri
 *
 * Patterns utilises dans cette classe :
 *  - Builder   : StudentAccount.Builder (classe interne) construit le compte pas a pas.
 *  - Prototype : StudentAccount implemente Cloneable ; clone() copie un modele,
 *                withIdentity() change seulement l'identite de la copie.
 */
// Prototype : StudentAccount est le ConcretePrototype (il sait se cloner)
public class StudentAccount implements Cloneable {

    // Niveaux autorises
    private static final List<String> LEVELS = Arrays.asList("L1", "L2", "L3", "L4", "M1", "M2");

    // Champs obligatoires
    private String id;
    private String fullName;
    private String email;

    // Champs optionnels
    private String phone;          // defaut : null
    private String level;          // defaut : "L1"
    private String major;          // defaut : null
    private boolean scholarship;   // defaut : false
    private List<String> courses;  // defaut : liste vide

    // Constructeur prive : seul le Builder peut creer un compte
    private StudentAccount(Builder b) {
        this.id = b.id;
        this.fullName = b.fullName;
        this.email = b.email;
        this.phone = b.phone;
        this.level = b.level;
        this.major = b.major;
        this.scholarship = b.scholarship;
        this.courses = new ArrayList<>(b.courses);
    }

    // ---------- Getters ----------
    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getLevel() { return level; }
    public String getMajor() { return major; }
    public boolean hasScholarship() { return scholarship; }

    // Copie en lecture seule : l'exterieur ne peut pas modifier la liste interne
    public List<String> getCourses() { return Collections.unmodifiableList(courses); }

    // Inscrire l'etudiant a un nouveau module
    public void enroll(String course) {
        if (course == null || course.trim().isEmpty())
            throw new IllegalArgumentException("Nom de module vide");
        courses.add(course.trim());
    }

    // ---------- Prototype ----------
    // Prototype : clone() = copie PROFONDE (la liste des modules est dupliquee,
    // sinon la copie et le modele partageraient la meme liste).
    @Override
    public StudentAccount clone() {
        try {
            StudentAccount copy = (StudentAccount) super.clone();
            copy.courses = new ArrayList<>(this.courses);
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);   // impossible : la classe est Cloneable
        }
    }

    // Prototype : change uniquement l'identite de la copie (id, nom, email),
    // avec les memes regles de validation que le Builder.
    // A appeler sur une copie : modele.clone().withIdentity(...)
    public StudentAccount withIdentity(String id, String fullName, String email) {
        Builder.validate(id, fullName, email);
        this.id = id.trim();
        this.fullName = fullName.trim();
        this.email = email.trim();
        return this;
    }

    @Override
    public String toString() {
        return "StudentAccount{id=" + id
                + ", name=" + fullName
                + ", email=" + email
                + ", phone=" + phone
                + ", level=" + level
                + ", major=" + major
                + ", scholarship=" + scholarship
                + ", courses=" + courses + "}";
    }

    // =====================================================
    // Builder : classe interne statique qui construit le compte.
    // Champs obligatoires dans le constructeur du Builder,
    // champs optionnels par methodes chainees (valeurs par defaut).
    // =====================================================
    public static class Builder {
        // obligatoires
        private final String id;
        private final String fullName;
        private final String email;
        // optionnels avec valeurs par defaut
        private String phone = null;
        private String level = "L1";
        private String major = null;
        private boolean scholarship = false;
        private final List<String> courses = new ArrayList<>();

        public Builder(String id, String fullName, String email) {
            validate(id, fullName, email);
            this.id = id.trim();
            this.fullName = fullName.trim();
            this.email = email.trim();
        }

        // Regles : id non vide, nom non vide, email contenant @
        static void validate(String id, String fullName, String email) {
            if (id == null || id.trim().isEmpty())
                throw new IllegalArgumentException("L'id ne doit pas etre vide");
            if (fullName == null || fullName.trim().isEmpty())
                throw new IllegalArgumentException("Le nom complet ne doit pas etre vide");
            if (email == null || !email.contains("@"))
                throw new IllegalArgumentException("Email invalide (doit contenir @) : " + email);
        }

        public Builder phone(String phone) { this.phone = phone; return this; }

        public Builder level(String level) {
            if (!LEVELS.contains(level))
                throw new IllegalArgumentException("Niveau invalide (L1, L2, L3, L4, M1, M2) : " + level);
            this.level = level;
            return this;
        }

        public Builder major(String major) { this.major = major; return this; }

        public Builder scholarship(boolean scholarship) { this.scholarship = scholarship; return this; }

        public Builder addCourse(String course) { this.courses.add(course); return this; }

        public StudentAccount build() { return new StudentAccount(this); }
    }
}
