package tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import tripservicekata.exception.UserNotLoggedInException;
import tripservicekata.user.User;

public class TripService {

	public List<Trip> getTripsByUser(User user, User loggedInUser) throws UserNotLoggedInException {
		
		validate(loggedInUser);
		
		return user.isFriendWith(loggedInUser)
				? tripsByUser(user)
				: NoTrips();
	}

	private void validate(User loggedInUser) {
		if (loggedInUser == null) {
			throw new UserNotLoggedInException();
		}
	}

	private ArrayList<Trip> NoTrips() {
		return new ArrayList<Trip>();
	}

	protected List<Trip> tripsByUser(User user) {
		return TripDAO.findTripsByUser(user);
	}

}
