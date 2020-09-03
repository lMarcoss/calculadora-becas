package edu.calc.becas.mseguridad.usuarios.api;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import edu.calc.becas.mseguridad.usuarios.service.UsuarioService;
import edu.calc.becas.mvc.config.security.user.UserRequestService;
import edu.calc.becas.utils.DecryptUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static edu.calc.becas.common.utils.Constant.*;

/**
 * API para exponee servicios de administracion de usuarios
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 4/14/19
 */
@Slf4j
@RestController
@RequestMapping("/usuarios")
@Api(description = "Servicios para administraci\u00f3n de usuarios")
public class UsuarioAPI {

    @Value("${key.encode.crypto}")
    String secretKeyDecrypt;

    private final UsuarioService usuarioService;
    private final UserRequestService userRequestService;

    /**
     * @param usuarioService     servicio de usuarios
     * @param userRequestService servicio de seguridad de las peticiones
     */
    @Autowired
    public UsuarioAPI(UsuarioService usuarioService, UserRequestService userRequestService) {
        this.usuarioService = usuarioService;
        this.userRequestService = userRequestService;
    }

    /**
     * Regresa el listado de usuarios en forma paginada
     *
     * @param page        pagina a recuperar
     * @param pageSize    numero de registros a regresar por pagina
     * @param status      estatus de los registros a obtener
     * @param tipoUsuario tipo de usuario a obtener
     * @return lista de usuarios
     */

    @GetMapping
    @ApiOperation(value = "Obtiene el listado de usuarios")
    public WrapperData getAll(
            @ApiParam(value = "P\u00e1gina a recuperar", defaultValue = DEFAULT_PAGE) @RequestParam(value = "page", defaultValue = DEFAULT_PAGE, required = false) String page,
            @ApiParam(value = "Registros a recuperar", defaultValue = ALL_ITEMS) @RequestParam(value = "pageSize", defaultValue = ALL_ITEMS, required = false) String pageSize,
            @ApiParam(value = "Estatus de los registros a recuperar", defaultValue = DEFAULT_ESTATUS) @RequestParam(value = "status", defaultValue = DEFAULT_ESTATUS, required = false) String status,
            @ApiParam(value = "Tipo de usuario a recuperar", defaultValue = TIPO_USUARIO_DEFAULT) @RequestParam(value = "tipo-usuario", defaultValue = TIPO_USUARIO_DEFAULT, required = false) String tipoUsuario) {
        if (pageSize.equalsIgnoreCase(ALL_ITEMS)) {
            pageSize = ITEMS_FOR_PAGE;
        }
        return this.usuarioService.getAllByStatusAndOneParam(Integer.parseInt(page), Integer.parseInt(pageSize), status, tipoUsuario);
    }

    /**
     * Registra un usuario
     *
     * @param usuario     usuario a registrar
     * @param httpServlet parametros de la peticion
     * @return usuario registrado
     * @throws GenericException lanza error ocurrido al registrar usuario
     */
    @PostMapping
    @ApiOperation(value = "Registra un usuario")
    public Usuario add(@ApiParam(value = "Usuario a registrar", required = true) @RequestBody Usuario usuario,
                       HttpServletRequest httpServlet) throws GenericException {
        UserLogin usuarioPeticion = userRequestService.getUserLogin(httpServlet);
        usuario.setAgregadoPor(usuarioPeticion.getUsername());
        validatePassword(usuario);
        return this.usuarioService.add(usuario);
    }

    /**
     * Valida y desenctripta la contrasenia de usuario
     *
     * @param usuario usuario a validar contrasenia
     * @throws GenericException lanza error ocurrido al validar password
     */
    private void validatePassword(Usuario usuario) throws GenericException {
        try {
            String password = usuario.getPassword();
            if (password != null && !password.equalsIgnoreCase("")) {
                usuario.setPassword(DecryptUtil.decrypt(usuario.getPassword(), secretKeyDecrypt, "password"));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new GenericException(e.getMessage());
        }
    }

    /**
     * Actualiza datos de un usuario
     *
     * @param usuario     usuario a modificar
     * @param httpServlet parametros de la peticion
     * @return usuario actualizado
     * @throws GenericException lanza error ocurrido al modificar usuario
     */
    @PatchMapping
    @ApiOperation(value = "Actualiza datos de un usuario")
    public Usuario update(@ApiParam(value = "Usuario a actualizar", required = true) @RequestBody Usuario usuario,
                          HttpServletRequest httpServlet) throws GenericException {
        UserLogin usuarioPeticion = userRequestService.getUserLogin(httpServlet);
        usuario.setActualizadoPor(usuarioPeticion.getUsername());
        validatePassword(usuario);
        return this.usuarioService.update(usuario);
    }
}
