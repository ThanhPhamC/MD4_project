package ra.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ra.jwt.JwtTokenProvider;
import ra.model.entity.*;
import ra.model.service.CartService;
import ra.model.service.RoleService;
import ra.model.service.UserService;
import ra.dto.request.UserLogin;
import ra.dto.request.UserRegister;
import ra.dto.request.UserUpdate;
import ra.dto.response.JwtResponse;
import ra.dto.response.MessageResponse;
import ra.security.CustomUserDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider tokenProvider;
    private UserService userService;
    private RoleService roleService;
    private PasswordEncoder encoder;
    private CartService cartService;

    @GetMapping("/searchByName")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Map<String,Object>> searchByName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam String userName){
        Pageable pageable = PageRequest.of(page,size);
        Page<Catalog> pageUser = userService.findByName(userName,pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("users",pageUser.getContent());
        data.put("total",pageUser.getSize());
        data.put("totalItems",pageUser.getTotalElements());
        data.put("totalPages",pageUser.getTotalPages());
        return  new ResponseEntity<>(data, HttpStatus.OK);
    }
    @GetMapping("/getPaggingAndSortByName")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Map<String,Object>> getPaggingAndSortByName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam String direction){
        Sort.Order order;
        if (direction.equals("asc")){
            order=new Sort.Order(Sort.Direction.ASC,"UserName");
        }else{
            order=new Sort.Order(Sort.Direction.DESC,"UserName");
        }
        Pageable pageable = PageRequest.of(page,size,Sort.by(order));
        Page<Catalog> pageUser = userService.getPagging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("users",pageUser.getContent());
        data.put("total",pageUser.getSize());
        data.put("totalItems",pageUser.getTotalElements());
        data.put("totalPages",pageUser.getTotalPages());
        return  new ResponseEntity<>(data, HttpStatus.OK);
    }
    @GetMapping("/getById")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getById(@RequestParam int userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserRegister userRegister) {
        if (userService.existsByUserName(userRegister.getUserName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: UserName is already"));
        }
        if (userService.existsByPhone(userRegister.getPhone())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Phone is already"));
        }
        if (userService.existsByEmail(userRegister.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already"));
        }
        Users user = new Users();
        user.setFullName(userRegister.getFullName());
        user.setUserName(userRegister.getUserName());
        user.setPassword(encoder.encode(userRegister.getPassword()));
        user.setEmail(userRegister.getEmail());
        user.setPhone(userRegister.getPhone());
        user.setUserStatus(true);
        user.setAvatar(userRegister.getAvatar());
        user.setUserAddress(userRegister.getUserAddress());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            user.setCreated(sdf.parse(sdf.format(new Date())));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Set<String> strRoles = userRegister.getListRoles();
        Set<Roles> listRoles = new HashSet<>();
        if (strRoles == null) {
            //User quyen mac dinh
            Roles userRole = roleService.findByRoleName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            listRoles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(adminRole);
                    case "moderator":
                        Roles modRole = roleService.findByRoleName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(modRole);
                    case "user":
                        Roles userRole = roleService.findByRoleName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(userRole);
                }
            });
        }
        user.setListRoles(listRoles);
        userService.saveOrUpdate(user);
        Cart cart= new Cart();
        cart.setUsers((Users) userService.saveOrUpdate(user));
        cart.setCartStatus(0);
        cartService.saveOrUpdate(cart);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody UserLogin userLogin) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.getUserName(), userLogin.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails customUserDetail = (CustomUserDetails) authentication.getPrincipal();
        //Sinh JWT tra ve client
        String jwt = tokenProvider.generateToken(customUserDetail);
        //Lay cac quyen cua user
        List<String> listRoles = customUserDetail.getAuthorities().stream()
                .map(item -> item.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(customUserDetail.getFullName(), jwt, customUserDetail.getUsername(), customUserDetail.getEmail(),
                customUserDetail.getPhone(), customUserDetail.getAvatar(), customUserDetail.getUserAddress(), listRoles,customUserDetail.getCarts().get(customUserDetail.getCarts().size()-1)));
    }
    @PutMapping("/updateUser")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdate userUpdate) {
        Users user = new Users();
        CustomUserDetails customUserDetail = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (customUserDetail.getUserId() == userUpdate.getUserId()) {
            user.setUserId(userUpdate.getUserId());
            user.setUserName(userUpdate.getUserName());
            user.setFullName(userUpdate.getFullName());
            user.setPassword(encoder.encode(userUpdate.getPassword()));
            user.setEmail(userUpdate.getEmail());
            user.setPhone(userUpdate.getPhone());
            user.setUserStatus(userUpdate.isUserStatus());
            user.setAvatar(userUpdate.getAvatar());
            user.setUserAddress(userUpdate.getUserAddress());
            user.setCartList(customUserDetail.getCarts());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                user.setCreated(sdf.parse(userUpdate.getCreated()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Users users1= (Users) userService.findById(customUserDetail.getUserId());
            user.setListRoles(users1.getListRoles());
        } else if (customUserDetail.getAuthorities().size() > userUpdate.getListRoles().size()) {
            Set<String> strRoles = userUpdate.getListRoles();
            Set<Roles> listRoles = new HashSet<>();
            if (strRoles == null) {
                Roles userRole = roleService.findByRoleName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                listRoles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "admin":
                            Roles adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                            listRoles.add(adminRole);
                        case "moderator":
                            Roles modRole = roleService.findByRoleName(ERole.ROLE_MODERATOR)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                            listRoles.add(modRole);
                        case "user":
                            Roles userRole = roleService.findByRoleName(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                            listRoles.add(userRole);
                    }
                });
            }
            user.setListRoles(listRoles);
            user = (Users) userService.findByUserName(userUpdate.getUserName());
            user.setUserStatus(userUpdate.isUserStatus());
        } else {
            return new ResponseEntity<>(new MessageResponse("Can not update User"), HttpStatus.FORBIDDEN);
        }
        userService.saveOrUpdate(user);
        return ResponseEntity.ok(new MessageResponse("User update successfully"));
    }
    @DeleteMapping("/delete")
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(Integer userId){
        CustomUserDetails customUserDetail = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users users= (Users) userService.findById(userId);
        if (customUserDetail.getAuthorities().size()>users.getListRoles().size()){
            users.setUserStatus(false);
            userService.saveOrUpdate(users);
        }
        return ResponseEntity.ok("Delete successfully");
    }
    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestParam("oldPass")String oldPass,@RequestParam("newPass")String newPass){
        CustomUserDetails customUserDetail = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users users= (Users) userService.findById(customUserDetail.getUserId());
        boolean check= encoder.matches(oldPass, users.getPassword());
        if (check){
            users.setPassword(encoder.encode(newPass));
            userService.saveOrUpdate(users);
            return new ResponseEntity<>("Update Password successfully",HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Password wrong",HttpStatus.EXPECTATION_FAILED);
        }
    }
    @GetMapping("/findByFullName")
    public ResponseEntity<Map<String,Object>> findByFullName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam String direction,
            @RequestParam String fullName){
        Sort.Order order;
        if (direction.equals("asc")){
            order=new Sort.Order(Sort.Direction.ASC,"UserName");
        }else{
            order=new Sort.Order(Sort.Direction.DESC,"UserName");
        }
        Pageable pageable = PageRequest.of(page,size,Sort.by(order));
        Page<Catalog> pageUser = userService.findByName(fullName,pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("users",pageUser.getContent());
        data.put("total",pageUser.getSize());
        data.put("totalItems",pageUser.getTotalElements());
        data.put("totalPages",pageUser.getTotalPages());
        return  new ResponseEntity<>(data, HttpStatus.OK);
    }
}