package com.project.owlback.studygroup.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.owlback.favorite.dto.Favorite;
import com.project.owlback.favorite.dto.temp.CodeReview;
import com.project.owlback.favorite.dto.temp.Url;
import com.project.owlback.goal.dto.Goal;
import com.project.owlback.goal.dto.Subject;
import com.project.owlback.score.dto.Score;
import com.project.owlback.user.dto.Role;
import com.project.owlback.user.dto.UserImg;
import com.project.owlback.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity
@DynamicInsert
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "study_group")
public class StudyGroup extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_group_id", nullable = false)
    private Long studyGroupId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "goal")
    private String goal;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "information")
    private String information;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name="join_process_id")
    private StudyJoinProcess joinProcess;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name="criteria_id")
    private StudyCriteria criteria;

    @Column(name = "criteria_detail")
    private String criteriaDetail;

    @Column(name = "max_member")
    private int maxMember;

    @Column(name = "min_member")
    private int minMember;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name="status_id")
    private StudyStatus status;

    @Column(name="join_code")
    private String joinCode;

    @OneToMany
    @JoinColumn(name = "study_group_id")
    private List<StudyTag> tags = new ArrayList<>();

    public void expire(StudyStatus status) {
        this.status = status;
    }
}
