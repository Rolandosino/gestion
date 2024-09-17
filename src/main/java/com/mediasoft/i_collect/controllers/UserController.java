package com.mediasoft.i_collect.controllers;

import com.mediasoft.i_collect.communs.utils.BaseController;
import com.mediasoft.i_collect.communs.utils.ResponseObject;
import com.mediasoft.i_collect.dto.UserInfosDto;
import com.mediasoft.i_collect.models.UserEntity;
import com.mediasoft.i_collect.objects.AuthResponse;
import com.mediasoft.i_collect.security.utils.AuthentificationService;
import com.mediasoft.i_collect.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mediasoft.i_collect.controllers.UserController.API_ROOT;

@Slf4j
@AllArgsConstructor
@RequestMapping(API_ROOT)
@RestController
@Tag(name = "User controller")
public class UserController extends BaseController {

    /**
     * Users API root.
     */
    public static final String API_ROOT = "/users";
    private final UserService userService;
    private final AuthentificationService authentificationService;

    /**
     * Rest API for the creation of the user.
     *
     * @param userInfosDto the user
     * @return the response entity
     */
    @PostMapping(produces = "application/hal+json")
    @Operation(description = "Create a new user.")
    @ApiResponse(responseCode = "201", description = "User created, location in the header.")
    public ResponseEntity<AuthResponse> saveNewUser(@RequestBody final UserInfosDto userInfosDto) throws MessagingException {
        try {
            UserEntity utilisateur = this.userService.getUserByEmail(userInfosDto.getEmailAdresse());
            if (userInfosDto.getEmailAdresse() != null && !userInfosDto.getEmailAdresse().contains("@")) {
                return new ResponseEntity<>(new AuthResponse("Votre mail invalide"), HttpStatus.BAD_REQUEST);
            }
            if (userInfosDto.getEmailAdresse() != null && !userInfosDto.getEmailAdresse().contains(".")) {
                return new ResponseEntity<>(new AuthResponse("Votre mail invalide"), HttpStatus.BAD_REQUEST);
            }
            if (utilisateur != null) {
                return new ResponseEntity<>(new AuthResponse("Votre mail est déjà utilisé"), HttpStatus.BAD_REQUEST);
            }
            final AuthResponse user = authentificationService.registrerUser(userInfosDto);
            final URI locationURI = UriComponentsBuilder.fromPath(API_ROOT + "/{id}").build(user.getUser().getId());
            return ResponseEntity.created(locationURI).body(user);
        } catch (Exception e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(new AuthResponse("La création de l'utilisateur a échoué"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Rest API for the creation of the user.
     *
     * @param userInfosDto the user
     * @param id           the user
     * @return the response entity
     */
    @PutMapping(produces = "application/hal+json", path = "/{id}")
    @Operation(description = "Update  user.")
    @ApiResponse(responseCode = "201", description = "User updated, location in the header.")
    public ResponseEntity<UserEntity> updateUser(@RequestBody final UserInfosDto userInfosDto,
                                                 @PathVariable(name = "id") Long id) throws MessagingException {
        try {
            final UserEntity user = userService.updateUser(userInfosDto, id);
            final URI locationURI = UriComponentsBuilder.fromPath(API_ROOT + "/{id}").build(user.getId());
            return ResponseEntity.created(locationURI).body(user);
        } catch (Exception e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Rest API to activate the user.
     */
    @PostMapping(path = "activate")
    @Operation(description = "Activate a new user.")
    @ApiResponse(responseCode = "200", description = "User activate, location in the header.")
    public void activation(@RequestParam String code) {
        this.userService.activation(code);
    }

    /**
     * Rest API to find a user by its id.
     *
     * @param id the identifier
     * @return the response entity
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable(name = "id") Long id) {
        try {
            UserEntity user = userService.getUser(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Rest API to delete a user by its id.
     *
     * @param id the identifier
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable("id") Long id) {
        ResponseObject responseObject = new ResponseObject();
        try {
            userService.deleteUser(id);
            responseObject.setCodeStatus(1);
            responseObject.setStatus(STATUS_SUCCES);
            responseObject.setMessage(MSG_SUCCES);
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        } catch (Exception e) {
            responseObject.setCodeStatus(-1);
            responseObject.setStatus(STATUS_ERREUR);
            responseObject.setMessage(ERREUR_SERVER);
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
