package tripservicekata.trip;

import java.util.List;

import tripservicekata.user.User;

public interface ITripDAO {

	List<Trip> tripsByUser(User user);

}
