package com.example.userservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_avatars")
@Getter
@Setter
@Builder
public class UserAvatar {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String avatarUrl;

    @OneToOne(fetch = FetchType.LAZY,  optional = false)
    @JoinColumn(name = "user_profile_id", nullable = false, unique = true)
    private UserProfile userProfile;


    @Column(nullable = false)
    private Instant uploadedAt;
}
