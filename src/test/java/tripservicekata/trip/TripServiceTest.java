package tripservicekata.trip;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static tripservicekata.user.UserBuilder.aUser;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import tripservicekata.exception.UserNotLoggedInException;
import tripservicekata.user.User;

public class TripServiceTest {
	
	private static final User GUEST = null;
	private static final User REGISTERED_USER = new User();
	private static final User ANOTHER_USER = new User();
	
	private static final Trip BRASIL = new Trip();
	private static final Trip LONDON = new Trip();
	
	private TripService tripService;
	private ITripDAO tripDAO;

	@Before
	public void before() {
		tripDAO = mock(ITripDAO.class);
		tripService = new TripService(tripDAO);
	}
	
	@Test(expected=UserNotLoggedInException.class)
	public void should_validate_logged_in_user() {
		tripService.getTripsByUser(null, GUEST);
	}
	
	@Test
	public void should_not_return_any_trips_if_users_are_not_friends() {
		User stranger = aUser()
							.friendWith(ANOTHER_USER)
							.withTripsTo(BRASIL)
							.build();
		
		List<Trip> trips = tripService.getTripsByUser(stranger, REGISTERED_USER);
		
		assertThat(trips, empty());
	}
	
	@Test
	public void should_return_trips_if_users_are_friends() {
		User friend = aUser()
							.friendWith(ANOTHER_USER, REGISTERED_USER)
							.withTripsTo(BRASIL, LONDON)
							.build();
		when(tripDAO.tripsByUser(friend)).thenReturn(Arrays.asList(BRASIL, LONDON));
		
		List<Trip> trips = tripService.getTripsByUser(friend, REGISTERED_USER);
		
		assertThat(trips, hasSize(2));
	}
	
}
