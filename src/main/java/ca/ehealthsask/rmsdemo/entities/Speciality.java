package ca.ehealthsask.rmsdemo.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Speciality {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "speciality")
    private List<Referral> referrals;

    @JsonIgnore
    @OneToMany(mappedBy = "speciality")
    private List<Provider> providers;

}
