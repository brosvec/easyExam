package cz.easyExam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;

@Entity
// We can't name the table User, as it is a reserved table name in some dbs, including Postgres
@Table(name = "PDA_USER")
@NamedQueries({
        @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username")
})
public class User extends AbstractEntity implements Cloneable {

    @Basic(optional = false)
    @Column(nullable = false,unique = true)
    private String username;

    @Basic(optional = false)
    @Column(nullable = false,unique = true)
    private String email;

    @JsonIgnore
    @Basic(optional = false)
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "owner_id")
    private List<Test> ownedTests;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "owner_id")
    private List<SubscribedTest> subscribedTest;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<QuestionUser> userQuestions;

    public List<Test> getOwnedTests() {
        return ownedTests;
    }

    public void setOwnedTests(List<Test> ownedTests) {
        this.ownedTests = ownedTests;
    }

    public List<SubscribedTest> getSubscribedTest() {
        return subscribedTest;
    }

    public void setSubscribedTest(List<SubscribedTest> subscribedTest) {
        this.subscribedTest = subscribedTest;
    }

    public User() {
        this.role = Role.GUEST;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void encodePassword(PasswordEncoder encoder) {
        this.password = encoder.encode(password);
    }

    public void erasePassword() {
        this.password = null;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                username +
                "(" + email + ")}";
    }
    @Override
    public User clone() throws CloneNotSupportedException {
        return (User) super.clone();
    }
}
