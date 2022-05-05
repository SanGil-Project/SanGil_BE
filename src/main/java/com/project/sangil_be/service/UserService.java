package com.project.sangil_be.service;

import com.project.sangil_be.S3.S3Service;
import com.project.sangil_be.dto.ResponseDto;
import com.project.sangil_be.dto.SignUpRequestDto;
import com.project.sangil_be.dto.UsernameRequestDto;
import com.project.sangil_be.model.User;
import com.project.sangil_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
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

        User user = new User(username, password, nickname,  userImageUrl, userTitle);
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

        String [] key = user.getUserImgUrl().split(".com/");
        String imageKey = key[key.length-1];
        System.out.println(imageKey);
        String profileImageUrl = s3Service.reupload(multipartFile, "profileimage",imageKey);

        user.editimage(profileImageUrl);
        userRepository.save(user);
    }


}
