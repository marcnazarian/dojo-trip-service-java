package tripservicekata.user;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserTest {

	private static final User BOB = new User();
	private static final User ALICE = new User();

	@Test
	public void should_inform_when_users_are_not_friends() {
		User user = new User();
		user.addFriend(BOB);
		
		assertThat(user.isFriendWith(ALICE), is(false));
	}
	
	@Test
	public void should_inform_when_users_are_friends() {
		User user = new User();
		user.addFriend(BOB);
		user.addFriend(ALICE);
		
		assertThat(user.isFriendWith(ALICE), is(true));
	}
}
