// package project.adp.voting_system_server;

// import org.springframework.web.bind.annotation.RestController;

// import jakarta.servlet.http.HttpServletRequest;
// import project.adp.voting_system_server.service.TestService;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;


// @RestController
// public class Temp {
    
//     @Autowired
//     private TestService testService;

//     @PostMapping("/insertTestData")
//     public ResponseEntity<String> postMethodName() {
//         try {
//             testService.insertData();
//             return ResponseEntity.ok("Data inserted successfully.");
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                     .body("Error inserting data: " + e.getMessage());
//         }
//     }
    
// }
