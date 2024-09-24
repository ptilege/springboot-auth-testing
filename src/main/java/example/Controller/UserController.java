package example.Controller;

import example.Model.Login;
import example.Model.Register;
import example.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Register user) {


        if (user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Email and password are required."));
        }


        userService.registerUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, HttpSession session) {
        if (error != null) {
            session.setAttribute("loginError", "Invalid username or password.");
        }
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String username, @RequestParam String password, HttpSession session) {
        Register user = userService.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("username", username);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", ""));
        }
    }


    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session) {
        if (session.getAttribute("username") != null) {
            return "dashboard";
        } else {
            return "redirect:/login";
        }
    }
}
