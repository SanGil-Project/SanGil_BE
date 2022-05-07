//package com.project.sangil_be.model;
//

//import com.project.sangil_be.dto.TrackingRequestDto;

//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Getter
//@Setter
//@Entity
//@NoArgsConstructor
//public class Tracking {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long trackingId;
//
//    @Column(nullable = false)
//    private Double lat;
//
//    @Column(nullable = false)
//    private Double lng;
//
//    @Column(nullable = false)
//    private String timer;
//
//    @Column(nullable = false)
//    private Double distance;
//
//    @Column(nullable = false)
//    private Long completedId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId")
//    private User user;
//
//    public Tracking(TrackingRequestDto requestDto, User user) {
//        this.lat = requestDto.getLat();
//        this.lng = requestDto.getLng();
//        this.timer = requestDto.getTimer();
//        this.user = user;
//    }
//}
