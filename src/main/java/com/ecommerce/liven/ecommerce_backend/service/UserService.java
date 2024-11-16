package com.ecommerce.liven.ecommerce_backend.service;

import com.ecommerce.liven.ecommerce_backend.api.model.LoginBody;
import com.ecommerce.liven.ecommerce_backend.api.model.RegistrationBody;
import com.ecommerce.liven.ecommerce_backend.exeption.ExistingUser;
import com.ecommerce.liven.ecommerce_backend.model.UsuarioLocal;
import com.ecommerce.liven.ecommerce_backend.model.dataAccesObject.UsuarioLocalDAO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UsuarioLocalDAO usuarioLocalDAO;
    private EncryptionService encryptionService;
    private JWTService jwtService;
    public UserService(UsuarioLocalDAO usuarioLocalDAO, EncryptionService encryptionService, JWTService jwtService) {
        this.usuarioLocalDAO = usuarioLocalDAO;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }


    public UsuarioLocal regUsuarioLocal(RegistrationBody registrationBody) throws ExistingUser {

        if (usuarioLocalDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()) {
            throw new ExistingUser();
        }

        UsuarioLocal user = new UsuarioLocal();

        user.setName(registrationBody.getName());
        user.setEmail(registrationBody.getEmail());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        return usuarioLocalDAO.save(user);
    }

    public String login(LoginBody loginBody) {
        Optional<UsuarioLocal> opUser = usuarioLocalDAO.findByEmailIgnoreCase(loginBody.getEmail());
        if (opUser.isPresent()) {
            UsuarioLocal user = opUser.get();
            if (encryptionService.checkPassword(loginBody.getPassword(), user.getPassword())) {
                return jwtService.generateJWT(user);
            }
        }
        return null;
    }
}
