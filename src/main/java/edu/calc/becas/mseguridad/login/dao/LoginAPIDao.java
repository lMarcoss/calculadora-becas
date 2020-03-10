package edu.calc.becas.mseguridad.login.dao;

import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;

public interface LoginAPIDao {

  Usuario login(String username, String password) throws GenericException;
}
