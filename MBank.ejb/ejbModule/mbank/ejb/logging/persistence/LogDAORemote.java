package mbank.ejb.logging.persistence;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface LogDAORemote
{
  Log create(Log log);
  List<Log> getAllLog();
  List<Log> getLogsByClientId(Long clientId);
}
