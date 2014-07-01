package tripservicekata.trip;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import tripservicekata.exception.UserNotLoggedInException;
import tripservicekata.user.User;

public class TripServiceTest {
	
	private static final User GUEST = null;
	private static final User REGISTERED_USER = new User();
	private static final User ANOTHER_USER = new User();
	
	private static final Trip BRASIL = new Trip();
	
	public User loggedInUser;
	private TripServiceForTest tripServiceForTest;

	@Before
	public void before() {
		tripServiceForTest = new TripServiceForTest();
	}
	
	@Test(expected=UserNotLoggedInException.class)
	public void should_validate_logged_in_user() {
		loggedInUser = GUEST;
		 
		tripServiceForTest.getTripsByUser(null);
	}
	
	@Test
	public void should_not_return_any_trips_if_users_are_not_friends() {
		loggedInUser = REGISTERED_USER;
		User stranger = new User();
		stranger.addFriend(ANOTHER_USER);
		stranger.addTrip(BRASIL);
		
		List<Trip> trips = tripServiceForTest.getTripsByUser(stranger);
		
		assertThat(trips, is(empty()));
	}
	
	private class TripServiceForTest extends TripService {

		@Override
		protected User loggedInUser() {
			return loggedInUser;
		}
		
	}
	
}
