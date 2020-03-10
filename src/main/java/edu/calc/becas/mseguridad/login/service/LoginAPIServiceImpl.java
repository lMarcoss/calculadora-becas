package edu.calc.becas.mseguridad.login.service;

import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mseguridad.login.dao.LoginAPIDaoImpl;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginAPIServiceImpl implements LoginAPIService {

  private final LoginAPIDaoImpl loginAPIDaoImpl;

  public LoginAPIServiceImpl(LoginAPIDaoImpl loginAPIDaoImpl){
    this.loginAPIDaoImpl = loginAPIDaoImpl;
  }

  @Override
  public Usuario login(String username, String password) throws GenericException {
    return loginAPIDaoImpl.login(username, password);
  }
}
