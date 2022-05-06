package com.project.sangil_be.service;

import com.project.sangil_be.S3.S3Service;
import com.project.sangil_be.api.DistanceToUser;
import com.project.sangil_be.dto.BookMarkResponseDto;
import com.project.sangil_be.dto.ResponseDto;
import com.project.sangil_be.dto.SignUpRequestDto;
import com.project.sangil_be.dto.UsernameRequestDto;
import com.project.sangil_be.model.BookMark;
import com.project.sangil_be.model.Mountain100;
import com.project.sangil_be.model.MountainComment;
import com.project.sangil_be.model.User;
import com.project.sangil_be.repository.BookMarkRepository;
import com.project.sangil_be.repository.Mountain100Repository;
import com.project.sangil_be.repository.MountainCommentRepository;
import com.project.sangil_be.repository.UserRepository;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final BookMarkRepository bookMarkRepository;
    private final Mountain100Repository mountain100Repository;
    private final MountainCommentRepository mountainCommentRepository;
    private final S3Service s3Service;


    public ResponseDto registerUser(SignUpRequestDto requestDto) {
        Boolean result = true;
        String username = requestDto.getUsername();

        Optional<User> foundname = userRepository.findByUsername(username);

        if (foundname.isPresent()) {
            result = false;
            return new ResponseDto(result);
        }

        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickname = "없음";
        String userImageUrl = "없음";
        String userTitle = "없음";

        User user = new User(username, password, nickname, userImageUrl, userTitle);
        userRepository.save(user);

        ResponseDto responseDto = new ResponseDto(result);

        return responseDto;

    }

    @Transactional
    public void editname(UsernameRequestDto usernameRequestDto, User user) {

        user.editusername(usernameRequestDto);
        userRepository.save(user);

    }

//    @Transactional
//    public void firstimage(MultipartFile multipartFile, User user) {
//
//        String profileImageUrl = s3Service.upload(multipartFile, "profileimage");
//
//        user.editimage(profileImageUrl);
//        userRepository.save(user);
//    }

    @Transactional
    public void editimage(MultipartFile multipartFile, User user) {

        String[] key = user.getUserImgUrl().split(".com/");
        String imageKey = key[key.length - 1];
        System.out.println(imageKey);
        String profileImageUrl = s3Service.reupload(multipartFile, "profileimage", imageKey);

        user.editimage(profileImageUrl);
        userRepository.save(user);
    }


    public List<BookMarkResponseDto> getBookMarkMountain(UserDetailsImpl userDetails) {
        List<BookMark> bookMarkList = bookMarkRepository.findAllByUserId(userDetails.getUser().getUserId());
        List<BookMarkResponseDto> bookMarkResponseDtos = new ArrayList<>();

        Double lat = 37.553877;
        Double lng = 126.971188;

        for (BookMark bookMark : bookMarkList) {
            boolean bookMarkChk = bookMarkRepository.existsByMountain100IdAndUserId(bookMark.getMountain100Id(),
                    userDetails.getUser().getUserId());
            Mountain100 mountain100 = mountain100Repository.findById(bookMark.getMountain100Id()).orElseThrow(
                    () -> new IllegalArgumentException("해당하는 산이 없습니다.")
            );

            Double distance = DistanceToUser.distance(lat, lng, mountain100.getLat(), mountain100.getLng(), "kilometer");

            int star = 0;
            float starAvr = 0f;

            for (int i = 0; i < 10; i++) {
                List<MountainComment> mountainComments = mountainCommentRepository.findAllByMountain100Id(bookMark.getMountain100Id());
                if (mountainComments.size() == 0) {
                    starAvr = 0;
                } else {
                    star += mountainComments.get(i).getStar();
                    starAvr = (float) star / mountainComments.size();
                }
            }
            bookMarkResponseDtos.add(new BookMarkResponseDto(mountain100, bookMarkChk, starAvr, distance));
        }
        return bookMarkResponseDtos;
    }

}