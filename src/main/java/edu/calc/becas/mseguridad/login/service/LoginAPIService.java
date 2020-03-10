package edu.calc.becas.mseguridad.login.service;

import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;

public interface LoginAPIService {

  Usuario login(String username, String password) throws GenericException;
}
