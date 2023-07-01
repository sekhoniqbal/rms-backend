package ca.ehealthsask.rmsdemo.entities;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Provider {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;

    @ManyToOne()
    @JoinColumn(name = "speciality_id", nullable = false)
    private Speciality speciality;

    private Boolean isAcceptingPatients;

    @JsonIgnore
    @OneToMany(mappedBy = "provider")
    private List<Referral> referrals;

}
