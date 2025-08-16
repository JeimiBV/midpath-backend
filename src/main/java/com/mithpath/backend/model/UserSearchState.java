package com.mithpath.backend.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "user_search_state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSearchState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "entity")
    private String entity;

    @Column(columnDefinition = "TEXT")
    private String filtersJson;
}
