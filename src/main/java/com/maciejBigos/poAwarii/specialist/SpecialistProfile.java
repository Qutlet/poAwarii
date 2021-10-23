package com.maciejBigos.poAwarii.specialist;

import com.maciejBigos.poAwarii.user.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class SpecialistProfile {

    @SequenceGenerator(
            name = "specialistProfile_sequence",
            sequenceName = "specialistProfile_sequence",
            allocationSize = 1
    )
    @Id
    @Column(name = "specialistProfileID")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "specialistProfile_sequence"
    )
    private Long id;
    /**
     * Profile fields
     */
    private Long userID;
    private String customProfileName;
    @ElementCollection
    private List<String> categories = new ArrayList<>();
    /**
     * User data field, consider delete this section
     */
    private String firstName; // Same as userFirstName
    private String lastName; // Same as userLastName
    private String email; // Same as userEmail
    private String phoneNumber; // Same as userPhoneNumber

    //todo photo gallery
}
