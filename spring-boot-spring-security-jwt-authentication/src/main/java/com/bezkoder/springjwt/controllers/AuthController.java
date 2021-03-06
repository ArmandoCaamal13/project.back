package com.bezkoder.springjwt.controllers;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.bezkoder.springjwt.exception.ResourceNotFoundException;
import com.bezkoder.springjwt.models.Producto;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.repository.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.JwtResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private ProductoRepositorio productoRepositorio;
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt, 
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getEmail(), 
                         roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
               signUpRequest.getEmail(),
               encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);

          break;
        case "mod":
          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(modRole);

          break;
        default:
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }


  @GetMapping("/list")
  public List<User> getAllUser(){return userRepository.findAll();}

  @GetMapping("/list/{id}")
  public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId)
  throws ResourceNotFoundException{
  User user = userRepository.findById(userId)
          .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
  return ResponseEntity.ok().body(user);
  }

  @DeleteMapping("/list/{id}")
  public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId)
          throws ResourceNotFoundException {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

    userRepository.delete(user);
    Map<String, Boolean> response = new HashMap<>();
    response.put("Delete", Boolean.TRUE);
    return response;
  }

  @GetMapping("/inventary")
  public List<Producto> getAllProducto(){
    return productoRepositorio.findAll();
  }

  @GetMapping("/inventary/{id}")
  public ResponseEntity<Producto> getProductoById(@PathVariable(value = "id") Long productoId)
    throws ResourceNotFoundException{
    Producto producto = productoRepositorio.findById(productoId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productoId));
      return ResponseEntity.ok().body(producto);
  }

  @PostMapping("/inventary")
  public Producto createProducto(@Valid @RequestBody Producto producto){
    return productoRepositorio.save(producto);
  }

  @PutMapping("/inventary/{id}")
  public ResponseEntity <Producto> updateProducto(@PathVariable(value = "id") Long productoId,
        @Valid @RequestBody Producto productoDetails) throws ResourceNotFoundException {
    Producto producto = productoRepositorio.findById(productoId)
            .orElseThrow(() -> new ResourceNotFoundException("Producto not found for this i :: " + productoId));

    producto.setEquipo(productoDetails.getEquipo());
    producto.setMarca(productoDetails.getMarca());
    producto.setModelo(productoDetails.getModelo());
    producto.setNum_serie(productoDetails.getNum_serie());
    final Producto updateProducto =productoRepositorio.save(producto);
    return ResponseEntity.ok(updateProducto);
  }

  @DeleteMapping("/inventary/{id}")
  public Map<String, Boolean> deleteProducto(@PathVariable(value = "id") Long productoId)
    throws ResourceNotFoundException {
    Producto producto = productoRepositorio.findById(productoId)
            .orElseThrow(() -> new ResourceNotFoundException("Producto not found for this id :: " + productoId));

    productoRepositorio.delete(producto);
    Map<String, Boolean> response = new HashMap<>();
    response.put("Delete", Boolean.TRUE);
    return response;
  }
}
