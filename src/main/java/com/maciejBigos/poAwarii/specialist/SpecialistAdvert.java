package com.maciejBigos.poAwarii.specialist;

import com.maciejBigos.poAwarii.user.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class SpecialistAdvert {

    @SequenceGenerator(
            name = "specialistAdvert_sequence",
            sequenceName = "specialistAdvert_sequence",
            allocationSize = 1
    )
    @Id
    @Column(name = "specialistAdvert_sequenceID")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "specialistAdvert_sequence"
    )
    private Long id;
    /**
     * Advert fields
     */
    private String advertName;
    @ElementCollection
    private List<String> categories = new ArrayList<>();
    /**
     * User data field, consider delete this section
     */
    private String specialistFirstName; // Same as userFirstName
    private String lastName; // Same as userLastName
    private String email; // Same as userEmail
    private String phoneNumber; // Same as userPhoneNumber
}
