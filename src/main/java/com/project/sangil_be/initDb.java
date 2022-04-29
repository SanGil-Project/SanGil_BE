package com.project.sangil_be;

import javax.annotation.PostConstruct;

import com.project.sangil_be.model.User;
import com.project.sangil_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class initDb {

    private final InitService initService;


    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final UserRepository userRepository;

        public void dbInit1() {
            User user = new User("username", "nickname", "password");
            userRepository.save(user);
        }
    }
}