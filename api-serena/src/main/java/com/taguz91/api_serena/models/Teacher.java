package com.taguz91.api_serena.models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "teachers")
@Accessors(chain = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Teacher extends BaseEntity implements Serializable, UserDetails {

    @Serial
    private static final long serialVersionUID = -4115808525376597079L;

    @Id
    private String id;

    @Column(nullable = true)
    private String reference;

    private String name;

    private String email;

    @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT  'false' ")
    private Boolean isActive;

    @JsonIgnore
    private String password;

    @Column(nullable = true)
    private LocalDateTime lastLogin;

    @Column(nullable = true, length = 255)
    private String token;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT  'false'")
    private Boolean isAdmin;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher")
    @JsonIgnore
    private List<Classroom> classrooms;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }
}
