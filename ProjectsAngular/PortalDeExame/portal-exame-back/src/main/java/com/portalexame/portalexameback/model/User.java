package com.portalexame.portalexameback.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private boolean enabled = true;
    private String profile;

    //usuario tem muitos perfis       aboutCASCADE: https://cursos.alura.com.br/forum/topico-quando-usar-cascadetype-e-qual-a-diferenca-entre-all-persist-merge-remove-refresh-detach-110962?gclid=CjwKCAiAyPyQBhB6EiwAFUuakmpgsYLfvE01w94nnL5oIee4NFFzTwE93rAeXCzWCM6ImF0xvDUKhxoCiAIQAvD_BwE
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER) //DIFERENÇA ENTRE LAZY E EAGER https://stackoverflow.com/questions/2990799/difference-between-fetchtype-lazy-and-eager-in-java-persistence-api
    @JsonIgnore   //https://pt.stackoverflow.com/questions/250729/quais-as-maneiras-de-evitar-recursividade-infinita-sem-o-uso-da-anota%C3%A7%C3%A3o-jsonign
    private Set<UserRole> userRole = new HashSet<UserRole>();

    public User(){

    }

    public User(Long id, String username, String password, String firstName, String lastName, String email, String phone, boolean enabled, String profile) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.enabled = enabled;
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Set<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(Set<UserRole> userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<Authority> set = new HashSet<>();

        getUserRole().forEach(userRole1 -> {
            set.add(new Authority(userRole1.getRole().getRoleName()));
        });

        return set;
    }
}
