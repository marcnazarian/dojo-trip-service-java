package tripservicekata.trip;

import org.junit.Test;

import tripservicekata.exception.UserNotLoggedInException;
import tripservicekata.user.User;

public class TripServiceTest {
	
	@Test(expected=UserNotLoggedInException.class)
	public void should_validate_logged_in_user() {
		TripServiceForTest tripServiceForTest = new TripServiceForTest();
		 
		tripServiceForTest.getTripsByUser(null);
	}
	
	private class TripServiceForTest extends TripService {

		@Override
		protected User loggedInUser() {
			return null;
		}
		
	}
	
}
