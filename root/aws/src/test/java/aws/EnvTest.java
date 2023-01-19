package aws;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import aws.utils.Environment;

public class EnvTest implements Environment{

	@Test
	void tesEnv() throws ReflectiveOperationException {

		assertThat(System.getenv("foo")).isNull();
		set("foo", "bar");
		assertThat(System.getenv("foo")).isEqualTo("bar");
		delete("foo");
		assertThat(System.getenv("foo")).isNull();
		
	}
}
