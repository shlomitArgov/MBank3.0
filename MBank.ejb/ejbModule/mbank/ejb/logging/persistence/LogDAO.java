package mbank.ejb.logging.persistence;

import java.util.List;
import javax.ejb.Local;

@Local
public interface LogDAO
{
  Log create(Log log);
  List<Log> getAllLog();
  List<Log> getLogsByClientId(Long clientId);
}
