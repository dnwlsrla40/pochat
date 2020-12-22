package com.woojin.pochat.controller;

import com.woojin.pochat.domain.user.User;
import com.woojin.pochat.dto.UserDto;
import com.woojin.pochat.service.UserService;
import com.woojin.pochat.util.UploadThumbnailUtils;
import com.woojin.pochat.util.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    @Autowired
    private JwtService jwtService;

    // @Autowired
    // private RabbitAdmin rabbitAdmin;

    private final UserService userService;

    private AmqpAdmin amqpAdmin;

    @Value("${resources.location}")
    private String thumbnailPath;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/signup")
    public String signUp(@RequestBody UserDto.UserCreateRequestDto requestDto){
        return userService.signUp(requestDto);
    }


//    @CrossOrigin(origins = "*", allowedHeaders = "*")
//    @PostMapping("/login")
//    public void Login(@RequestBody UserDto.UserCreateRequestDto requestDto, HttpServletResponse res){
//        userService.Login(requestDto, res);
//    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDto.UserCreateRequestDto requestDto, HttpServletResponse res){
//        amqpAdmin.declareQueue(new Queue("test1", false));
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        CachingConnectionFactory cf = new CachingConnectionFactory("127.0.0.1", 5672);
        cf.setUsername("guest");
        cf.setPassword("guest");

        RabbitAdmin admin = new RabbitAdmin(cf);
        Queue queue = new Queue("test1Queue");
        admin.declareQueue(queue);


        try{
            User loginUser = userService.signin(requestDto);
            System.out.println("controller loginUser: " + loginUser);
            String token = jwtService.create(loginUser);

            res.setHeader("jwt-auth-token",token);

            resultMap.put("status", true);
            resultMap.put("data", loginUser);
            status = HttpStatus.ACCEPTED;
        } catch(RuntimeException e) {
            log.error("로그인 실패", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/user/{username}")
    public User getUserInfo(@PathVariable(name = "username") String username){

        return userService.getUserInfo(username);
    }

    // thumbnail 업로드
    @PostMapping("/thumbnail")
    public String uploadThumbnail(@RequestPart("file") MultipartFile file) throws Exception{
        return UploadThumbnailUtils.fileUpload(thumbnailPath, file.getOriginalFilename(), file.getBytes());
    }

    // thumbnail 업로드 test용
    @GetMapping("/download")
    public ResponseEntity<Resource> fileDownload() throws IOException {
        Path path = Paths.get("");
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "test" + "\"")
                .body(resource);
    }
}
