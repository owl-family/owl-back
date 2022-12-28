//package com.project.owlback;
//
//import com.project.owlback.user.model.User;
//import com.project.owlback.user.repository.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.Optional;
//
//@DataJpaTest
//public class tempTest {
//    @Autowired
//    UserRepository userRepository;
//
//    @Test
//    void findAllTest() {
//        Long id = 1L;
//
//        Optional<User> result = userRepository.findById(id);
//
//        System.out.println("=============================");
//
//        if(result.isPresent()) {
//            User user = result.get();
//            System.out.println(user);
//        }
//    }
//}
