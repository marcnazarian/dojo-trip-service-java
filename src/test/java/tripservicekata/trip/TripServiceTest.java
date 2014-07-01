package tripservicekata.trip;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static tripservicekata.user.UserBuilder.aUser;

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
	private static final Trip LONDON = new Trip();
	
	public User loggedInUser;
	private TripServiceForTest tripServiceForTest;

	@Before
	public void before() {
		tripServiceForTest = new TripServiceForTest();
		loggedInUser = REGISTERED_USER;
	}
	
	@Test(expected=UserNotLoggedInException.class)
	public void should_validate_logged_in_user() {
		loggedInUser = GUEST;
		 
		tripServiceForTest.getTripsByUser(null);
	}
	
	@Test
	public void should_not_return_any_trips_if_users_are_not_friends() {
		User stranger = aUser()
							.friendWith(ANOTHER_USER)
							.withTripsTo(BRASIL)
							.build();
		
		List<Trip> trips = tripServiceForTest.getTripsByUser(stranger);
		
		assertThat(trips, empty());
	}
	
	@Test
	public void should_return_trips_if_users_are_friends() {
		User stranger = aUser()
							.friendWith(ANOTHER_USER, REGISTERED_USER)
							.withTripsTo(BRASIL, LONDON)
							.build();
		
		List<Trip> trips = tripServiceForTest.getTripsByUser(stranger);
		
		assertThat(trips, hasSize(2));
	}
	
	private class TripServiceForTest extends TripService {

		@Override
		protected List<Trip> tripsByUser(User user) {
			return user.trips();
		}

		@Override
		protected User loggedInUser() {
			return loggedInUser;
		}
		
	}
	
}
