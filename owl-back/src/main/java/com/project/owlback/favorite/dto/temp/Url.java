//package com.project.owlback.favorite.dto.temp;
//
//import com.project.owlback.favorite.dto.Favorite;
//import com.project.owlback.user.model.User;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.Date;
//
//@Entity
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@ToString
//public class Url {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long urlId;
//
//    @Column(name = "TITLE")
//    private String title;
//
//    @Column(name = "CONTENT")
//    private String content;
//
//    @Column(name = "LINK")
//    private String link;
//
//    @Column(name = "VIEW")
//    private long view;
//
//    @Column(name = "CREATED_DATE")
//    private Date createdDate;
//
//    @Column(name = "MODIFIED_DATE")
//    private Date modifiedDate;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @OneToOne(mappedBy = "url")
//    private Favorite favorite;
//}
