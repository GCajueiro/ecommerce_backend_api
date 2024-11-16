package com.ecommerce.liven.ecommerce_backend.service

import com.ecommerce.liven.ecommerce_backend.api.model.LoginBody
import com.ecommerce.liven.ecommerce_backend.api.model.RegistrationBody
import com.ecommerce.liven.ecommerce_backend.exeption.ExistingUser
import com.ecommerce.liven.ecommerce_backend.model.UsuarioLocal
import com.ecommerce.liven.ecommerce_backend.model.dataAccesObject.UsuarioLocalDAO
import spock.lang.Specification
import spock.lang.Subject

class UserServiceSpec extends Specification {

    UsuarioLocalDAO usuarioLocalDAO = Mock()
    EncryptionService encryptionService = Mock()
    JWTService jwtService = Mock()

    @Subject
    UserService userService = new UserService(usuarioLocalDAO, encryptionService, jwtService)

    def "testa regUsuarioLocal deve registrar usuário quando o e-mail não estiver registrado"() {
        dado: "um RegistrationBody e um e-mail de usuário ainda não registrado"
        RegistrationBody registrationBody = new RegistrationBody(nome: "João", email: "joao@example.com", senha: "senha")
        usuarioLocalDAO.findByEmailIgnoreCase(registrationBody.email) >> Optional.empty()

        quando: "o método regUsuarioLocal é chamado"
        UsuarioLocal user = userService.regUsuarioLocal(registrationBody)

        então: "o usuário deve ser salvo e retornado"
        1 * usuarioLocalDAO.save(_) >> { args -> args[0] }
        1 * encryptionService.encryptPassword(registrationBody.senha) >> "senhaCriptografada"
        user.nome == registrationBody.nome
        user.email == registrationBody.email
        user.senha == "senhaCriptografada"
    }

    def "testa regUsuarioLocal deve lançar exceção ExistingUser quando o e-mail já estiver registrado"() {
        dado: "um RegistrationBody e um e-mail já registrado"
        RegistrationBody registrationBody = new RegistrationBody(nome: "João", email: "joao@example.com", senha: "senha")
        usuarioLocalDAO.findByEmailIgnoreCase(registrationBody.email) >> Optional.of(new UsuarioLocal())

        quando: "o método regUsuarioLocal é chamado"
        userService.regUsuarioLocal(registrationBody)

        então: "uma exceção ExistingUser deve ser lançada"
        thrown(ExistingUser)
    }

    def "teste de login deve retornar JWT quando as credenciais estiverem corretas"() {
        dado: "um LoginBody válido e um usuário existente com credenciais correspondentes"
        LoginBody loginBody = new LoginBody(email: "joao@example.com", senha: "senha")
        UsuarioLocal user = new UsuarioLocal(nome: "João", email: "joao@example.com", senha: "senhaCriptografada")
        usuarioLocalDAO.findByEmailIgnoreCase(loginBody.email) >> Optional.of(user)
        encryptionService.checkPassword(loginBody.senha, user.senha) >> true
        jwtService.generateJWT(user) >> "jwtVálido"

        quando: "o método login é chamado"
        String token = userService.login(loginBody)

        então: "um JWT válido deve ser retornado"
        token == "jwtVálido"
    }

    def "teste de login deve retornar null quando as credenciais estiverem incorretas"() {
        dado: "um LoginBody válido mas com credenciais incorretas"
        LoginBody loginBody = new LoginBody(email: "joao@example.com", senha: "senhaErrada")
        UsuarioLocal user = new UsuarioLocal(nome: "João", email: "joao@example.com", senha: "senhaCriptografada")
        usuarioLocalDAO.findByEmailIgnoreCase(loginBody.email) >> Optional.of(user)
        encryptionService.checkPassword(loginBody.senha, user.senha) >> false

        quando: "o método login é chamado"
        String token = userService.login(loginBody)

        então: "null, deve ser retornado, pois as credenciais estão incorretas"
        token == null
    }

    def "teste de login, deve retornar null quando o usuário não existir"() {
        dado: "um LoginBody válido, mas sem encontrar um usuário"
        LoginBody loginBody = new LoginBody(email: "joao@example.com", senha: "senha")
        usuarioLocalDAO.findByEmailIgnoreCase(loginBody.email) >> Optional.empty()

        quando: "o método login é chamado"
        String token = userService.login(loginBody)

        então: "null deve ser retornado pois o usuário não existe"
        token == null
    }
}